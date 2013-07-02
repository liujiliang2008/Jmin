/*
 *Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.net;

import java.net.SocketException;

/**
 * 网络连接异常
 *
 * @author Chris Liao
 */

public class ConnectionException extends SocketException {

  /**
   * 构造函数
   */
  public ConnectionException() {
  }

  /**
   * 构造函数
   */
  public ConnectionException(String msg) {
    super(msg);
  }
}
