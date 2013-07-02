/*
 * Copyright (c) jmin Organization. All rights reserved.
 */
package org.jmin.jec;

/**
 * 事件处理器
 * 
 * @author Chris
 */
public interface EventProcessor {
	
	/**
	 * 处理事件
	 */
	public Object process(Event event);
	
}
