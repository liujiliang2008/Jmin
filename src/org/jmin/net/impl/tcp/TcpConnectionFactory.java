/*
 *Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.net.impl.tcp;

import java.io.IOException;
import java.net.Socket;

import org.jmin.net.Connection;
import org.jmin.net.ConnectionListener;
import org.jmin.net.impl.ProtocolConnectionFactory;

/**
 * 网络连接工厂
 *
 * @author Chris Liao
 */

public class TcpConnectionFactory extends  ProtocolConnectionFactory{
	
  /**
   * 创建连接到Server的连接
   */
  public Connection openConnection(String host,int port) throws IOException{
  	return this.openConnection(host,port,null);
  }

	/**
	 * 连接到TCP Server
	 */
	public Connection openConnection(String serverHost,int serverPort,ConnectionListener listener)throws IOException{
		Socket socket = new Socket(serverHost,serverPort);
//		socket.setTcpNoDelay(false);
//		socket.setKeepAlive(true);
		Connection con = new TcpConnection(socket);
		if(listener!=null)
			con.addConnectionEventListener(listener);
		return con;
	}
}