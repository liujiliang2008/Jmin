/*
*Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.connection;

import java.sql.Connection;

import org.jmin.log.Logger;
/**
 * 连接包裹
 *
 * @author Chris liao
 * @version 1.0
 */

class ConnectionWrapper{
	
  /**
   * 连接实例
   */
  private Connection connection;
  
  /**
   * 连接的当前状态代码
   */
  private int currentStatusCode;

  /**
   * 最近被归还时间
   */
  private long lastReturnedTime;

  /**
   * 最近的被取出使用时间
   */
  private long lastBorrowedTime;
 
  /**
   * statemen缓存
   */
  private StatementCache statementCache;
  
  /**
   * 连接接口的Proxy的Handler
   */
  private ConnectionHandler connectionHandler;

	/**
	 * message Printer
	 */
	private static final Logger printer = Logger.getLogger(ConnectionWrapper.class);
  
	/**
   * Constructor
   */
  public ConnectionWrapper(Connection connection,ConnectionPool connectionPool){
  	this(connection,20,connectionPool);
  }
  
  /**
   * Constructor
   */
  public ConnectionWrapper(Connection connection,int statementCacheSize,ConnectionPool connectionPool){
    this.connection = connection;
    this.currentStatusCode = ConnectionStatus.Idle;
    this.statementCache = new StatementCache(statementCacheSize);
  }
  
  /**
   * 连接实例
   */
  public Connection getConnection() {
    return connection;
  }
 
  /**
   * 最近的被取出使用时间
   */
  public long getLastBorrowedTime() {
		return lastBorrowedTime;
	}
  
  /**
   * 最近的被取出使用时间
   */
	public void setLastBorrowedTime(long lastBorrowedTime) {
		this.lastBorrowedTime = lastBorrowedTime;
	}
	
	 /**
   * 最近被归还时间
   */
	public long getLastReturnedTime() {
		return lastReturnedTime;
	}
	
	 /**
   * 最近被归还时间
   */
	public void setLastReturnedTime(long lastReturnedTime) {
		this.lastReturnedTime = lastReturnedTime;
	}
	

  /**
   * statemen缓存
   */
	public StatementCache getStatementCache() {
		return statementCache;
	}
  
	/**
   * 连接接口的Proxy的Handler
   */
	public ConnectionHandler getConnectionHandler() {
		return connectionHandler;
	}
	
	/**
   * 连接接口的Proxy的Handler
   */
	public void setConnectionHandler(ConnectionHandler connectionHandler) {
		this.connectionHandler = connectionHandler;
	}

	/**
   * 是否使用中
   */
  public boolean isUsing(){
  	return (this.currentStatusCode == ConnectionStatus.Using);
  }
  
  /**
   * 是否闲置
   */
  public boolean isIdle(){
  	return (this.currentStatusCode == ConnectionStatus.Idle);
  }
  
	/**
   * 连接的当前状态代码
   */
  public int getCurrentStatusCode() {
		return currentStatusCode;
	}
  
  /**
   * 连接的当前状态代码
   */
	public void setCurrentStatusCode(int currentStatusCode) {
		this.currentStatusCode = currentStatusCode;
	}
  
  
  /**
   * 关闭当前连接
   */
  public synchronized void closeConnection(){
  	this.statementCache.clear();
  	this.closeConnection(this.connection);
  	printer.info("Closed connection:" + this.connection);
		this.connection=null;
  }
 
	/**
	 * 关闭连接
	 */
	private void closeConnection(Connection connection) {
		try {
			if(!connection.getAutoCommit())
				connection.rollback();
		} catch (Throwable e) {
		}
		
		try { 
			this.connection.close();
		} catch (Throwable e) {
		}
	}
}