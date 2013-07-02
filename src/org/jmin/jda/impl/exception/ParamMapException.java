/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.exception;

/**
 * SQL参数映射异常
 * 
 * @author Chris
 */

public class ParamMapException extends SqlDefinitionException {

	/**
	 * 构造函数
	 */
	public ParamMapException(String sqlId){
		super(sqlId);
	}
	
	/**
	 * 构造函数
	 */
	public ParamMapException(String sqlId,String message) {
		super(sqlId,message);
	}
	
	/**
	 * 构造函数
	 */
	public ParamMapException(String sqlId,Throwable cause) {
		super(sqlId,cause);
	}
	
	/**
	 * 构造函数
	 */
	public ParamMapException(String sqlId,String message,Throwable cause) {
		super(sqlId,message,cause);
	}
}