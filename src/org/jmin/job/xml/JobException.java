/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.job.xml;

/**
 * 作业排期异常
 * 
 * @author Chris Liao 
 */

public class JobException extends Exception {

	/**
	 * 构造函数
	 */
	public JobException(){}
	
	/**
	 * 构造函数
	 */
	public JobException(String message) {
		super(message);
	}
}
