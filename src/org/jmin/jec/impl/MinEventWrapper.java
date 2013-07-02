/*
 * Copyright (c) jmin Organization. All rights reserved.
 */
package org.jmin.jec.impl;

import org.jmin.jec.Event;
import org.jmin.jec.EventCallBack;

/**
 * 事件包装
 * 
 * @author Chris Liao
 */
public class MinEventWrapper {
	
	/**
	 * 事件
	 */
	private Event event;
	
	/**
	 * 事件类型
	 */
	private MinEventType eventType;
	
	/**
	 *  事件句柄
	 */
	private MinEventHandle eventHandle;
	
	/**
	 * 异步回调
	 */
	private EventCallBack eventCallBack;
	

	/**
	 * 构造函数
	 */
	public MinEventWrapper(Event event,MinEventType eventType,MinEventHandle eventHandle){
		this.event =event;
		this.eventType=eventType;
		this.eventHandle=eventHandle;
	}
  
	/**
	 * 获取事件
	 */
	public Event getEvent() {
		return event;
	}
 
	/**
	 * 获取事件类型
	 */
	public MinEventType getEventType() {
		return eventType;
	}
	
	/**
	 * 获取事件句柄
	 */
	public MinEventHandle getEventHandle() {
		return eventHandle;
	}
  
	/**
	 * 异步回调
	 */
	public EventCallBack getEventCallBack() {
		return eventCallBack;
	}
	/**
	 * 异步回调
	 */
	public void setEventCallBack(EventCallBack eventCallBack) {
		this.eventCallBack = eventCallBack;
	}
}
