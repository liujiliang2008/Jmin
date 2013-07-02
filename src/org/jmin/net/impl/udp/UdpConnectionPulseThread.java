/*
 *Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.net.impl.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import org.jmin.net.impl.udp.packet.UdpCommands;
import org.jmin.net.impl.udp.packet.UdpPulsePacketUtil;

/**
 * 脉搏读取回复线程
 *
 * @author Chris Liao
 */

public class UdpConnectionPulseThread extends Thread{
	/**
	 * 读取脉搏请求的的Thread 
	 */
	private DatagramSocket pulseSocket;
	
	/**
	 * 构造方法
	 */
	public UdpConnectionPulseThread(DatagramSocket pulseSocket){
		this.pulseSocket = pulseSocket;
	}
	
	/**
	 * 线程方法
	 */
	public void run(){
		while(true){
			try{
				byte[] clipData = new byte[UdpCommands.Connection_Pulse_Query.length()];
				DatagramPacket clipPacket = new DatagramPacket(clipData, clipData.length);
				this.pulseSocket.receive(clipPacket);
				String result = new String(clipData);
				if(UdpCommands.Connection_Pulse_Query.equals(result)){//有远程方在查询脉动
					UdpPulsePacketUtil.sendPulseReply(this.pulseSocket,clipPacket.getAddress(),clipPacket.getPort());
				}
			}catch(SocketException e){
				break;
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
}
