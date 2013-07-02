/*
 * Copyright (c) jmin Organization. All rights reserved.
 */
package org.jmin.jec.impl.xml;

import org.jmin.jec.EventProcessor;

/**
 * 事件处理器信息
 * 
 * @author Chris Liao
 */

public class ProcessorBean {
	
	/**
	 * 处理器名字
	 */
	private String name;
	
	/**
	 * 处理器类名
	 */
	private String className;
	
	/**
	 * 处理器
	 */
	private EventProcessor eventProcessor;
	
	/**
	 * 处理器名字
	 */
	public String getName() {
		return name;
	}
	/**
	 * 处理器名字
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 处理器类名
	 */
	public String getClassName() {
		return className;
	}
	/**
	 * 处理器类名
	 */
	public void setClassName(String className) {
		this.className = className;
	}
	
	/**
	 * 处理器
	 */
	public EventProcessor getEventProcessor() {
		return eventProcessor;
	}
	/**
	 * 处理器
	 */
	public void setEventProcessor(EventProcessor eventProcessor) {
		this.eventProcessor = eventProcessor;
	}
}
