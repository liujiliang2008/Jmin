/*
*Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.net.impl.udp;

import org.jmin.net.impl.ProtocolConnectionThread;

/**
 * Socket connector
 *
 *@author Chris
 */

public class UdpConnectionThread extends ProtocolConnectionThread {
	
  /**
   * 构造函数
   */
  public UdpConnectionThread(UdpConnection connection) {
  	super(connection);
  }
 
 
}