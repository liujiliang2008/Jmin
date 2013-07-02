/*
 * Copyright (c) jmin Organization. All rights reserved.
 */
package org.jmin.jec;

/**
 * 事件处理模式
 * 
 * 0:串行模式,中途异常将中断执行
 * 1:并行模式,忽律中间异常，执行完所有处理
 * 
 * @author Chris
 */
public class EventProcessMode {
	public final static EventProcessMode LINE_MODE  = new EventProcessMode(0);//串行模式
	public final static EventProcessMode PARALLEL_MODE  = new EventProcessMode(1);//并行模式
	 
	private int modeValue;
	public EventProcessMode(int modeValue){
		this.modeValue = modeValue;
	}
	
	/**
	 * 重写方法
	 */
	public boolean equals(Object obj){
		if(obj instanceof EventProcessMode){
			EventProcessMode other = (EventProcessMode)obj;
			return this.modeValue == other.modeValue;
		}else {
			return false;
		}
	}
}
