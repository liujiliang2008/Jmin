/*
 *Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.net;

/**
 * 网络连接器上的事件监听器
 *
 * @author Chris Liao
 */

public interface ConnectionDataFilter {

  /**
   * 拦截读取出来的数据
   */
  public void beforeRead(ConnectionAddress address) throws SecurityException;

  /**
   * 拦截读取出来的数据
   */
  public void afterRead(ConnectionAddress address, byte[] data) throws SecurityException;

  /**
   * 拦截将要写入的数据
   */
  public void beforeWrite(ConnectionAddress address, byte[] data) throws SecurityException;

  /**
   * 拦截将要写入的数据
   */
  public void afterWrite(ConnectionAddress address, byte[] data) throws SecurityException;

}