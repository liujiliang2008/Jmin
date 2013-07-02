/*
 *Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.test.net;

import org.jmin.log.Logger;
import org.jmin.net.ConnectionListener;
import org.jmin.net.NetworkServerListener;
import org.jmin.net.event.ConnectEvent;
import org.jmin.net.event.ConnectedEvent;
import org.jmin.net.event.DataReadEvent;
import org.jmin.net.event.DataReadOutEvent;
import org.jmin.net.event.DataWriteEvent;
import org.jmin.net.event.DataWriteInEvent;
import org.jmin.net.event.DisconnectEvent;
import org.jmin.net.event.ErrorEvent;
import org.jmin.net.event.ServerClosedEvent;
import org.jmin.net.event.ServerCreatedEvent;
import org.jmin.net.event.ServerErrorEvent;

/**
 * Server的事件监听
 *
 * @author Chris Liao
 */

public class ServerEventListener implements NetworkServerListener,ConnectionListener {

	/**
	 * 日记打印
	 */
	private Logger log = Logger.getLogger(ServerEventListener.class);

	/**
	 *创建Server触发事件
	 */
	public void onCreated(ServerCreatedEvent event) {

		/**
		 * 打印Server的端口
		 */
		log.info("Created server on port: " + event.getPort());
	}

	/**
	 * Server发生错误时触发事件
	 */
	public void onError(ServerErrorEvent event) {

		/**
		 * 打印错误信息
		 */
		log.info("Catched a server exception: " + event.detail);
	}

	/**
	 * 当Server关闭时触发事件
	 */
	public void onClosed(ServerClosedEvent event) {

		/**
		 * print server close event
		 */
		log.info("Closed server on port: " + event.getPort());
	}

	/**
	 *当有远程请求连接的时候,触发事件
	 */
	public void onConnect(ConnectEvent event) {

		/**
		 * 打印消息来源
		 */
		log.info("Found request connection: " + event.getRemoteHost());
	}

	/**
	 * 当远程已经被接受时候,触发事件
	 */
	public void onConnected(ConnectedEvent event) {

		/**
		 * 打印已经被接受的对方连接地址
		 */
		log.info("Accepted connection: " + event.getRemoteHost());
	}

	/**
	 * 当有客户端关闭连接时候,触发事件
	 */
	public void onDisconnected(DisconnectEvent event) {

		/**
		 * 打印客户端的网络地址
		 */
		log.info("Found disconnected connection: "+event.getRemoteHost()+"]");
	}
	

	
	
	
  //以下事件发生在Server的Connection上
	/**
   * 当读取到Server发送过来的消息，将会触发这个事件
   */
  public void onRead(DataReadEvent event) {
  	
    /**
     * 打印读取数据Connection
     */
    log.info("Begin to read data from connection: "+ event.getSource()) ;
  }
  
  /**
   * 当读取到Server发送过来的消息，将会触发这个事件
   */
  public void onReadOut(DataReadOutEvent event) {

    /**
     * 获取发送过来的Byte数据
     */
    byte[] data = event.getReadByteArrayData();

    /**
     * 打印读取到的数据
     */
    log.info("Read out data [" + new String(data)  + "] from connection: "+ event.getSource()) ;
    
		/**
		 * 回复给客户端的消息
		 */
		String replyMessage ="Received,thanks";
		
		/**
		 * 设置给远程的回复
		 */
		event.setReplyByteArray(replyMessage.getBytes());
  }
  
  /**
   * 写数据到Server时触发的事件
   */
  public void onWrite(DataWriteEvent event) {
  	
 	 /**
     *获得要发送的数据
     */
    byte[] data = event.getWriteByteArrayData();
 
    /**
     * 打印要写数据Connection
     */
    log.info("Begin to write data [" + new String(data)  + "] to connection: "+ event.getSource()) ;
  }
  
  /**
   * 写数据到Server时触发的事件
   */
  public void onWriteIn(DataWriteInEvent event) {
  	
  	 /**
     *获得要发送的数据
     */
    byte[] data = event.getWroteByteArrayData();

    /**
     * 打印要发送的数据
     */
    log.info("Finished writting data [ " + new String(data) + "] to connection: "+ event.getSource());
  }
 
  /**
   * 当在网络连接上发生错误的时候，触发该错误。
   */
  public void onError(ErrorEvent event) {

    /**
     * 获取异常
     */
    Throwable cause = event.getCause();

    /**
     * 打印异常
     */
    log.error("Catched a exception:", cause);
  }
}