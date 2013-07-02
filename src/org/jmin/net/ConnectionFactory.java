/*
 *Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.net;

import java.io.IOException;

/**
 * 网络连接工厂
 *
 * @author Chris Liao
 */

public interface ConnectionFactory {
	
  /**
   * 创建连接到Server的连接
   */
  public Connection openConnection(String host,int port) throws IOException;

  /**
   * 创建连接到Server的连接
   */
  public Connection openConnection(String host,int port,ConnectionListener listener) throws IOException;

}
