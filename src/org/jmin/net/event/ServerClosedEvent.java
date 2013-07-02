/*
*Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.net.event;

/**
 * Server关闭事件
 * 
 * @author Chris
 */
public class ServerClosedEvent extends SocketServerEvent {

	/**
	 * Constructor with a source object.
	 */
	public ServerClosedEvent(int port ) {
		super(port);
	}

}