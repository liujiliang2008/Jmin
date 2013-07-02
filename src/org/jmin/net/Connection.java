/*
 *Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.net;

import java.io.IOException;

/**
 * 网络连接
 *
 * @author Chris Liao
 */

public interface Connection {

  /**
   * Return local host
   */
  public ConnectionAddress getLocalHost();

  /**
   * Retturn remote host
   */
  public ConnectionAddress getRemoteHost();

  /**
   * 增加一个数据过滤器，可以拦截一些哪些信息是不可以传送或被接受
   */
  public void addConnectionDataFilter(ConnectionDataFilter filter);

  /**
   * 增加一个数据过滤器，可以拦截一些哪些信息是不可以传送或被接受
   */
  public void removeConnectionDataFilter(ConnectionDataFilter filter);

  /**
   * 增加一个连接事件监听者,一个Server下的所有连接的事件都只对应一个监听
   */
  public void addConnectionEventListener(ConnectionListener listener);

  /**
   * 删除一个连接事件监听者
   */
  public void removeConnectionEventListener(ConnectionListener listener);
  
  

  /**
   * 将数据通过连接输送给远程连接方
   */
  public void write(byte[] data) throws IOException;

  /**
   * 从连接上读出远程发送过来的数据
   */
  public byte[] read() throws IOException;

  /**
   * 在规定的时间范围内从连接上读出远程发送过来的数据,否则算超过时间
   */
  public byte[] read(int time) throws IOException;

  /**
   * 将数据同步写给对方，并需要对方给出回复,默认超过时间为一小时
   */
  public byte[] writeSyn(byte[] data) throws IOException;

  /**
   * 将数据同步写给对方，并需要对方给出回复
   */
  public byte[] writeSyn(byte[] data, int time)throws IOException;

  /**
   * 关闭连接
   */
  public void close()throws IOException;

  /**
   * 将连接运行起来，使其一直处于等待对方数据的状态
   */
  public void run()throws IOException;

  /**
   * 是否关闭
   */
  public boolean isClosed();
  
  /**
   * 将连接运行起来，使其一直处于等待对方数据的状态
   */
  public boolean isRunning();

}
