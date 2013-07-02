/*
 *Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.net.impl.tcp;

import org.jmin.net.impl.ProtocolConnectionThread;

/**
 * TCP Socket Thread
 *
 *@author Chris
 */

public class TcpConnectionThread extends ProtocolConnectionThread {

	/**
	 * 构造函数
	 */
	public TcpConnectionThread(TcpConnection connection) {
		super(connection);
	}
}