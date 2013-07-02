/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.exception;

import org.jmin.jda.JdaException;

/**
 * SQL执行异常
 * 
 * @author Chris
 */

public class SqlExecutionException extends JdaException {
	/**
	 * 构造函数
	 */
	public SqlExecutionException(String sqlId){
		super(sqlId);
	}
	
	/**
	 * 构造函数
	 */
	public SqlExecutionException(String sqlId,String message) {
		super(sqlId,message);
	}
	
	/**
	 * 构造函数
	 */
	public SqlExecutionException(String sqlId,Throwable cause) {
		super(sqlId,cause);
	}
	
	/**
	 * 构造函数
	 */
	public SqlExecutionException(String sqlId,String message,Throwable cause) {
		super(sqlId,message,cause);
	}
}