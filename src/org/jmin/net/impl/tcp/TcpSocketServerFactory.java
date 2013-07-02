/*
 *Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.net.impl.tcp;

import java.io.IOException;

import org.jmin.net.ConnectionListener;
import org.jmin.net.NetworkServer;
import org.jmin.net.NetworkServerListener;
import org.jmin.net.event.ServerCreatedEvent;
import org.jmin.net.impl.ProtocolServerFactory;

/**
 * TCP Socket server管理中心
 *
 * @author Chris Liao
 */

public class TcpSocketServerFactory extends ProtocolServerFactory{
	
	/**
	 * 在某个端口上建立一个网络TCP Server
	 */
	public NetworkServer createServer(int serverPort,NetworkServerListener serverListener,ConnectionListener listener)throws IOException{
		TcpSocketServer server = new TcpSocketServer(serverPort);
		server.addServerEventListener(serverListener);
		server.addConnectionEventListener(listener);
		server.getServerEventPublisher().publish(new ServerCreatedEvent(serverPort));
		return server;
	}
}