/*
*Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.impl.exception;

/**
 *
 * @author Chris Liao
 * @version 1.0
 */

public class BeanParentClassException extends BeanDefinitionException {

	/**
	 * 构造函数
	 */
	public BeanParentClassException(String message) {
		super(message);
	}
	
	/**
	 * 构造函数
	 */
	public BeanParentClassException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * 构造函数
	 */
	public BeanParentClassException(String message,Throwable cause) {
		super(message,cause);
	}
}