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

public class BeanClassModifiedException extends BeanException {

	/**
	 * 构造函数
	 */
	public BeanClassModifiedException(String message){
		this(message,null);
	}
	
	/**
	 * 构造函数
	 */
	public BeanClassModifiedException(Throwable cause) {
		this(null,cause);
	}
	
	/**
	 * 构造函数
	 */
	public BeanClassModifiedException(String message,Throwable cause) {
		super(message,cause);
	}
}