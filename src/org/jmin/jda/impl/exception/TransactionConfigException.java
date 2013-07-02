/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.exception;

import java.sql.SQLException;

/**
 * JTA事务定义异常
 * 
 * @author Chris
 */

public class TransactionConfigException extends SQLException {
  
	/**
	 * 构造函数
	 */
  public TransactionConfigException() {
		super();
	}
  
  /**
	 * 构造函数
	 */
	public TransactionConfigException(String s) {
		super(s);
	}
}