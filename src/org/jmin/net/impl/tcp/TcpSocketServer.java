/*
 *Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.net.impl.tcp;

import java.io.IOException;
import java.net.ServerSocket;

import org.jmin.net.NetworkServerException;
import org.jmin.net.impl.ProtocolServer;

/**
 * TCP server定义
 * 
 * @author Chris Liao 
 */
public class TcpSocketServer extends ProtocolServer {
	
	/**
	 * Server socket
	 */
	private ServerSocket serverSocket;
	
	/**
	 * 构造函数
	 */
	public TcpSocketServer(int port)throws IOException {
		super(port);
		this.serverSocket = new ServerSocket(port);
	}

	/**
	 * Server Socket
	 */
	public ServerSocket getServerSocket(){
		return serverSocket;
	}
	
	/**
	 * 让服务器运行起来
	 */
	public void run()throws IOException{
		if(this.isRunning()){
			 throw new NetworkServerException("Server is running");
		}else{
			this.socketServerThread = new TcpSocketServerThread(this);
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
		 try{
		  this.isClosed = true;
			this.serverSocket.close();
			this.socketServerThread.interrupt();
		 }catch(IOException e){
		 }
	 }
	}
}
