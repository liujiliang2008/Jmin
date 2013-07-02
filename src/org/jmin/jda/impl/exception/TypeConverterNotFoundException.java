/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.exception;

import org.jmin.jda.JdaException;

/**
 * 转换器没有找到异常
 * 
 * @author chris
 */
public class TypeConverterNotFoundException extends JdaException{
	
	/**
	 * 构造函数
	 */
	public TypeConverterNotFoundException(String message){
		super(null,message);
	}
	
	/**
	 * 构造函数
	 */
	public TypeConverterNotFoundException(Throwable cause) {
		super(null,null,cause);
	}
	
	/**
	 * 构造函数
	 */
	public TypeConverterNotFoundException(String message,Throwable cause) {
		super(null,message,cause);
	}
}