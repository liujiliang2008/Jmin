/*
*Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.net.event;

import org.jmin.net.Connection;

/**
 * When the connection to remote host is accepted in server side, the event will
 * be published.
 *
 * @author Chris
 */

public class ConnectedEvent extends ConnectionEvent {

  /**
   * Constructor with a source object.
   *
   * @param source
   *            even occured from target object.
   */
  public ConnectedEvent(Connection source) {
    super(source);
  }
}