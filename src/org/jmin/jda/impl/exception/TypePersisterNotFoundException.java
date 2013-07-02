/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.exception;

import org.jmin.jda.JdaException;

/**
 * 持久器没有找到异常
 * 
 * @author chris
 */

public class TypePersisterNotFoundException extends JdaException{
	
	/**
	 * 构造函数
	 */
	public TypePersisterNotFoundException(String message){
		super(null,message);
	}
	
	/**
	 * 构造函数
	 */
	public TypePersisterNotFoundException(Throwable cause) {
		super(null,null,cause);
	}
	
	/**
	 * 构造函数
	 */
	public TypePersisterNotFoundException(String message,Throwable cause) {
		super(null,message,cause);
	}
}