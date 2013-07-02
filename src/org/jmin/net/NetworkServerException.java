/*
 *Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.net;

import java.net.SocketException;

/**
 * 网络服务器异常
 *
 * @author Chris Liao
 */

public class NetworkServerException extends SocketException {

  /**
   * 构造函数
   */
  public NetworkServerException() {
    super();
  }

  /**
   * 构造函数
   */
  public NetworkServerException(String s) {
    super(s);
  }
}
