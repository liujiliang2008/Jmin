/*
 * Copyright (c) jmin Organization. All rights reserved.
 */
package org.jmin.jec.impl;

import java.util.Iterator;
import java.util.Map;

import org.jmin.jec.Event;
import org.jmin.jec.EventCallBack;
import org.jmin.jec.EventException;
import org.jmin.jec.EventHandle;
import org.jmin.jec.EventProcessMode;
import org.jmin.jec.EventProcessor;
import org.jmin.jec.EventPublisher;
import org.jmin.jec.EventResultMode;
import org.jmin.jec.EventState;

/**
 * 事件发布者
 * 
 * @author Chris Liao
 */
public class MinEventPublisher implements EventPublisher{
	
	/**
	 * 事件中心
	 */
	private MinEventCenter eventCenter;
	
	/**
	 * 构造函数
	 */
	public MinEventPublisher(MinEventCenter eventCenter){
		this.eventCenter=eventCenter;
	}
	
	/**
	 * 发布同步事件
	 */
	public EventHandle publishSynEvent(Event event)throws EventException{
		this.checkEvent(event);
		return this.publishSynEvent(event.getClass(),event);
	}
	
	/**
	 * 发布同步事件
	 */
	public EventHandle publishSynEvent(Object eventKey,Event event)throws EventException{
		this.checkEvent(event);
		this.checkEventKey(eventKey);
		return this.publishSynEvent(event,true);
	}
	
	/**
	 * 发布同步事件
	 */
	public EventHandle publishSynEvent(Event event,boolean line)throws EventException{
		this.checkEvent(event);
		return this.publishSynEvent(event.getClass(),event,line);
	}
	
	/**
	 * 发布同步事件
	 */
	public EventHandle publishSynEvent(Object eventKey,Event event,boolean line)throws EventException{
		this.checkEvent(event);
		this.checkEventKey(eventKey);
		
		MinEventType eventType=this.getMinEventType(eventKey,event);
		if(eventType == null)
			throw new EventException("Can't find event type in center");
		

		 //构造事件句柄
		Object typeKey = eventType.getEventKey();
		String[] processoNames=eventType.getEventProcessorNames();
		EventProcessor[] processors=eventType.getEventProcessors();
		if(processoNames.length==0)
			throw new EventException("Event Processor list can't be empty ");
		
		EventProcessMode eventProcessMode=(line)?EventProcessMode.LINE_MODE:EventProcessMode.PARALLEL_MODE;
		MinEventHandle handle = new MinEventHandle(typeKey,event,processoNames,processors,EventResultMode.SYN_MODE,eventProcessMode);	
	  
		MinEventWrapper wrapper = new MinEventWrapper(event,eventType,handle);
		this.eventCenter.getEventProcessCenter().processEvent(wrapper);
		if(EventState.EXCEPTION.equals(handle.getEventState()))
			throw new EventException(handle.getInterruptedCause());
		
		return handle;
	}
	
	
	/**
	 * 发布异步事件
	 */
	public EventHandle publishAsynEvent(Event event)throws EventException{
		this.checkEvent(event);
		return this.publishAsynEvent(event.getClass(),event);
	}
	
	/**
	 * 发布异步事件
	 */
	public EventHandle publishAsynEvent(Object eventKey,Event event)throws EventException{
		this.checkEvent(event);
		this.checkEventKey(eventKey);
		return this.publishAsynEvent(eventKey,event,true);
	}

	/**
	 * 发布异步事件
	 */
	public EventHandle publishAsynEvent(Event event,boolean line)throws EventException{
		this.checkEvent(event);
		return this.publishAsynEvent(event.getClass(),event,line);
	}

	/**
	 * 发布异步事件
	 */
	public EventHandle publishAsynEvent(Object eventKey,Event event,boolean line)throws EventException{
		this.checkEvent(event);
		this.checkEventKey(eventKey);
		return this.publishAsynEvent(eventKey,event,line,null);
	}
	
	/**
	 * 发布异步事件
	 */
	public EventHandle publishAsynEvent(Event event,EventCallBack callback)throws EventException{
		this.checkEvent(event);
		return this.publishAsynEvent(event.getClass(),event,callback);
	}
	
	/**
	 * 发布异步事件
	 */
	public EventHandle publishAsynEvent(Object eventKey,Event event,EventCallBack callback)throws EventException{
		this.checkEvent(event);
		this.checkEventKey(eventKey);
		return this.publishAsynEvent(eventKey,event,true,callback);
	}
	
	/**
	 * 发布异步事件
	 */
	public EventHandle publishAsynEvent(Event event,boolean line,EventCallBack callback)throws EventException{
		this.checkEvent(event);
		return this.publishAsynEvent(event.getClass(),event,line,callback);
	}
	
	/**
	 * 发布异步事件
	 */
	public EventHandle publishAsynEvent(Object eventKey,Event event,boolean line,EventCallBack callback)throws EventException{
		this.checkEvent(event);
		this.checkEventKey(eventKey);
		
		MinEventType eventType =this.getMinEventType(eventKey,event); 
		if(eventType == null)
			throw new EventException("Can't find event type in center");

		 //构造事件句柄
		Object typeKey = eventType.getEventKey();
		String[] processoNames=eventType.getEventProcessorNames();
		EventProcessor[] processors=eventType.getEventProcessors();
		if(processoNames.length==0)
			throw new EventException("Event Processor list can't be empty ");
		
		EventProcessMode eventProcessMode=(line)?EventProcessMode.LINE_MODE:EventProcessMode.PARALLEL_MODE;
		MinEventHandle handle = new MinEventHandle(typeKey,event,processoNames,processors,EventResultMode.ASYN_MODE,eventProcessMode);	

		MinEventWrapper wrapper = new MinEventWrapper(event,eventType,handle);
		wrapper.setEventCallBack(callback);
		this.eventCenter.getEventProcessCenter().addEvent(wrapper);
		return handle;
	}
	
	/**
	 * 获取事件所支持的类型
	 */
	private MinEventType getMinEventType(Object eventKey,Event event)throws EventException{
		int mactchedCount=0;
		MinEventType targetMinEventType = eventCenter.getEventType(eventKey);
		if(targetMinEventType==null){//通过Key的方式无法找到事件类型，则进行模糊匹配
			Iterator itor= eventCenter.getEventTypeMap().entrySet().iterator();
			while(itor.hasNext()){
				Map.Entry entry =(Map.Entry)itor.next();
				MinEventType type =(MinEventType)entry.getValue();
				if(type.getEventType().isInstance(event)){//符合条件
					mactchedCount++;
					targetMinEventType = type;
					
					if(mactchedCount>=2)
						throw new EventException("Found mutil type for event");
				}
			}
		}
		
		return targetMinEventType;
	}
	
	/**
	 * 检查事件
	 */
	private void checkEvent(Event event) throws EventException {
		if(event == null)
			throw new EventException("Event can't null");
	}
	
	/**
	 * 检查事件
	 */
	private void checkEventKey(Object eventKey) throws EventException {
		if(eventKey == null)
			throw new EventException("Event type key can't null");
	}
}
