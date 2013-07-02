/*
*Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.test.net;

import org.jmin.log.Logger;
import org.jmin.net.ConnectionListener;
import org.jmin.net.event.DataReadEvent;
import org.jmin.net.event.DataReadOutEvent;
import org.jmin.net.event.DataWriteEvent;
import org.jmin.net.event.DataWriteInEvent;
import org.jmin.net.event.ErrorEvent;

/**
 * 客户端 连接事件的监听
 *
 * @author Chris Liao
 */

public class ClientEventListener implements ConnectionListener {

  /**
   * 日记打印
   */
  private Logger log = Logger.getLogger(ClientEventListener.class);

  private byte[] data1 = new byte[10];


  public ClientEventListener(){
    for(int i=0;i<data1.length;i++){
      data1[i]='A';
    }
  }

  /**
   * 当读取Server发送过来的消息，将会触发这个事件
   */
  public void onRead(DataReadEvent event) {

    /**
     * 打印读取数据Connection
     */
    log.info("Begin to read data from connection: " + event.getSource());
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
    log.info("Read out data [" + new String(data) + "] from connection: " + event.getSource());

    /**
     * 放置回复数据
     */
    event.setReplyByteArray(data1);
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
    log.info("Begin to write data [" + new String(data) + "] to connection: " + event.getSource());
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
    log.info("Finished writting data [" + new String(data) + "] to connection: " + event.getSource());
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
    log.error("Catched a exception:" + cause.getMessage() + "]");
  }
}