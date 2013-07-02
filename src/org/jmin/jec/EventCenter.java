/*
 * Copyright (c) jmin Organization. All rights reserved.
 */
package org.jmin.jec;

/**
 * 事件中心
 * 
 * @author Chris
 */
public interface EventCenter {
  
	/**
	 * 注册一个事件类型
	 */
	public void registerEventType(Class type)throws EventException;
	
	/**
	 * 注册一个事件类型
	 */
	public void registerEventType(Object eventKey,Class eventType)throws EventException;
	
	/**
	 * 删除一个事件类型处理器
	 */
	public void removeEventProcessor(Object eventKey,String processorName)throws EventException;
	
	/**
	 * 获取一个事件类型处理器
	 */
	public EventProcessor getEventProcessor(Object eventKey,String processorName)throws EventException;
	
	/**
	 * 注册一个事件类型处理器
	 */
	public void addEventProcessor(Object eventKey,EventProcessor processor)throws EventException;
	
	/**
	 * 注册一个事件类型处理器
	 */
	public void addEventProcessor(Object eventKey,String processorName,EventProcessor processor)throws EventException;

  /**
   * 获取的事件中心的事件发布者
   */
	public EventPublisher getEventPublisher();

}
