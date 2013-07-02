/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.transaction.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import org.jmin.jda.impl.transaction.Transaction;
import org.jmin.jda.impl.transaction.TransactionException;

/**
 * JDBC事务
 * 
 * @author Chris liao
 */

public class JdbcTransaction implements Transaction {
	
  /**
   * 数据连接
   */
  private Connection connection;
 
  /**
   * 构造函数
   */
  public JdbcTransaction(Connection connection,int isolationLevel)throws SQLException {
  	this.connection = connection;
  	this.connection.setAutoCommit(false);
  	this.connection.setTransactionIsolation(isolationLevel);
  }

  /**
   * 提交事物
   */
  public void commit() throws SQLException {
		if (connection != null) {
			connection.commit();
		 }else {
			 throw new TransactionException("Invalid operation,transaction has been committed or rollback");
		 }
  }
  
  /**
   * 回滚事物
   */
  public void rollback() throws SQLException {
		if (connection != null) {
			connection.rollback();
		}else {
		 throw new TransactionException("Invalid operation,transaction has been committed or rollback");
		}
  }
  
  /**
   * 获得事物的连接
   */
  public Connection getConnection() throws SQLException {
    return this.connection;
  }
  
  /**
   * 使事务死亡
   */
  public void clear()throws SQLException{
    this.connection.setAutoCommit(true);
    this.connection = null;
  }
}
