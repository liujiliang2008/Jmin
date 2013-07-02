/*
 * Copyright (c) jmin Organization. All rights reserved.
 */
package org.jmin.jec;

/***
 * 事件的发布者,默认为同步结果模式和串行处理模式
 * 
 * @author Chris
 */
public interface EventPublisher {
	
	/**
	 * 发布同步事件
	 */
	public EventHandle publishSynEvent(Event event)throws EventException;
	
	/**
	 * 发布同步事件
	 */
	public EventHandle publishSynEvent(Object eventKey,Event event)throws EventException;
	
	/**
	 * 发布同步事件
	 */
	public EventHandle publishSynEvent(Event event,boolean line)throws EventException;
	
	/**
	 * 发布同步事件
	 */
	public EventHandle publishSynEvent(Object eventKey,Event event,boolean line)throws EventException;
	
	
	
	
	/**
	 * 发布异步事件
	 */
	public EventHandle publishAsynEvent(Event event)throws EventException;
	
	/**
	 * 发布异步事件
	 */
	public EventHandle publishAsynEvent(Object eventKey,Event event)throws EventException;

	/**
	 * 发布异步事件
	 */
	public EventHandle publishAsynEvent(Event event,boolean line)throws EventException;

	/**
	 * 发布异步事件
	 */
	public EventHandle publishAsynEvent(Object eventKey,Event event,boolean line)throws EventException;
	
	/**
	 * 发布异步事件
	 */
	public EventHandle publishAsynEvent(Event event,EventCallBack callback)throws EventException;
	
	/**
	 * 发布异步事件
	 */
	public EventHandle publishAsynEvent(Object eventKey,Event event,EventCallBack callback)throws EventException;
	
	/**
	 * 发布异步事件
	 */
	public EventHandle publishAsynEvent(Event event,boolean line,EventCallBack callback)throws EventException;
	
	/**
	 * 发布异步事件
	 */
	public EventHandle publishAsynEvent(Object eventKey,Event event,boolean line,EventCallBack callback)throws EventException;
	
}
