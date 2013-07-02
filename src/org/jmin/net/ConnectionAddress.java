/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.net;

import java.net.InetAddress;
/**
 * We use this class to describe address for communication host
 *
 * @author Chris Liao
 * @version 1.0
 */

public class ConnectionAddress {

  /**
   * host name
   */
  private InetAddress remoteAddress;

  /**
   * 对方监听的端口
   */
  private int remoteListenPort;

  /**
   * Constructor
   */
  public ConnectionAddress(InetAddress address, int port) {
    this.remoteAddress = address;
    this.remoteListenPort = port;
  }

  /**
   * Return host name
   */
  public InetAddress getRemoteAddress() {
    return remoteAddress;
  }

  /**
   * Return port
   */
  public int getRemoteListenPort() {
    return remoteListenPort;
  }

  public String toString() {
    return this.remoteAddress + ":" + remoteListenPort;
  }

  /**
   * override method
   */
  public int hashCode() {
    return remoteAddress.hashCode() ^ remoteListenPort;
  }

  /**
   * override method
   */
  public boolean equals(Object obj) {
    if (obj instanceof ConnectionAddress) {
    	ConnectionAddress other = (ConnectionAddress) obj;
      return this.remoteAddress.equals(other.remoteAddress)&& this.remoteListenPort == other.remoteListenPort;
    } else {
      return false;
    }
  }

}