/*
*Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.net.event;

/**
 * Socket server事件
 *
 * @author Chris Liao
 */

public class SocketServerEvent extends java.util.EventObject{
	
 /**
  * Server port
  */
  private int serverPort;

  /**
   * Event constructor
   */
  public SocketServerEvent(int serverPort) {
  	super("Localhost");
    this.serverPort = serverPort;
  }
  
  /**
   * Return port
   */
  public int getPort(){
	  return this.serverPort;
  }
  
  /**
   * Hash code
   */
  public int hashCode(){
  	return this.serverPort;
  }
  
  /**
   * equals method
   */
  public boolean equals(Object obj){
  	if(obj instanceof SocketServerEvent){
  		SocketServerEvent other = (SocketServerEvent)obj;
  		return (this.serverPort == other.serverPort);
  	}else{
  		return false;
  	}
  }
  
  /**
   * override method
   */
  public String toString(){
	  return "Localhost:" + serverPort;
  }
}
