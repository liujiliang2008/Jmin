/*
 *Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.net.impl.udp;

import java.util.TimerTask;

/**
 * 脉搏跳动任务类
 *
 * @author Chris Liao
 */

public class UdpConnectionPulseTask extends TimerTask{
	
	/**
	 * UdpSocketThread
	 */
	private UdpSocketServerThread thread = null;
	
	/**
	 * 构造函数
	 */
	public UdpConnectionPulseTask(UdpSocketServerThread thread){
		this.thread = thread;
	}
	
	/**
	 * 任务方法
	 */
	public void run(){
		this.thread.checkRemotePlus();
	}
}
