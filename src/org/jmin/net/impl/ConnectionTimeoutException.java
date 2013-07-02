/*
 *Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.net.impl;

import java.io.InterruptedIOException;

/**
 * 读超时异常
 *
 * @author Chris
 */
public class ConnectionTimeoutException extends InterruptedIOException {

  /**
   * 构造函数
   */
  public ConnectionTimeoutException() {
    super();
  }

  /**
   * 构造函数
   */
  public ConnectionTimeoutException(String s) {
    super(s);
  }
}