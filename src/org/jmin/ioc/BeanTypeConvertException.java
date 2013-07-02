/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc;

/**
 * 转换异常
 * 
 * @author chris
 */
public class BeanTypeConvertException extends BeanException {

	/**
	 * constructor
	 */
	public BeanTypeConvertException(String message) {
		super(message);
	}

	/**
	 * constructor
	 */
	public BeanTypeConvertException(Throwable cause) {
		super(cause);
	}

	/**
	 * constructor
	 */
	public BeanTypeConvertException(String message,Throwable cause) {
		super(message,cause);
	}
}
