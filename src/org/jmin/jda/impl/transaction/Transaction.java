/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.transaction;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 事务接口
 * 
 * @author Chris Liao
 */

public interface Transaction {

	/**
	 * 提交
	 */
  public void commit() throws SQLException;
  
  /**
   * 回滚
   */
  public void rollback() throws SQLException;
  
  /**
   * 获得事务Connection
   */
  public Connection getConnection()throws SQLException;
  
  /**
   * 使事务死亡
   */
  public void clear()throws SQLException;
 
}
