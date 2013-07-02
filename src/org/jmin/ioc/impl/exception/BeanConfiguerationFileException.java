/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.impl.exception;

import org.jmin.ioc.BeanException;

/**
 * A exception for bean Definition
 *
 * @author Chris Liao
 * @version 1.0
 */

public class BeanConfiguerationFileException extends BeanException {

	/**
	 * 构造函数
	 */
	public BeanConfiguerationFileException(String message) {
		super(message);
	}
	
	/**
	 * 构造函数
	 */
	public BeanConfiguerationFileException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * 构造函数
	 */
	public BeanConfiguerationFileException(String message,Throwable cause) {
		super(message,cause);
	}

}