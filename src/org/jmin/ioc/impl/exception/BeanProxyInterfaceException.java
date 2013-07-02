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

public class BeanProxyInterfaceException extends BeanDefinitionException {
	
	/**
	 * 构造函数
	 */
	public BeanProxyInterfaceException(String message) {
		super(message);
	}
	
	/**
	 * 构造函数
	 */
	public BeanProxyInterfaceException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * 构造函数
	 */
	public BeanProxyInterfaceException(String message,Throwable cause) {
		super(message,cause);
	}
}