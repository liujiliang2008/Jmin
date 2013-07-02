/*
 * Copyright (c) jmin Organization. All rights reserved.
 */
package org.jmin.jec.impl;

import org.jmin.jec.Event;
import org.jmin.jec.EventCallBack;
import org.jmin.jec.EventException;
import org.jmin.jec.EventProcessMode;
import org.jmin.jec.EventProcessor;
import org.jmin.jec.EventResultMode;
import org.jmin.jec.EventState;

/**
 * 事件异步处理驱动线程
 * 
 * @author Chris
 */
public class MinEventExecutor extends Thread{
	
 /**
  * 事件允许的最大数
  */
 private int maxSize;
	
 /**
	* 事件队列
	*/
	private MinEventQueue eventQueue;
	
  /**
  * 构造函数
  */
  public MinEventExecutor(int maxSize){
   this.maxSize=maxSize;
	 this.eventQueue = new MinEventQueue();
  }
  
  /**
   * 异步处理
   */
  public void run(){
  	while(true){//永久循环
  		 if(eventQueue.isEmpty()){//为空则进行等待
  				synchronized(eventQueue){
  				 try{
  					 eventQueue.wait();
  				  }catch(InterruptedException e){
  				  }
  				}
  			}else{
  				 try {
						this.processEvent(this.pop());//开始处理事件
					} catch (Throwable e) {
					}
  		 }
  	}
  }
  
  /**
   * 取出一条事件
   */
  public synchronized MinEventWrapper pop(){
  	return eventQueue.pop();
  }
  
  /**
   * 增加一条事件
   */
  public synchronized void addEvent(MinEventWrapper wrapper)throws EventException{
  	if(eventQueue.size() >= maxSize)
  	 throw new EventException("Event queue has been reach max size("+maxSize+"),and can't add new event");
  	eventQueue.push(wrapper);
  	synchronized(eventQueue){
  		eventQueue.notify();
  	}
  }
  
  /**
   * 处理事件
   */
  public void processEvent(MinEventWrapper wrapper)throws EventException{
   	Event event=wrapper.getEvent();
  	MinEventHandle handle=wrapper.getEventHandle();
  	String[]processorNames=handle.getEventProcessorNames();
  	EventProcessor[]processors=handle.getEventProcessors();
    EventCallBack callback = wrapper.getEventCallBack();
    handle.setEventState(EventState.HANDLING);//状态变为：开始运行
    
  	Object[]resultObjects=new Object[processors.length];
  	MinEventResult[]eventResults=new MinEventResult[processors.length];
  	handle.setEventResult(resultObjects,eventResults);
  	 
    for(int i=0;i<processors.length;i++){
    	EventProcessor processor =processors[i];
    	String processorName=processorNames[i];
    	
    	eventResults[i] = new MinEventResult(processorName,processor);
    	eventResults[i].setStartTime(System.currentTimeMillis());
  
			try {
				resultObjects[i]=processor.process(event);
				eventResults[i].setResultObject(resultObjects[i]);
			} catch (Throwable e) {
				eventResults[i].setFailedCause(e);//设置处理器失败原因
		  	if(EventProcessMode.LINE_MODE.equals(handle.getEventProcessMode())){//串行模式,异常中断
		  		handle.setEventState(EventState.EXCEPTION);//设置中断标记
		  		handle.setInterruptedCause(processorName,e);//设置中断名称
		  		break;
		  	}
			}finally{
				eventResults[i].setEndTime(System.currentTimeMillis());
			}
    }
   
    //如果是正常模式，表示是正常完成
    if(EventState.HANDLING.equals(handle.getEventState()))
     handle.setEventState(EventState.COMPLETE);
    
    //异步模式，如果需要回调，则调用
    if(EventResultMode.ASYN_MODE.equals(handle.getEventResultMode())&& callback!=null){
    	try {
				callback.callback(handle);
			} catch (Throwable e) {
			}
    }
  }
}
