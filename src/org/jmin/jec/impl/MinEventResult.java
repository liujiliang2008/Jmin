/*
 * Copyright (c) jmin Organization. All rights reserved.
 */
package org.jmin.jec.impl;

import org.jmin.jec.EventProcessor;
import org.jmin.jec.EventResult;

/**
 * 事件处理结果
 * 
 * @author Liao
 */

public class MinEventResult implements EventResult{
	
	/**
	 * 处理开始时间
	 */
	private long startTime;
	
	/**
	 * 处理结束时间
	 */
	private long endTime;
	
	/**
	 * 处理结果
	 */
	private Object resultObject;
	
	/**
	 * 失败异常
	 */
	private Throwable failedCause;
	
	/**
	 * 处理器名字
	 */
	private String processorName;
	
	/**
	 * 处理器
	 */
	private EventProcessor processor;
	
	/**
	 * 构造函数
	 */
	public MinEventResult(String processorName,EventProcessor processor){
		this.processorName =processorName;
		this.processor =processor;
	}
	
	/**
	 * 事件的处理器名称
	 */
	public String getProcessorName(){
		return processorName;
	}
	
	/**
	 * 事件的处理的器
	 */
	public EventProcessor getProcessor(){
		return processor;
	}
	
	/**
	 * 处理开始时间
	 */
	public long getStartTime(){
		return this.startTime;
	}
	
	/**
	 * 处理开始时间
	 */
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	
	/**
	 * 处理结束时间
	 */
	public long getEndTime(){
		return this.endTime ;
	}
	
  public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	/**
   * 处理结果
   */
	public Object getResultObject(){
		return  this.resultObject;
	}
	
	/**
   * 处理结果
   */
	public void setResultObject(Object resultObject) {
		this.resultObject = resultObject;
	}
	
	/**
	 * 失败原因
	 */
	public Throwable getFailedCause() {
		return failedCause;
	}	
	
	/**
	 * 失败原因
	 */
	public void setFailedCause(Throwable failedCause) {
		this.failedCause = failedCause;
	}
	
}
