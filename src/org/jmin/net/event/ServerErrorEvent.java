/*
*Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.net.event;

/**
 * Server上发生异常
 * 
 * @author Chris
 */
public class ServerErrorEvent extends SocketServerEvent {

  /**
   * caused exception
   */
  public Throwable detail;

  public ServerErrorEvent(int serverPort,Throwable detail) {
    super(serverPort);
    this.detail = detail;
  }

  /**
   * Return caused throwable.
   */
  public Throwable getCause() {
    return detail;
  }

  /**
   * override method
   */
  public String toString() {
    return "Error occured in server port:" + this.getPort() + ",caused: " +detail.toString();
  }
}