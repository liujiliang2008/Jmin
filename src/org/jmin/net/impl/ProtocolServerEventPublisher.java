/*
*Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.net.impl;
import java.util.EventObject;
import java.util.Observable;

/**
 * A object to publish event, then listeners can catch them and handle them.
 *
 * @author Chris Liao
 */

public class ProtocolServerEventPublisher extends Observable{

  /**
   * Method to publish a net event
   *
   * @param event target object
   */
  public void publish(EventObject event){
    this.setChanged();
    this.notifyObservers(event);
    this.clearChanged();
  }
}