/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc;

import org.jmin.ioc.BeanException;

/**
 * A exception for Ioc Parameter
 *
 * @author Chris Liao
 * @version 1.0
 */

public class BeanParameterException extends BeanException {

	/**
	 * constructor
	 */
	public BeanParameterException(String message) {
		super(message);
	}

	/**
	 * constructor
	 */
	public BeanParameterException(Throwable cause) {
		super(cause);
	}

	/**
	 * constructor
	 */
	public BeanParameterException(String message,Throwable cause) {
		super(message,cause);
	}

}