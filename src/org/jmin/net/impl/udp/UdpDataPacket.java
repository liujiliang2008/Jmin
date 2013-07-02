/*
*Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.net.impl.udp;

import java.net.InetAddress;

/**
 * UDP数据
 *
 * @author Chris Liao 
 */

public class UdpDataPacket {

  /**
   * 远程端口
   */
  private int remotePort;
  
  /**
   * host name
   */
  private InetAddress remoteAddress;

	/**
	 * 目标数据
	 */
	private byte[] data;
	
	/**
	 * 构造函数
	 */
	public UdpDataPacket(InetAddress remoteAddress,int remotePort) {
	 this.remoteAddress = remoteAddress;
	 this.remotePort = remotePort;
	}
	
	
	/**
	 * 远程读数据的端口
	 */
	public int getRemotePort() {
		return remotePort;
	}
	
	
	/**
	 * 远程读数据的机器地址
	 */
	public InetAddress getremoteAddress() {
		return remoteAddress;
	}
	
	/**
	 * 获得网络数据
	 */
	public void setData(byte[] data) {
		this.data = data;
	}

	/**
	 * 目标数据
	 */
	public byte[] getByteArrayData() {
		return data;
	}
}
