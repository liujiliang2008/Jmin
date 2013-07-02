/*
*Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.net.event;

/**
 * Server创建事件
 * 
 * @author Chris
 */
public class ServerCreatedEvent extends SocketServerEvent {
	
  /**
   * Constructor with a source object.
   */
  public ServerCreatedEvent(int port) {
    super(port);
  }
}