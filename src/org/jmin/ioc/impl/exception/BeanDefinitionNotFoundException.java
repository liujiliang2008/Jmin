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

public class BeanDefinitionNotFoundException extends BeanDefinitionException {

	/**
	 * 构造函数
	 */
	public BeanDefinitionNotFoundException(String message) {
		super(message);
	}
	
	/**
	 * 构造函数
	 */
	public BeanDefinitionNotFoundException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * 构造函数
	 */
	public BeanDefinitionNotFoundException(String message,Throwable cause) {
		super(message,cause);
	}
}