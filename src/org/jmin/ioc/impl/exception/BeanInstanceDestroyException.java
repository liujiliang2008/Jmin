/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.impl.exception;

import org.jmin.ioc.BeanException;

/**
 * A exception for bean destroy
 *
 * @author Chris Liao
 * @version 1.0
 */

public class BeanInstanceDestroyException extends BeanException {
	
	/**
	 * 构造函数
	 */
	public BeanInstanceDestroyException(String message) {
		super(message);
	}
	
	/**
	 * 构造函数
	 */
	public BeanInstanceDestroyException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * 构造函数
	 */
	public BeanInstanceDestroyException(String message,Throwable cause) {
		super(message,cause);
	}
}