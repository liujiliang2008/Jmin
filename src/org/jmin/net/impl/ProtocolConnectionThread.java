/*
 *Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.net.impl;

/**
 * 连接的Thread
 * 
 * @author Chris Liao
 */
public class ProtocolConnectionThread extends Thread{
	
//	/**
//	 * Logger
//	 */
//	private static Logger logger = Logger.getLogger(ProtocolConnectionThread.class);
	
	/**
	 * 网络Socket
	 */
	private ProtocolConnection connection;

	/**
	 * 构造函数
	 */
	public ProtocolConnectionThread(ProtocolConnection connection) {
		this.connection = connection;
	}

	/**
	 * 线程方法
	 */
	public void run() {
		while (!connection.isClosed()) {
			try {
				connection.read();
			} catch (Throwable e) {
				//logger.debug(e);
			}
		}
	}
}