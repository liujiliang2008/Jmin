/*
* Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.jda.impl.connection;

import java.sql.SQLException;

/**
 * 数据库连接池定义信息
 * 
 * @author Chris
 * @version 1.0
 */

public abstract class ConnectionPoolInfo {
	
	/**
	 * 初始化完，将不允再许修改属性
	 */
	private boolean inited = false;
	
	/**
	* 池最大容许的size
	*/
	private int connectionMaxSize = 20;
	
	/**
	* Connection的Statement缓存容器大小
	*/
	private int statementCacheSize = 20;
		
	/**
	* 连接的最大闲置时间，超过将被关闭，默认时间为3分钟
	*/
	private long connectionIdleTimeout = 180000;
	
	/**
	* 获取连接的最大同步等待时间:3分钟
	*/
	private long connectionMaxWaitTime = 180000;
	
	
	/**
	 * 初始化完，将不允再许修改属性
	 */
	void setInited(boolean inited) {
		this.inited = inited;
	}

	/**
	* 池最大容许的size
	*/
	public int getConnectionMaxSize() {
		return connectionMaxSize;
	}
	
	/**
	* 池最大容许的size
	*/
	public void setConnectionMaxSize(int connectionMaxSize) {
		if(!this.inited)
		this.connectionMaxSize = connectionMaxSize;
	}
	
	/**
	* Connection的Statement缓存容器大小
	*/
	public int getStatementCacheSize() {
		return statementCacheSize;
	}
	
	/**
	* Connection的Statement缓存容器大小
	*/
	public void setStatementCacheSize(int statementCacheSize) {
		if(!this.inited)
			this.statementCacheSize = statementCacheSize;
	}

	/**
	* 连接的最大闲置时间，超过将被关闭，默认时间为3分钟
	*/
	public long getConnectionIdleTimeout() {
		return connectionIdleTimeout;
	}
	
	/**
	* 连接的最大闲置时间，超过将被关闭，默认时间为3分钟
	*/
	public void setConnectionIdleTimeout(long connectionIdleTimeout) {
		if(!this.inited)
			this.connectionIdleTimeout = connectionIdleTimeout;
	}
	
	/**
	* 获取连接的最大同步等待时间
	*/
	public long getConnectionMaxWaitTime() {
		return connectionMaxWaitTime;
	}
	
	/**
	* 获取连接的最大同步等待时间
	*/
	public void setConnectionMaxWaitTime(long connectionMaxWaitTime) {
		if(!this.inited)
			this.connectionMaxWaitTime = connectionMaxWaitTime;
	}
	
	/**
	* 连接工厂
	*/
	public abstract ConnectionFactory getConnectionFactory();
 
	/**
	 * 检查参数信息
	 */
	protected void check()throws SQLException{
		if(this.connectionMaxSize <=0)
			throw new SQLException("Connection max size must be greater than zero");
		if(this.connectionIdleTimeout <=0)
			throw new SQLException("Connection max idle time must be greater than zero");
		if(this.connectionMaxWaitTime <=0)
			throw new SQLException("Connection max waiting time must be greater than zero");
		if(this.statementCacheSize <=0)
			throw new SQLException("Statement cache Size must be greater than zero");
	}
}
