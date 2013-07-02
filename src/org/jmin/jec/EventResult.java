/*
 * Copyright (c) jmin Organization. All rights reserved.
 */
package org.jmin.jec;

/**
 * 事件的处理结果
 * 
 * @author Administrator
 */
public interface EventResult{
	
	/**
	 * 处理开始时间
	 */
	public long getStartTime();

	/**
	 * 处理结束时间
	 */
	public long getEndTime();
	
  /**
   * 处理结果
   */
	public Object getResultObject();
	
	/**
	 * 失败原因
	 */
	public Throwable getFailedCause();
	
	/**
	 * 事件的处理器名称
	 */
	public String getProcessorName();
	
	/**
	 * 事件的处理的器
	 */
	public EventProcessor getProcessor();
 
}