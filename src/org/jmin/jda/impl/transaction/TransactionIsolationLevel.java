/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.transaction;

/**
 * 事务隔离等级
 * 
 * @author Chris Liao
 */

public class TransactionIsolationLevel {

	public int TRANSACTION_NONE = 0;

	public int TRANSACTION_READ_UNCOMMITTED = 1;

	public int TRANSACTION_READ_COMMITTED = 2;

	public int TRANSACTION_REPEATABLE_READ = 4;

	public int TRANSACTION_SERIALIZABLE = 8;
}
