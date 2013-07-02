/*
*Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.net.impl.udp.packet;

/**
 *UDP commands
 *
 *@author Chris
 */

public class UdpCommands {
	
	/**
	 * 请求连接
	 */
	public final static String Connection_Request = "0001";
	
	/**
	 * 接受连接
	 */
	public final static String Connection_Accept = "0002";

	/**
	 * 脉搏跳动查询
	 */
	public final static String Connection_Pulse_Query = "0003";
	
	/**
	 * 脉搏跳动回复
	 */
	public final static String Connection_Pulse_Reply = "0004";

	/**
	 * 普通数据包
	 */
	public final static String Data_Packet = "0005";
	
	/**
	 * 数据包回执
	 */
	public final static String Data_Packet_Receipt = "0006";

}
