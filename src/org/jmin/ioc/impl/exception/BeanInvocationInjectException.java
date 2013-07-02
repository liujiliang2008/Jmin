/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.impl.exception;

import org.jmin.ioc.BeanException;

/**
 * A exception for bean create
 *
 * @author Chris Liao
 * @version 1.0
 */

public class BeanInvocationInjectException extends BeanException {

	/**
	 * 构造函数
	 */
	public BeanInvocationInjectException(String message) {
		super(message);
	}
	
	/**
	 * 构造函数
	 */
	public BeanInvocationInjectException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * 构造函数
	 */
	public BeanInvocationInjectException(String message,Throwable cause) {
		super(message,cause);
	}
}