/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.impl.exception;

import org.jmin.ioc.BeanException;

/**
 * A exception for bean init
 *
 * @author Chris Liao
 * @version 1.0
 */

public class BeanInstanceInitException extends BeanException {
 
	/**
	 * 构造函数
	 */
	public BeanInstanceInitException(String message) {
		super(message);
	}
	
	/**
	 * 构造函数
	 */
	public BeanInstanceInitException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * 构造函数
	 */
	public BeanInstanceInitException(String message,Throwable cause) {
		super(message,cause);
	}
}