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

public class BeanPropertyInjectException extends BeanException {

	/**
	 * 构造函数
	 */
	public BeanPropertyInjectException(String message) {
		super(message);
	}
	
	/**
	 * 构造函数
	 */
	public BeanPropertyInjectException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * 构造函数
	 */
	public BeanPropertyInjectException(String message,Throwable cause) {
		super(message,cause);
	}
}