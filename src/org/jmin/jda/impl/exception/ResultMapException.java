/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.exception;

/**
 * SQL结果映射异常
 * 
 * @author Chris
 */
public class ResultMapException extends SqlDefinitionException {

	/**
	 * 构造函数
	 */
	public ResultMapException(String sqlId){
		super(sqlId);
	}
	
	/**
	 * 构造函数
	 */
	public ResultMapException(String sqlId,String message) {
		super(sqlId,message);
	}
	
	/**
	 * 构造函数
	 */
	public ResultMapException(String sqlId,Throwable cause) {
		super(sqlId,cause);
	}
	
	/**
	 * 构造函数
	 */
	public ResultMapException(String sqlId,String message,Throwable cause) {
		super(sqlId,message,cause);
	}
}