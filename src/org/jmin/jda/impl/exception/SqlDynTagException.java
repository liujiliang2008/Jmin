/*
 *Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.exception;

import org.jmin.jda.JdaException;

/**
 * 动态标签异常
 * 
 * @author Chris 
 */

public class SqlDynTagException extends JdaException {
  
	/**
	 * 构造函数
	 */
	public SqlDynTagException(String sqlId){
		super(sqlId);
	}
	
	/**
	 * 构造函数
	 */
	public SqlDynTagException(String sqlId,String message) {
		super(sqlId,message);
	}
	
	/**
	 * 构造函数
	 */
	public SqlDynTagException(String sqlId,Throwable cause) {
		super(sqlId,cause);
	}
	
	/**
	 * 构造函数
	 */
	public SqlDynTagException(String sqlId,String message,Throwable cause) {
		super(sqlId,message,cause);
	}
}