/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.impl.exception;

/**
 * 类的方法异常
 *
 * @author Chris Liao
 * @version 1.0
 */

public class BeanClassMethodException extends BeanDefinitionException {

	/**
	 * 构造函数
	 */
	public BeanClassMethodException(String message) {
		super(message);
	}
	
	/**
	 * 构造函数
	 */
	public BeanClassMethodException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * 构造函数
	 */
	public BeanClassMethodException(String message,Throwable cause) {
		super(message,cause);
	}
}