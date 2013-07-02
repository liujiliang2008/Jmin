/*
 * Copyright (c) jmin Organization. All rights reserved.
 */
package org.jmin.jec;

/**
 * 事件状态
 * 
 * @author Chris
 */
public class EventState {
	
	public final static EventState WAIT  = new EventState(0);//等待处理状态
	public final static EventState HANDLING =new EventState(1);//正在处理中
	public final static EventState COMPLETE =new EventState(2);//已经完成 
	public final static EventState EXCEPTION =new EventState(3);//异常中断
	
	private int modeValue;
	public EventState(int modeValue){
		this.modeValue = modeValue;
	}
	
	/**
	 * 重写方法
	 */
	public boolean equals(Object obj){
		if(obj instanceof EventState){
			EventState other = (EventState)obj;
			return this.modeValue == other.modeValue;
		}else {
			return false;
		}
	}
}
