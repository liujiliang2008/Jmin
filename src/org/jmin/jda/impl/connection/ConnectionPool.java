/*
* Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.jda.impl.connection;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;

import org.jmin.log.Logger;

/**
 * 数据库连接池
 * 
 * @author Chris
 * @version 1.0
 */
public class ConnectionPool {
	
	/**
	 * 连接实例列表
	 */
	private Map connectionMap = null;
	
	/**
	 * 连接池信息
	 */
	private ConnectionPoolInfo connectionPoolInfo = null;
	
	/**
	 * 闲置连接检查
	 */
	private ConnectionIdleTask connectionIdleTask = null;
	
	/**
	 * message Printer
	 */
	private static final Logger logger = Logger.getLogger(ConnectionPool.class);
	
	/**
	 * 构造函数
	 */
	public ConnectionPool(ConnectionPoolInfo connectionPoolInfo)throws SQLException{
		this.connectionMap = new HashMap();
		this.connectionPoolInfo = connectionPoolInfo;
		this.connectionPoolInfo.check();
		if(this.connectionPoolInfo.getConnectionFactory()==null)
			throw new SQLException("Connection factory can't be null");
		this.connectionPoolInfo.setInited(true);
		this.connectionIdleTask = new ConnectionIdleTask(this);
		new Timer(true).schedule(connectionIdleTask,1000,3000);
		Runtime.getRuntime().addShutdownHook(new ConnectionPoolHook(this));
	}
	
	/**
	 * 从池中借用
	 */
	public Connection borrowConnection()throws SQLException{
		synchronized (connectionMap) {
		  ConnectionWrapper connectionWrapper = this.searchIdleConnection();
			if(connectionWrapper == null){//当没有找到一个闲置的Connection，则创建一个新的连接		
				if(connectionMap.size() < connectionPoolInfo.getConnectionMaxSize()){
					Connection con = connectionPoolInfo.getConnectionFactory().createConnection();
					connectionWrapper = new ConnectionWrapper(con,this.connectionPoolInfo.getStatementCacheSize(),this);
					this.connectionMap.put(con,connectionWrapper);
					logger.debug("Created new pooled connection:"+con);
				}else {
					long elapsedTime=0;
					long startWaitTime = System.currentTimeMillis();
					
				  while(elapsedTime < connectionPoolInfo.getConnectionMaxWaitTime()){
						synchronized (connectionMap) {
							try {
								connectionMap.wait(connectionPoolInfo.getConnectionMaxWaitTime()- elapsedTime);
							} catch (InterruptedException e) {
							}
						}
					 connectionWrapper = this.searchIdleConnection();
					 elapsedTime =System.currentTimeMillis() - startWaitTime;
					 if(connectionWrapper == null){
						 if(elapsedTime >= connectionPoolInfo.getConnectionMaxWaitTime()) 
						  throw new SQLException("Connection pool wait timout("+elapsedTime+")ms");
					 }else{
						 break;
					 }
				}
				
				if(connectionWrapper == null)
					throw new SQLException("Connection pool internal error");
				}
			}else{
				logger.debug("Found a idle pooled connection:"+connectionWrapper.getConnection());
			}
			
			connectionWrapper.getConnection().setAutoCommit(true);
			ConnectionHandler connectionHandler = new ConnectionHandler(connectionWrapper.getConnection(),connectionWrapper.getStatementCache(),this);
			connectionWrapper.setConnectionHandler(connectionHandler);
			connectionWrapper.setCurrentStatusCode(ConnectionStatus.Using);
			connectionWrapper.setLastBorrowedTime(System.currentTimeMillis());
			connectionHandler.setLastExecutedTime(System.currentTimeMillis());
			return (Connection)Proxy.newProxyInstance(this.getClass().getClassLoader(),new Class[]{Connection.class},connectionHandler);
		 }
	}
	
	/**
	 * 搜索闲置的连接
	 */
	private ConnectionWrapper searchIdleConnection(){
		ConnectionWrapper targetWrapper = null;
		Iterator itor = connectionMap.values().iterator();
		while(itor.hasNext()){
			ConnectionWrapper wrapper = (ConnectionWrapper)itor.next();
			if(wrapper.isIdle()){
				targetWrapper = wrapper;
				break;
			}
		}
		return targetWrapper;
	}
	
	/**
	 * 归还连接
	 */
	 void returnConnection(Connection con)throws SQLException{//外部归还
			ConnectionWrapper targetWrapper=(ConnectionWrapper)connectionMap.get(con);
			if(targetWrapper!=null){
				resetConnection(con);
				targetWrapper.setCurrentStatusCode(ConnectionStatus.Idle);
				targetWrapper.setLastReturnedTime(System.currentTimeMillis());
				logger.debug("Return a pooled conneciton:"+con);
			}
	   
		 if(targetWrapper!=null){
			 synchronized (connectionMap){
				connectionMap.notify();//主要通知外部的借者
			 }
		}
	}
	
	 /**
	  * 将那些未提交的事务进行回滚
	  */
	 void resetConnection(Connection con){
		 try {
				if(!con.getAutoCommit())
					con.rollback();
				con.setAutoCommit(true);
			} catch (Throwable e) {
			}
	 }
	 
	/**
	 * 关闭所有闲置超过一定时间的连接,定时扫描器调用
	 */
	synchronized void closeIdleTimeoutConnection(){//内部调用清理超过一定时间未使用的Connection
		Iterator itor = connectionMap.values().iterator();
		while(itor.hasNext()){
			ConnectionWrapper wrapper = (ConnectionWrapper)itor.next();
			if(wrapper.isIdle()){
				if(wrapper.getLastReturnedTime() >0 && System.currentTimeMillis() - wrapper.getLastReturnedTime() >= connectionPoolInfo.getConnectionIdleTimeout()){
					wrapper.closeConnection();
					wrapper.setCurrentStatusCode(ConnectionStatus.Closed);
					itor.remove();
					wrapper=null;
			  }
		  }else {//长时间占用连接者，将强行归还到池中去
		  	ConnectionHandler handler = wrapper.getConnectionHandler();
		    if(handler!=null && handler.getLastExecutedTime() >0 && System.currentTimeMillis() - handler.getLastExecutedTime() >= connectionPoolInfo.getConnectionIdleTimeout()){
		    	 handler.releaseConnection();//归还连接
			  }
		  }
	  }
	}
	
	/**
	 * 破坏当前连接池
	 */
	synchronized void destroy(){
		Iterator itor = connectionMap.values().iterator();
		while(itor.hasNext()){
			ConnectionWrapper wrapper = (ConnectionWrapper)itor.next();
			wrapper.closeConnection();
			wrapper.setCurrentStatusCode(ConnectionStatus.Closed);
			itor.remove();
			wrapper=null;
		}
	}
}
