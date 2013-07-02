/*
*Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.net.impl.udp;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Socket 工厂
 *
 *@author Chris
 */

public class UdpSocketFactory {
	
	/**
	 * seq
	 */
	private int nextUDPPort = 0;
	
	/**
	 * 单实例子
	 */
	private static UdpSocketFactory instance;
	
	/**
	 * 获得工厂单实例
	 */
	public synchronized static UdpSocketFactory getInstance(){
		if(instance == null)
			instance = new UdpSocketFactory();
		return instance;
	}
	
	/**
	 * 获得下一个DatagramSocket
	 */
	public synchronized DatagramSocket getNextDatagramSocket()throws IOException{
		if(this.nextUDPPort == Integer.MAX_VALUE)
			this.nextUDPPort = 1;
		
		try{
			this.nextUDPPort++;
			return new DatagramSocket(this.nextUDPPort);
		}catch(SocketException e){
			return getNextDatagramSocket();
		}
	}
	
	/**
	 * 获得下一个DatagramSocket
	 */
	public synchronized DatagramSocket createDatagramSocket(int port)throws IOException{
		return new DatagramSocket(port);
	}
	
	/**
	 * 
	 */
	public static void main(String[] args)throws IOException{
		for(int i=1;i<100;i++){
			DatagramSocket socket = UdpSocketFactory.getInstance().getNextDatagramSocket();
			System.out.println(socket.getLocalPort());
		}
	}
}
