/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.exception;

import org.jmin.jda.JdaException;

/**
 * SQL数据源定义异常
 * 
 * @author Chris
 */

public class DataSourceException extends JdaException {
  
	/**
	 * 构造函数
	 */
	public DataSourceException(){
		this((String)null);
	}
	
	/**
	 * 构造函数
	 */
	public DataSourceException(String message) {
		this(message,null);
	}
	
	/**
	 * 构造函数
	 */
	public DataSourceException(Throwable cause) {
		this(null,cause);
	}
	
	/**
	 * 构造函数
	 */
	public DataSourceException(String message,Throwable cause) {
		super(null,message,cause);
	}
}