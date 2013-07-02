/*
*Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.net.event;

import org.jmin.net.Connection;

/**
 * 当监听开始在某个连接上读取数据的时候触发
 *
 * @author Chris
 */

public class DataReadEvent extends ConnectionEvent {

  /**
   * Constructor with a source object.
   */
  public DataReadEvent(Connection source) {
    super(source);
  }
}