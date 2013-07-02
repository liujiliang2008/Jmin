/*
*Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.net.event;

import org.jmin.net.Connection;

/**
 * When remote host close connection,this event will be published as
 * notification.
 *
 * @author Chris
 */

public class DisconnectEvent extends ConnectionEvent {

  /**
   * Constructor with a source object.
   *
   * @param source
   *            even occured from target object.
   */
  public DisconnectEvent(Connection source) {
    super(source);
  }
}