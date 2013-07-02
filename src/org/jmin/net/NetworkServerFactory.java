/*
 *Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.net;

import java.io.IOException;

/**
 * 网络服务器工厂
 *
 * @author Chris Liao
 */
public interface NetworkServerFactory {

  /**
   * 在某个端口上建立一个网络Server
   */
  public NetworkServer createServer(int serverPort,NetworkServerListener serverListener,ConnectionListener listener) throws IOException;
  
}
