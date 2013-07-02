/*
 * Copyright (c) jmin Organization. All rights reserved.
 */
package org.jmin.jec.impl;

import java.util.LinkedList;

/**
 * 事件队列
 * 
 * @author Chris
 */
public class MinEventQueue {
	
	/**
	 * 列表
	 */
	private LinkedList eventList = new LinkedList();
	
	/**
	 * 是否为空
	 */
	public boolean isEmpty(){
		return eventList.isEmpty();
	}
	
	/**
	 * 事件数量
	 */
	public int size(){
	 return eventList.size();
	}
	
	/**
	 * 增加一个事件
	 */
	public void push(MinEventWrapper wrapper){
		eventList.addLast(wrapper);
	}
	
	/**
	 * 取出一个事件
	 */
	public MinEventWrapper pop(){
		return (MinEventWrapper)eventList.removeFirst();
	}
}
