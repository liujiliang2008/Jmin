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

public class UdpDatagramPacketReader {
	
	/**
	 * 从DatagramSocket上读出一个数据包,
	 * 
	 * 利用本地读Socket读出来自远程机器端口数据,收到数据后然后将回执写回.
	 */
	public static DatagramPacket read(DatagramSocket localReadSocket) throws IOException {
		byte[] readData = new byte[localReadSocket.getReceiveBufferSize()];
		DatagramPacket readPacket = new DatagramPacket(readData,readData.length);
		localReadSocket.receive(readPacket);
		UdpDatagramPacketReceipt.writeReceipt(localReadSocket,readPacket.getAddress(),readPacket.getPort());
		return readPacket;
	}
	
	/**
	 * 
	 * 从DatagramSocket上读出一个数据包,
	 * 
	 * 利用本地读Socket读出来自远程机器端口数据,收到数据后然后将回执写回.
	 */
	public static DatagramPacket read(DatagramSocket localReadSocket,InetAddress remoteAddress,int remotePort) throws IOException {
		byte[] readData = new byte[localReadSocket.getReceiveBufferSize()];
		DatagramPacket readPacket = new DatagramPacket(readData,readData.length);
		readPacket.setAddress(remoteAddress);
		readPacket.setPort(remotePort);
		localReadSocket.receive(readPacket);
		UdpDatagramPacketReceipt.writeReceipt(localReadSocket,readPacket.getAddress(),readPacket.getPort());
		return readPacket;
	}
}
