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

public class BeanMultiMatchedSubClassFoundException extends BeanException {

	/**
	 * 构造函数
	 */
	public BeanMultiMatchedSubClassFoundException(String message) {
		super(message);
	}
	
	/**
	 * 构造函数
	 */
	public BeanMultiMatchedSubClassFoundException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * 构造函数
	 */
	public BeanMultiMatchedSubClassFoundException(String message,Throwable cause) {
		super(message,cause);
	}

}