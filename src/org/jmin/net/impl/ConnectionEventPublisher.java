/*
*Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.net.impl;
import java.util.Observable;

import org.jmin.net.event.ConnectionEvent;

/**
 * A object to publish event, then listeners can catch them and handle them.
 *
 * @author Chris Liao
 */

public class ConnectionEventPublisher extends Observable{

  /**
   * Method to publish a net event
   */
  public void publish(ConnectionEvent event){
    this.setChanged();
    this.notifyObservers(event);
    this.clearChanged();
  }
}