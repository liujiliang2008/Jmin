/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.exception;

import org.jmin.jda.JdaException;

/**
 * SQL定义异常
 *
 * @author Chris
 * @version 1.0
 */
public class SqlDefinitionException extends JdaException {
	
	/**
	 * 构造函数
	 */
	public SqlDefinitionException(String sqlId){
		super(sqlId);
	}
	
	/**
	 * 构造函数
	 */
	public SqlDefinitionException(String sqlId,String message) {
		super(sqlId,message);
	}
	
	/**
	 * 构造函数
	 */
	public SqlDefinitionException(String sqlId,Throwable cause) {
		super(sqlId,cause);
	}
	
	/**
	 * 构造函数
	 */
	public SqlDefinitionException(String sqlId,String message,Throwable cause) {
		super(sqlId,message,cause);
	}
}