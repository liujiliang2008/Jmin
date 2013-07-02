/*
*Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.net.impl.udp.packet;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * UDP输出的辅助类
 * 
 * @author liao
 */

public class UdpDatagramPacketWriter {
	
	/**
	 * 在DatagramSocket上写出一个数据包
	 */
	public static void write(DatagramSocket localWriteSocket,DatagramPacket packet)throws IOException {
		localWriteSocket.send(packet);
		UdpDatagramPacketReceipt.readReceipt(localWriteSocket,packet.getAddress(),packet.getPort());
	}
	
	/**
	 * 在DatagramSocket上写出一个数据包
	 */
	public static void write(DatagramSocket localWriteSocket,InetAddress address,int remoteReadPort,byte[] data)throws IOException {
		DatagramPacket packet = new DatagramPacket(data,data.length);
		packet.setAddress(address);
	  packet.setPort(remoteReadPort);
		write(localWriteSocket,packet);
	}
	
	/**
	 * 在DatagramSocket上写出一个数据包
	 */
	public static void write(DatagramSocket localWriteSocket,InetAddress address,int remoteReadPort,byte[] data,int offset,int length)throws IOException {
		DatagramPacket packet = new DatagramPacket(data,offset,data.length);
		packet.setAddress(address);
	  packet.setPort(remoteReadPort);
		write(localWriteSocket,packet);
	}
	
}
