/*
* Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.jda.impl.connection;

/**
 * 破坏沟子函数
 * 
 * @author Chris liao
 */
class ConnectionPoolHook extends Thread{
	
	/**
	 * 连接池
	 */
	private ConnectionPool connectionPool;
	
	/**
	 * 构造函数
	 */
	public ConnectionPoolHook(ConnectionPool connectionPool){
		this.connectionPool =connectionPool;
	}
	
	/**
	 * 线程方法
	 */
	public void run(){
		connectionPool.destroy();
	}
}
