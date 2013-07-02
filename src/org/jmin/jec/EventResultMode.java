/*
 * Copyright (c) jmin Organization. All rights reserved.
 */
package org.jmin.jec;

/**
 * 事件结果模式
 * 0:同步模式,立即处理并返回结果
 * 1:异步模式,队列方式处理
 * 
 * @author Chris
 */
public class EventResultMode {
	public final static EventResultMode SYN_MODE  = new EventResultMode(0);//同步模式
	public final static EventResultMode ASYN_MODE = new EventResultMode(1);//异步模式
 
	private int modeValue;
	public EventResultMode(int modeValue){
		this.modeValue = modeValue;
	}
	
	/**
	 * 重写方法
	 */
	public boolean equals(Object obj){
		if(obj instanceof EventResultMode){
			EventResultMode other = (EventResultMode)obj;
			return this.modeValue == other.modeValue;
		}else {
			return false;
		}
	}
}
