/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.connection;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.jmin.log.Logger;

/**
 * connection handler
 *
 * @author Chris
 * @version 1.0
 */

public class ConnectionHandler implements InvocationHandler {
	
	/**
	 * closed indicator
	 */
	private boolean closed;

	/**
	 * 连接
	 */
	private Connection connction;

	/**
	 * 连接池
	 */
	private ConnectionPool connectionPool;
	
	/**
	 * Statement Cache
	 */
	private StatementCache statementCache;
	
	/**
	 * 最近一次活动时间
	 */
	private long lastExecuteTime;

	/**
	 * message Printer
	 */
	private static final Logger printer = Logger.getLogger(ConnectionHandler.class);

	/**
	 * constructor with a physical connection
	 */
	public ConnectionHandler(Connection connction,StatementCache statementCache,ConnectionPool connectionPool) {
		this.connction = connction;
		this.connectionPool = connectionPool;
		this.statementCache = statementCache;
	}
	
	/**
	 * 最近一次活动时间
	 */
	public long getLastExecutedTime() {
		return lastExecuteTime;
	}
	
	/**
	 * 最近一次活动时间
	 */
	public void setLastExecutedTime(long lastExecuteTime) {
		this.lastExecuteTime = lastExecuteTime;
	}
	
	/**
	 * 关闭连接
	 */
	void releaseConnection(){
		try {
			this.connectionPool.returnConnection(this.connction);
			this.closed = true;
			this.connction = null;
			this.statementCache=null;
			this.connectionPool=null;
		} catch(SQLException e) {
		}
	}

	/**
	 * reflect method 
	 */
	public Object invoke(Object proxy, Method method, Object[] args)throws Throwable {
		Object invocationResult = null;
		String methodName = method.getName();
		this.setLastExecutedTime(System.currentTimeMillis());
		
		if(closed)
			throw new java.sql.SQLException("Connection has been closed");
	
		if(methodName.startsWith("prepare") && this.statementCache.maxSize() > 0) {//优先从缓存中查找出一个statement
			String sql =((String)args[0]).trim();
			PreparedStatement statement = statementCache.get(sql);
			if(statement== null){
				statement = (PreparedStatement)method.invoke(connction,args);
				this.statementCache.put(sql,statement);
				printer.debug("Created new preparedStatement:"+statement);
			}else{
				printer.debug("Found cached preparedStatement:"+statement);
			}
			
			StatementHandler handler = new StatementHandler(statement,this);
			ClassLoader classLoader = statement.getClass().getClassLoader();
			if(statement instanceof CallableStatement)
				invocationResult = Proxy.newProxyInstance(classLoader,new Class[]{CallableStatement.class},handler);
			else
				invocationResult = Proxy.newProxyInstance(classLoader,new Class[]{PreparedStatement.class},handler);
		} else if ("close".equals(methodName)) {
			this.releaseConnection();
		} else {
			invocationResult = method.invoke(this.connction, args);
		}
		return invocationResult;
	}
}
