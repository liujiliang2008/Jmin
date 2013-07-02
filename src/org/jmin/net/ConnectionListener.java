/*
*Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.net;

import org.jmin.net.event.DataReadEvent;
import org.jmin.net.event.DataReadOutEvent;
import org.jmin.net.event.DataWriteEvent;
import org.jmin.net.event.DataWriteInEvent;
import org.jmin.net.event.ErrorEvent;

/**
 * 网络连接器上的事件监听器
 * 
 * @author Chris Liao 
 */

public interface ConnectionListener {
	
  /**
   * Read data from remote host
   */
  public void onRead(DataReadEvent event);
  
  /**
   * Read data from remote host
   */
  public void onReadOut(DataReadOutEvent event);
  
  /**
   * Write data to remote host
   */
  public void onWrite(DataWriteEvent event);
  
  /**
   * Write data to remote host
   */
  public void onWriteIn(DataWriteInEvent event);
  
  /**
   * When errors occur during communication
   */
  public void onError(ErrorEvent event);

}
