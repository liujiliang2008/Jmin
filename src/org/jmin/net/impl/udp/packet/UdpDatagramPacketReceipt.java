/*
*Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.net.impl.udp.packet;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * UDP读写回执
 * 
 * @author liao
 */
public class UdpDatagramPacketReceipt {
	/**
	 * 将回执写回对方某端口
	 */
	public static void writeReceipt(DatagramSocket localSocket,InetAddress remoteAddress,int remotePort)throws IOException{
		byte[] clipData = UdpCommands.Data_Packet_Receipt.getBytes();
		DatagramPacket clipPacket = new DatagramPacket(clipData,clipData.length);
		clipPacket.setAddress(remoteAddress);
		clipPacket.setPort(remotePort);
		localSocket.send(clipPacket);
	}

	/**
	 * 读出来自对方某端口的回执
	 */
	public static void readReceipt(DatagramSocket localSocket,InetAddress remoteAddress,int remotePort)throws IOException{
		byte[] clipData = new byte[UdpCommands.Data_Packet_Receipt.length()];	
		DatagramPacket clipPacket = new DatagramPacket(clipData,clipData.length);
		clipPacket.setAddress(remoteAddress);
		clipPacket.setPort(remotePort);
		localSocket.receive(clipPacket);
		String result = new String(clipData);
		
		if(!UdpCommands.Data_Packet_Receipt.equals(result)){
			throw new IOException("Write Exception");
		}
	}
}
