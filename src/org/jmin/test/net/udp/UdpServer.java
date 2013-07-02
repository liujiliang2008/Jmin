/*
*Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.test.net.udp;

import java.io.IOException;

import org.jmin.net.ConnectionListener;
import org.jmin.net.NetworkServer;
import org.jmin.net.NetworkServerFactory;
import org.jmin.net.NetworkServerListener;
import org.jmin.net.impl.udp.UdpSocketServerFactory;
import org.jmin.test.net.ObjectLocker;
import org.jmin.test.net.ServerEventListener;

/**
 * UDP Server测试例子
 * 
 * 
 * @author Chris Liao 
 */

public class UdpServer {

  /**
   * 测试方法入口
   */
  public static void main(String args[]) throws IOException {
  	
    /**
     * 打开一个应用Server的管理器
     */
  	NetworkServerFactory serverFactory = new UdpSocketServerFactory();

    /**
     * 创建一个Server事件的监听者
     */
  	NetworkServerListener serverListener = new ServerEventListener();
    
    /**
     * 创建一个Server
     */
  	NetworkServer server = serverFactory.createServer(9988,serverListener,(ConnectionListener)serverListener);
    
    /**
     *让Server运行起来
     */
    server.run();
    
    /**
     *创建一个同步对象,并用其阻塞当前线程
     */
    ObjectLocker.lock(new Object());
  }
}