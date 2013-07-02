/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.transaction;

import org.jmin.jda.JdaException;

/**
 * 事务异常类
 * 
 * @author Chris Liao
 */

public class TransactionException extends JdaException {
	
	/**
	 * 构造函数
	 */
	public TransactionException(String message){
		super(null,message);
	}
	
	/**
	 * 构造函数
	 */
	public TransactionException(Throwable cause) {
		super(null,null,cause);
	}
	
	/**
	 * 构造函数
	 */
	public TransactionException(String message,Throwable cause) {
		super(null,message,cause);
	}
}
