/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.impl.exception;

/**
 * A exception applin in Registration
 *
 * @author Chris Liao
 * @version 1.0
 */

public class BeanClassNotFoundException extends BeanDefinitionException {

	/**
	 * 构造函数
	 */
	public BeanClassNotFoundException(String message) {
		super(message);
	}
	
	/**
	 * 构造函数
	 */
	public BeanClassNotFoundException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * 构造函数
	 */
	public BeanClassNotFoundException(String message,Throwable cause) {
		super(message,cause);
	}

}