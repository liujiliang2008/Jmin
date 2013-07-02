/*
*Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.test.net.tcp;

import org.jmin.net.Connection;
import org.jmin.net.ConnectionFactory;
import org.jmin.net.impl.tcp.TcpConnectionFactory;
import org.jmin.test.net.ClientEventListener;
import org.jmin.test.net.ObjectLocker;

/**
 * TCP连接测试例子
 *
 * @author Chris Liao
 */

public class TcpClient {
	
  /**
   * 测试方法的入口
   */
  public static void main(String args[]) throws Exception {
  	
  	/**
  	 * 发送到Server的消息
  	 */
  	String message ="A";
  	
    /**
     * 创建应用连接管理器
     */
  	ConnectionFactory connectionFactory = new TcpConnectionFactory();

    /**
 		 * 通过应用连接管理器创建一个通向远程Server的连接
 		 * 
 		 * 参数：	服务器地址，服务器端口，本地连接的网络事件监听者
 		 */
  	Connection connection = connectionFactory.openConnection("localhost",9988);
  	 
  	/**
  	 * 增加监视
  	 */
  	connection.addConnectionEventListener(new ClientEventListener());
  	
    /**
     * 发送二进制消息到Server
     */
  	connection.write(message.getBytes());
  	
//  	/**
//  	 * 从远程读取消息
//  	 */
//  	logger.info("Reply: " + new String(connection.read(2000)));
  	
  	
		/**
 	  *创建一个同步对象,并用其阻塞当前线程
 	  */
  	connection.run();
	  ObjectLocker.lock(new Object());
  }
}