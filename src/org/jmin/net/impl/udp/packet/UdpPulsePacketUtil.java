/*
 *Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.net.impl.udp.packet;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * UDP连接的脉搏跳动,连接一方向另一方发送脉搏跳动询问,等待对方的回复， 如果长时间未回复，则认为通过对方的连接已经失效
 * 
 * @author Chris Liao
 */

public class UdpPulsePacketUtil {

	/**
	 * 发送请求脉搏探测命令，等待对方的回复
	 */
	public static void sendPulseQuery(DatagramSocket localSocket,InetAddress remoteAddress, int remotePort)throws IOException {
		byte[] queryData = UdpCommands.Connection_Pulse_Query.getBytes();
		DatagramPacket queryPacket = new DatagramPacket(queryData, queryData.length);
		queryPacket.setAddress(remoteAddress);
		queryPacket.setPort(remotePort);
		localSocket.send(queryPacket);
		
		byte[] replyData = new byte[UdpCommands.Connection_Pulse_Reply.length()];
		DatagramPacket replyPacket = new DatagramPacket(replyData, replyData.length);
		replyPacket.setAddress(queryPacket.getAddress());
		replyPacket.setPort(queryPacket.getPort());
		localSocket.receive(replyPacket);
		String reply = new String(replyData);
		if(!UdpCommands.Connection_Pulse_Reply.equals(reply)){
			throw new IOException("remote connecti");
		}
	}
		
	/**
	 * 读取对方脉搏跳动的回复
	 */
	public static void sendPulseReply(DatagramSocket localSocket,InetAddress remoteAddress, int remotePort)throws IOException {
		byte[] clipData = UdpCommands.Connection_Pulse_Reply.getBytes();
		DatagramPacket clipPacket = new DatagramPacket(clipData,clipData.length);
		clipPacket.setAddress(remoteAddress);
		clipPacket.setPort(remotePort);
		localSocket.send(clipPacket);
	}
}
