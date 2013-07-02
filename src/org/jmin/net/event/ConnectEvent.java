/*
*Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.net.event;

import org.jmin.net.Connection;

/**
 * When receive a connection request, the event will be created.
 *
 * @author Chris
 */

public class ConnectEvent extends ConnectionEvent {

	 /**
   * Constructor with a source object
   */
  public ConnectEvent(Connection source) {
    super(source);
  }
  
}