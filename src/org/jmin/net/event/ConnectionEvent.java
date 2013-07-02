/*
*Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.net.event;

import java.util.EventObject;

import org.jmin.net.Connection;
import org.jmin.net.ConnectionAddress;

/**
 * 发生在网络连接器上的事件
 * 
 * @author Chris Liao 
 */
public class ConnectionEvent extends EventObject {
	
	/**
	 * 构造函数
	 */
  public ConnectionEvent(Connection socket) {
    super(socket);
  }
  
  /**
   * 获得远程地址
   */
  public ConnectionAddress getRemoteHost(){
  	return ((Connection)super.getSource()).getRemoteHost();
  }
}