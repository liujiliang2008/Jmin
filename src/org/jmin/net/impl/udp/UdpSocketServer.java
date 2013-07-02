/*
 *Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.net.impl.udp;

import java.io.IOException;
import java.net.DatagramSocket;

import org.jmin.net.NetworkServerException;
import org.jmin.net.impl.ProtocolServer;

/**
 * 为了提高UDP数据传输性,提供了两个端口：一个专门用来读取数据，一个用来专门写数据
 * 
 * @author Chris Liao 
 */
public class UdpSocketServer  extends ProtocolServer {
	
	/**
	 * 数据读Socket,专门用来读取用户连接请求数据
	 */
	private DatagramSocket readSocket;

	/**
	 * 数据写Socket,专门用来回复网络数据
	 */
	private DatagramSocket writeSocket;
	
	/**
	 * 构造函数
	 */
	public UdpSocketServer(int port)throws IOException{
		super(port);
		this.readSocket = UdpSocketFactory.getInstance().createDatagramSocket(port);
		this.writeSocket = UdpSocketFactory.getInstance().getNextDatagramSocket();
	}
	
	/**
	 * Server Socket
	 */
	public DatagramSocket getReadSocket(){
		return this.readSocket;
	}
	
	/**
	 * Server Socket
	 */
	public DatagramSocket getWriteSocket(){
		return this.writeSocket;
	}

	/**
	 * 让服务器运行起来
	 */
	public void run()throws IOException{
		if(this.isRunning()){
			 throw new NetworkServerException("Server is running");
		}else{
			this.socketServerThread = new UdpSocketServerThread(this);
		  this.socketServerThread.setDaemon(true);
	  	this.socketServerThread.start();
  	}
	}
	
	/**
	 * 关闭Server
	 */
	public void close()throws IOException{
		if(this.isClosed()){
			 throw new NetworkServerException("Server is closed");
		}else{
		  this.isClosed = true;
			this.readSocket.close();
			this.writeSocket.close();
			this.socketServerThread.interrupt();
		}
	}
}