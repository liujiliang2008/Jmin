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

public class BeanClassException extends BeanDefinitionException {

	/**
	 * 构造函数
	 */
	public BeanClassException(String message) {
		super(message);
	}
	
	/**
	 * 构造函数
	 */
	public BeanClassException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * 构造函数
	 */
	public BeanClassException(String message,Throwable cause) {
		super(message,cause);
	}
}