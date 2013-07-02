/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc;

import org.jmin.ioc.BeanException;

/**
 * 元素异常
 * 
 * @author chris
 */
public class BeanElementException extends BeanException {

	/**
	 * constructor
	 */
	public BeanElementException(String message) {
		super(message);
	}

	/**
	 * constructor
	 */
	public BeanElementException(Throwable cause) {
		super(cause);
	}

	/**
	 * constructor
	 */
	public BeanElementException(String message,Throwable cause) {
		super(message,cause);
	}
}
