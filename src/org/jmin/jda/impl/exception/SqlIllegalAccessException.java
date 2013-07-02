/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.exception;

/**
 * SQL非法操作异常
 * 
 * @author Chris
 */

public class SqlIllegalAccessException extends SqlExecutionException {

	/**
	 * 构造函数
	 */
	public SqlIllegalAccessException(String sqlId){
		super(sqlId);
	}
	
	/**
	 * 构造函数
	 */
	public SqlIllegalAccessException(String sqlId,String message) {
		super(sqlId,message);
	}
	
	/**
	 * 构造函数
	 */
	public SqlIllegalAccessException(String sqlId,Throwable cause) {
		super(sqlId,cause);
	}
	
	/**
	 * 构造函数
	 */
	public SqlIllegalAccessException(String sqlId,String message,Throwable cause) {
		super(sqlId,message,cause);
	}
}