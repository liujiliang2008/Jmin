/*
 * Copyright (c) jmin Organization. All rights reserved.
 */
package org.jmin.jec;

/**
 * 事件发布后,事件中心返回的句柄
 * 
 * @author Chris Liao
 */

public interface EventHandle {
	/**
	 * 获得已发布的事件名
	 */
	public Object getEventkey();
	
	/**
	 * 获得已发布的事件
	 */
	public Event getPublishedEvent();
	
	/**
	 * 获得事件的状态
	 */
	public EventState getEventState();
	
	/**
	 * 获得处理器名称
	 */
	public String[] getEventProcessorNames();
	
	/**
	 * 获得处理器
	 */
	public EventProcessor[] getEventProcessors();
	
	/**
	 * 获得事件结果模式
	 */
	public EventResultMode getEventResultMode();
	
	/**
	 * 获得事件处理模式
	 */
	public EventProcessMode getEventProcessMode();
	
	
	/**
	 * 返回事件处理对象结果
	 */
	public Object[] getResultObjects();
	
	/**
	 * 返回事件处理的详细结果
	 */
	public EventResult[] getEventResults();
	
	/**
	 * 返回事件处理对象结果
	 */
	public Object getResultObject(String processorName);
	
	/**
	 * 返回事件处理的详细结果
	 */
	public EventResult getEventResult(String processorName);
	
	/**
	 * 获得串行模式下的:中断处理器名称
	 */
	public String getInterruptedName();
	
	/**
	 * 获得串行模式下的:中断异常
	 */
	public Throwable getInterruptedCause();
	
}
