/*
*Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.net.event;

import org.jmin.net.Connection;

/**
 * 当监听在某个连接上读取到数据触发
 *
 * @author Chris
 */

public class DataReadOutEvent extends ConnectionEvent {

  /**
   * receive data
   */
  private byte[] readData;
  
  /**
   * 将回复的数据
   */
  private byte[] replyData;

  /**
   * Constructor with a source object.
   */
  public DataReadOutEvent(Connection source, byte[] data) {
    super(source);
    this.readData = data;
  }

  /**
   * Return byte data
   * @return
   */
  public byte[] getReadByteArrayData() {
    return readData;
  }
  
  /**
   * 获得需要回复的数据
   */
  public byte[] getReplyByteArrayData(){
  	return this.replyData;
  }
  
  /**
   * 获得需要回复的数据
   */
  public void setReplyByteArray(byte[] replyData){
  	 this.replyData = replyData;
  }
}