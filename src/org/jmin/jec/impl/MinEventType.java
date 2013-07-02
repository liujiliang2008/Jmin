/*
 * Copyright (c) jmin Organization. All rights reserved.
 */
package org.jmin.jec.impl;

import java.util.ArrayList;
import java.util.List;

import org.jmin.jec.EventProcessor;

/**
 * 事件类型
 * 
 * @author Chris Liao
 */
public final class MinEventType {
	
	/**
	 * 事件类型Key
	 */
	private Object eventKey;
	
	/**
	 * 事件类型
	 */
	private Class eventType;

	/**
	 * 处理器名称
	 */
	private List eventProcessorNameList;
	
	/**
	 * 事件处理器列表
	 */
	private List eventProcessorList;
	

	/**
	 * 构造函数
	 */
	public MinEventType(Class eventType){
	 this(eventType,eventType);
	}
	
	/**
	 * 构造函数
	 */
	public MinEventType(Object eventKey,Class eventType){
		this.eventKey = eventKey;
		this.eventType = eventType;
		this.eventProcessorNameList=new ArrayList();
		this.eventProcessorList=new ArrayList();
	}

	/**
	 * 事件名
	 */
	public Object getEventKey() {
		return eventKey;
	}
	
	/**
	 * 事件类型
	 */
	public Class getEventType() {
		return eventType;
	}
	
	/**
	 *是否存在处理器名称
	 */
	public boolean containsProcessorName(String processorName){
	 return this.eventProcessorNameList.contains(processorName);
	}
	
	/**
	 * 返回类型下处理器名字
	 */
	public int getEventProcessorSize(){
		return eventProcessorNameList.size();
	}
	
	/**
	 * 获得Processor位置
	 */
	public int getEventProcessorIndex(String processorName){
		return this.eventProcessorNameList.indexOf(processorName);
	}
	
	/**
	 * 返回类型下处理器名字
	 */
	public String[] getEventProcessorNames(){
		return (String[])eventProcessorNameList.toArray(new String[0]);
	}
	
	/**
	 * 返回类型下处理器名字
	 */
	public EventProcessor[] getEventProcessors(){
		return (EventProcessor[])eventProcessorList.toArray(new EventProcessor[0]);
	}
	
	/**
	 * 获得Processor
	 */
	public EventProcessor getEventProcessor(String processorName){
	 int index=this.getEventProcessorIndex(processorName);
	 if(index >-1)
		 return (EventProcessor)this.eventProcessorList.get(index);
	 else
		 return null;
	}
	
	/**
	 * 删除Processor
	 */
	public void removeProcessor(String processorName){
		int index=this.eventProcessorNameList.indexOf(processorName);
		if(index >-1)
			 eventProcessorList.remove(index);
	}
	
	/**
	 * 增加Processor
	 */
	public void addProcessor(String processorName,EventProcessor processor){
		int index=this.eventProcessorNameList.indexOf(processorName);
		if(index ==-1){
			eventProcessorNameList.add(processorName);
			eventProcessorList.add(processor);
		}
	}
}
