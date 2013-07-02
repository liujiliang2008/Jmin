/*
 * Copyright (c) jmin Organization. All rights reserved.
 */
package org.jmin.jec.impl;

import java.util.HashMap;
import java.util.Map;

import org.jmin.jec.Event;
import org.jmin.jec.EventCenter;
import org.jmin.jec.EventException;
import org.jmin.jec.EventProcessor;
import org.jmin.jec.EventPublisher;

/**
 * 事件中心实现
 * 
 * @author Chris
 */
public class MinEventCenter implements EventCenter{
	
	/**
	 * 事件定义Map
	 */
	private Map eventTypeMap;
	
	/**
	 * 事件发布者
	 */
	private EventPublisher eventPublisher;
	
	/**
	 * 事件异步驱动线程
	 */
	private MinEventExecutor eventProcessCenter;
	
	/**
	 * 事件异步驱动线程
	 */
	private final static int MAX_ASYN_EVENT_COUNT=10000;
	
	
	/**
	 * 构造函
	 */
	public MinEventCenter(){
		this(true);
	}
		
	/**
	 * 构造函
	 */
	public MinEventCenter(int size){
		this(MAX_ASYN_EVENT_COUNT,true);
	}
	
	/**
	 * 构造函
	 */
	public MinEventCenter(boolean daemon){
   this(MAX_ASYN_EVENT_COUNT,daemon);
	}

	/**
	 * 构造函
	 */
	public MinEventCenter(int size,boolean daemon){
		this.eventTypeMap =new HashMap();
		this.eventPublisher = new MinEventPublisher(this);
		this.eventProcessCenter = new MinEventExecutor(size);
		this.eventProcessCenter.setDaemon(daemon);
		this.eventProcessCenter.start();
	}
	
	/**
	 * 注册一个事件类型
	 */
	public void registerEventType(Class type)throws EventException{
		this.registerEventType(type,type);
	}
	
	/**
	 * 注册一个事件类型
	 */
	public void registerEventType(Object eventKey,Class eventType)throws EventException{
		 this.checkEventKey(eventKey);
		 this.checkEventType(eventKey,eventType);
		 this.checkIdDuplicateRegister(eventKey);
		 this.eventTypeMap.put(eventKey,new MinEventType(eventKey,eventType));
	}
	
	/**
	 * 获取一个事件类型处理器
	 */
	public EventProcessor getEventProcessor(Object eventKey,String processorName)throws EventException{
		this.checkEventKey(eventKey);
		this.checkProcessorName(processorName);
		return this.getEventType(eventKey).getEventProcessor(processorName);
	}
	
	/**
	 * 删除一个事件类型处理器
	 */
	public void removeEventProcessor(Object eventKey,String processorName)throws EventException{
		this.checkEventKey(eventKey);
		this.checkProcessorName(processorName);
		this.getEventType(eventKey).removeProcessor(processorName);
	}

	/**
	 * 注册一个事件类型处理器
	 */
	public void addEventProcessor(Object eventKey,EventProcessor processor)throws EventException{
		this.checkEventKey(eventKey);
		this.checkProcessor(processor);
		this.addEventProcessor(eventKey,processor.getClass().getName(),processor);
	}

	/**
	 * 注册一个事件类型处理器
	 */
	public void addEventProcessor(Object eventKey,String processorName,EventProcessor processor)throws EventException{
		this.checkEventKey(eventKey);
		this.checkProcessorName(processorName);
		this.checkProcessor(processor);
		this.getEventType(eventKey).addProcessor(processorName,processor);
	}

  /**
   * 获取的事件中心的事件发布者
   */
	public EventPublisher getEventPublisher(){
		return this.eventPublisher;
	}
	
  /**
   * 获取的事件的处理中心
   */
	public MinEventExecutor getEventProcessCenter(){
		return this.eventProcessCenter;
	}
  
  /**
	* 返回定义表
	*/
	Map getEventTypeMap()throws EventException {
		return eventTypeMap;
	}
	
  /**
	* 查找Bean定义
	*/
	MinEventType getEventType(Object id)throws EventException {
	 if(eventTypeMap.containsKey(id))
		 return (MinEventType)eventTypeMap.get(id);
	 else
		 throw new EventException("Not found event type with id:" + id);
	}
	
	/**
	* 检查类型是否已经注册
	*/
	private void checkIdDuplicateRegister(Object id)throws EventException {
		if(this.eventTypeMap.containsKey(id))
			throw new EventException("Duplicate register event type with key:" + id);
	}
	
	/**
	* 检查事件类型
	*/
	private void checkEventKey(Object eventKey)throws EventException {
	 if(eventKey == null)
			throw new EventException("Event key can't be null");
	}
	
	/**
	* 检查事件类型
	*/
	private void checkEventType(Object eventKey,Class eventClass)throws EventException {
		if(eventClass == null)
			throw new EventException("Event["+eventKey+"]type class can't be null");
		if(!Event.class.isAssignableFrom(eventClass))
			throw new EventException("Event["+eventKey+"]type class must be extends from(org.jmin.jec.Event)");
	}
	
	/**
	* 检查处理器
	*/
	private void checkProcessorName(String processorName)throws EventException {
	 if(processorName == null || processorName.trim().length()==0)
			throw new EventException("Event processor name can't be null");
	}
	
	/**
	* 检查处理器
	*/
	private void checkProcessor(EventProcessor processor)throws EventException {
	 if(processor == null)
			throw new EventException("Event processor can't be null");
	}
}
