/*
*Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.net;

import org.jmin.net.event.ConnectEvent;
import org.jmin.net.event.ConnectedEvent;
import org.jmin.net.event.DisconnectEvent;
import org.jmin.net.event.ServerClosedEvent;
import org.jmin.net.event.ServerCreatedEvent;
import org.jmin.net.event.ServerErrorEvent;

/**
 * A listener run in service side and catch connection request event from
 * client.
 *
 * @author Chris Liao
 */

public interface NetworkServerListener  {

  /**
   * When server is created,then occur event
   */
  public void onCreated(ServerCreatedEvent event);

  /**
   * When server is closed,then post the event
   */
  public void onClosed(ServerClosedEvent event);

  /**
   * When server is closed,then post the event
   */
  public void onError(ServerErrorEvent event);

  /**
   * When connection request coming,this method will be called
   */
  public void onConnect(ConnectEvent event);

  /**
   * Method run after acceptance in server side.
   */
  public void onConnected(ConnectedEvent event);

  /**
   * Method run after remote host close connection.
   */
  public void onDisconnected(DisconnectEvent event);

}