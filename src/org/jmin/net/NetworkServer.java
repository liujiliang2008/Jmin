/*
 *Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.net;

import java.io.IOException;

/**
 * 网络服务器
 *
 * @author Chris Liao
 */
public interface NetworkServer {

  /**
   * 添加一个过滤器，可以决定来自哪些机器的请求是可以接受的,担当Firewall
   */
  public void addConnectionFilter(ConnectionFilter filter);

  /**
   * 添加一个过滤器，可以决定来自哪些机器的请求是可以接受的,担当Firewall
   */
  public void removeConnectionFilter(ConnectionFilter filter);

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
   * 增加一个服务器事件监听者
   */
  public void addServerEventListener(NetworkServerListener listener);

  /**
   * 删除一个服务器事件监听者
   */
  public void removeServerEventListener(NetworkServerListener listener);

  /**
   * 关闭Server
   */
  public void close()throws IOException;

  /**
   * 让Server运行起来，并处于等待接收客户的连接请求的状态
   */
  public void run() throws IOException;
  
  /**
   * 是否已经关闭
   */
  public boolean isClosed();
  
  /**
   * 是否处于运行状态
   */
  public boolean isRunning();
  
}
