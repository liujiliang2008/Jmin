/*
*Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.net.impl.udp;

import java.net.DatagramSocket;
import java.net.InetAddress;

import org.jmin.net.ConnectionAddress;

/**
 * UDP连接地址描述类
 *
 *@author Chris
 */

public class UdpConnectionAddress extends ConnectionAddress{
	
  /**
   * 远程的读端口
   */
  private int remoteDataReadPort;
  
  /**
   * 远程的写端口
   */
  private int remoteDataWritePort;
 
  /**
   * 远程的脉搏读口
   */
  private int remotePlusReadPort;
	
  /**
   * 远程的脉搏写口
   */
  private int remotePlusWritePort;
	
	 /**
   * 本地数据读Socket
   */
  private DatagramSocket localDataReadSocket;
  
  /**
   * 本地数据写Socket
   */
  private DatagramSocket localDataWriteSocket;
  
	/**
	 * 本地脉搏读Socket;
	 */
	private DatagramSocket localPulseReadSocket;
	
	/**
	 * 本地脉搏跳动写Socket;
	 */
	private DatagramSocket localPulseWriteSocket;
	
  /**
   * Constructor
   */
  public UdpConnectionAddress(InetAddress address, int port) {
     super(address,port);
  }

  /**
   * 本地数据读Socket
   */
	public DatagramSocket getLocalDataReadSocket() {
		return localDataReadSocket;
	}
	
	 /**
   * 本地数据读Socket
   */
	public void setLocalDataReadSocket(DatagramSocket localDataReadSocket) {
		this.localDataReadSocket = localDataReadSocket;
	}
	
  /**
   * 本地数据写Socket
   */
	public DatagramSocket getLocalDataWriteSocket() {
		return localDataWriteSocket;
	}
	
  /**
   * 本地数据写Socket
   */
	public void setLocalDataWriteSocket(DatagramSocket localDataWriteSocket) {
		this.localDataWriteSocket = localDataWriteSocket;
	}
	
	/**
	 * 本地脉搏读Socket;
	 */
	public DatagramSocket getLocalPulseReadSocket() {
		return localPulseReadSocket;
	}
	
	/**
	 * 本地脉搏读Socket;
	 */
	public void setLocalPulseReadSocket(DatagramSocket localPulseReadSocket) {
		this.localPulseReadSocket = localPulseReadSocket;
	}
	
	/**
	 * 本地脉搏跳动写Socket;
	 */
	public DatagramSocket getLocalPulseWriteSocket() {
		return localPulseWriteSocket;
	}
	
	/**
	 * 本地脉搏跳动写Socket;
	 */
	public void setLocalPulseWriteSocket(DatagramSocket localPulseWriteSocket) {
		this.localPulseWriteSocket = localPulseWriteSocket;
	}

	 /**
   * 远程的读端口
   */
	public int getRemoteDataReadPort() {
		return remoteDataReadPort;
	}
	
	 /**
   * 远程的读端口
   */
	public void setRemoteDataReadPort(int remoteDataReadPort) {
		this.remoteDataReadPort = remoteDataReadPort;
	}
	
	/**
   * 远程的写端口
   */
	public int getRemoteDataWritePort() {
		return remoteDataWritePort;
	}
	
	/**
   * 远程的写端口
   */
	public void setRemoteDataWritePort(int remoteDataWritePort) {
		this.remoteDataWritePort = remoteDataWritePort;
	}
	
	 /**
   * 远程的脉搏读口
   */
	public int getRemotePlusReadPort() {
		return remotePlusReadPort;
	}
	
	 /**
   * 远程的脉搏读口
   */
	public void setRemotePlusReadPort(int remotePlusReadPort) {
		this.remotePlusReadPort = remotePlusReadPort;
	}
	
	 /**
   * 远程的脉搏写口
   */
	public int getRemotePlusWritePort() {
		return remotePlusWritePort;
	}
	
	 /**
   * 远程的脉搏写口
   */
	public void setRemotePlusWritePort(int remotePlusWritePort) {
		this.remotePlusWritePort = remotePlusWritePort;
	}
}
