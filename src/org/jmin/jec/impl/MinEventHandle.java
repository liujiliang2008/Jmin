/*
 * Copyright (c) jmin Organization. All rights reserved.
 */
package org.jmin.jec.impl;

import org.jmin.jec.Event;
import org.jmin.jec.EventHandle;
import org.jmin.jec.EventProcessMode;
import org.jmin.jec.EventProcessor;
import org.jmin.jec.EventResult;
import org.jmin.jec.EventResultMode;
import org.jmin.jec.EventState;

/**
 * 事件发布后的句柄
 * 
 * @author ChrisLiao
 */

public class MinEventHandle implements EventHandle{

	/**
	 * 事件本身
	 */
	private Event event;

	/**
	 * 事件类型Key
	 */
	private Object eventKey;
	
	/**
	 * 事件的状态
	 */
	private EventState eventState;

	/**
	 * 事件结果模式
	 */
	private EventResultMode resultMode;
	
	/**
	 * 事件处理模式
	 */
	private EventProcessMode processMode;
	
	
	/**
	 * 处理器名称
	 */
	private String[]processorNames;
	
	/**
	 * 处理器
	 */
	private EventProcessor[]processors;
	            
	/**
	 * 处理结果对象
	 */
	private Object[]resultObjects;
	
	/**
	 * 处理的事件结果
	 */
	private EventResult[]eventResults;
	
	/**
	 * 串行模式下的：中断处理器名称
	 */
	private String interruptedName;
	
	/**
	 * 串行模式下的:中断异常
	 */
	private Throwable interruptedCause;
	

	/**
	 * 构造函数
	 */
	MinEventHandle(Object eventKey,Event event,String[]processorNames,EventProcessor[]processors,EventResultMode resultMode,EventProcessMode processMode){
		this.eventKey=eventKey;
		this.event =event;
		this.resultMode=resultMode;
		this.processMode=processMode;
		this.eventState=EventState.WAIT;//创建时候,默认为:等待状态
		this.processorNames=processorNames;
		this.processors=processors;
		
		this.resultObjects = new Object[processorNames.length];
		this.eventResults = new EventResult[processorNames.length];
	}
	
	/**
	 * 获得已发布的事件名
	 */
	public Object getEventkey(){
		return this.eventKey;
	}
	
	/**
	 * 获得事件
	 */
	public Event getPublishedEvent(){
		return this.event;
	}
	
	/**
	 * 获得事件结果模式
	 */
	public EventResultMode getEventResultMode(){
		return resultMode;
	}
	
	/**
	 * 获得事件处理模式
	 */
	public EventProcessMode getEventProcessMode(){
		return processMode;
	}
	
	/**
	 * 获得事件的状态
	 */
	public EventState getEventState(){
		return eventState;
	}
	
	/**
	 * 设置事件的状态
	 */
	 void setEventState(EventState eventState){
		this.eventState=eventState;
	}
	
	/**
	 * 获得处理器的名称
	 */
	public String[] getEventProcessorNames(){
		return this.processorNames;
	}
	
	/**
	 * 获得处理器
	 */
	public EventProcessor[] getEventProcessors(){
		return this.processors;
	}
	
	/**
	 * 返回事件处理对象结果
	 */
	public Object[] getResultObjects(){
		this.checkEventCompleteState();
		return this.resultObjects;
	}
	
	/**
	 * 返回事件处理的详细结果
	 */
	public EventResult[] getEventResults(){
		this.checkEventCompleteState();
		return this.eventResults;
	}
	
	/**
	 * 返回事件处理对象结果
	 */
	public Object getResultObject(String processorName){
		this.checkEventCompleteState();
		int index=this.getProcessorIndex(processorName);
		if(index==-1)
		  throw new IllegalArgumentException("Not found processor name("+processorName+")in event type");
		return this.resultObjects[index];
	}
	
	/**
	 * 返回事件处理的详细结果
	 */
	public EventResult getEventResult(String processorName){
		this.checkEventCompleteState();
		int index=this.getProcessorIndex(processorName);
		if(index==-1)
		  throw new IllegalArgumentException("not found processor name("+processorName+")in event type");
		return this.eventResults[index];
	}
	
	/**
	 * 串行模式下的:中断处理器名称
	 */
	public String getInterruptedName() {
		return interruptedName;
	}
	
	/**
	 * 串行模式下的:中断异常
	 */
 	public Throwable getInterruptedCause() {
		return interruptedCause;
	}
 	
	/**
	 * 串行模式下的:中断处理器名称
	 */
	void setInterruptedCause(String interruptedName,Throwable interruptedCause) {
		this.interruptedName = interruptedName;
		this.interruptedCause = interruptedCause;
	}
	
	/**
	 * 放入处理的详细处理
	 */
	void setEventResult(Object[]resultObjects,EventResult[]eventResults) {
   this.resultObjects=resultObjects;
   this.eventResults=eventResults;
	}
	
	/**
	 * 获得处理器的次序位置
	 */
	private int getProcessorIndex(String processname){
		int index=-1;
		for(int i=0;i<processorNames.length;i++){
			if(processname.equals(processorNames[i])){
				index =i;
				break;
			}
		}
		return index;
	}

	/**
	 * 检查事件是否完成
	 */
	private void checkEventCompleteState(){
		if(!EventState.COMPLETE.equals(eventState) && !EventState.EXCEPTION.equals(eventState)) 
			throw new IllegalStateException("Event has not been handled");
	}
}
