/*
*Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.net.impl;

/**
 * Socket server Thread
 *
 * @author Chris
 */

public abstract class ProtocolServerThread extends Thread{
	
	/**
	 * 当前Thread的启动Thread
	 */
	private Thread startThread;
	
	/**
	 * 当前Thread需要监听的socketServer
	 */
	private ProtocolServer socketServer;
	
	/**
	 * 构造函数
	 */
	public ProtocolServerThread(ProtocolServer socketServer){
		this.socketServer = socketServer;
	}
	
	/**
	 * 启动Thread 
	 */
	public void start(){
		this.startThread = Thread.currentThread();
		super.start();
	}
	
	/**
	 * 获得启动Thread是否还处于活动状态
	 */
	protected boolean startThreadActive(){
		return startThread.isAlive();
	}
	
	/**
	 * 获得需要监听的socketServer
	 */
	protected ProtocolServer getProtocolSocketServer(){
		return this.socketServer;
	}
}
