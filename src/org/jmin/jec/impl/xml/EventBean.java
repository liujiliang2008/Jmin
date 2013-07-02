/*
 * Copyright (c) jmin Organization. All rights reserved.
 */
package org.jmin.jec.impl.xml;

import java.util.List;

/**
 * 事件信息
 * 
 * @author Chris Liao
 */
public class EventBean {
	
	/**
	 * 事件的名字
	 */
	private String name;
	
	/**
	 * 事件的类名
	 */
	private String className;
	
	/**
	 * 事件处理器列表
	 */
	private List processorList;
	
	/**
	 * 事件的名字
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 事件的名字
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 事件的类名
	 */
	public String getClassName() {
		return className;
	}
	
	/**
	 * 事件的类名
	 */
	public void setClassName(String className) {
		this.className = className;
	}
	
	/**
	 * 事件处理器列表
	 */
	public List getProcessorList() {
		return processorList;
	}
	
	/**
	 * 事件处理器列表
	 */
	public void setProcessorList(List processorList) {
		this.processorList = processorList;
	}
}
