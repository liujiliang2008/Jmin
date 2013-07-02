/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.exception;

/**
 * 映射文件异常
 * 
 * @author Chris
 */

public class SqlDefinitionFileException extends SqlDefinitionException {

	/**
	 * 构造函数
	 */
	public SqlDefinitionFileException(String sqlId){
		super(sqlId);
	}
	
	/**
	 * 构造函数
	 */
	public SqlDefinitionFileException(String sqlId,String message) {
		super(sqlId,message);
	}
	
	/**
	 * 构造函数
	 */
	public SqlDefinitionFileException(String sqlId,Throwable cause) {
		super(sqlId,cause);
	}
	
	/**
	 * 构造函数
	 */
	public SqlDefinitionFileException(String sqlId,String message,Throwable cause) {
		super(sqlId,message,cause);
	}
}
