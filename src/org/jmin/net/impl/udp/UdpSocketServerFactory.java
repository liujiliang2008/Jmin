/*
 *Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.net.impl.udp;

import java.io.IOException;

import org.jmin.net.ConnectionListener;
import org.jmin.net.NetworkServer;
import org.jmin.net.NetworkServerListener;
import org.jmin.net.event.ServerCreatedEvent;
import org.jmin.net.impl.ProtocolServerFactory;

/**
 * UDP Socket server管理中心
 *
 * @author Chris Liao
 */

public class UdpSocketServerFactory extends ProtocolServerFactory{
	
	/**
	 * 在某个端口上建立一个网络UDP Server
	 */
	public NetworkServer createServer(int serverPort,NetworkServerListener serverListener,ConnectionListener listener)throws IOException{
		UdpSocketServer server = new UdpSocketServer(serverPort);
		server.addServerEventListener(serverListener);
		server.addConnectionEventListener(listener);
		server.getServerEventPublisher().publish(new ServerCreatedEvent(serverPort));
		return server;
	}
}