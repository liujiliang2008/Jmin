/*
* Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.jda.impl.connection;

import java.util.TimerTask;

/**
 * 连接闲置检测任务
 * 
 * @author Chris
 * @version 1.0
 */

class ConnectionIdleTask extends TimerTask{
	
	/**
	 * 连接池
	 */
 private ConnectionPool connectionPool;
	
	/**
	 * 构造函数
	 */
	public ConnectionIdleTask(ConnectionPool connectionPool){
	 this.connectionPool = connectionPool;
	}
	
	/**
	 * 任务运行
	 */
	public void run(){
	 connectionPool.closeIdleTimeoutConnection();
	}
}
