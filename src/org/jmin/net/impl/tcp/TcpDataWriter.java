/*
*Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.net.impl.tcp;

import java.io.IOException;
import java.net.Socket;

import org.jmin.net.impl.ConnectionConstants;
import org.jmin.net.impl.util.DigitUtil;
import org.jmin.net.impl.util.Util;

/**
 * 网络输入读写辅助类
 *
 * @author Chris Liao
 * @version 1.0
 */

public class TcpDataWriter {

  /**
   * write data to Socket
   */
  public static void write(Socket socket, byte[] targetData) throws IOException {
    //发送数据头
    byte[] flagData =  DigitUtil.getBytes(ConnectionConstants.FLAG);
    byte[] targetLen = DigitUtil.getBytes(targetData.length);
    byte[] headData = new byte[12];
    System.arraycopy(flagData,0,headData,0,8);
    System.arraycopy(targetLen,0,headData,8,4);
    socket.getOutputStream().write(headData);
    socket.getOutputStream().flush();

    int buffsize = socket.getSendBufferSize();
    int remainLen = targetData.length;
    while (remainLen > 0) {
      if(remainLen >= buffsize){
        socket.getOutputStream().write(targetData,targetData.length -remainLen,buffsize);
        socket.getOutputStream().flush();
        remainLen = remainLen - buffsize;
      } else {
        socket.getOutputStream().write(targetData,targetData.length-remainLen,remainLen);
        socket.getOutputStream().flush();
        remainLen = 0;
      }
    }

    socket.getOutputStream().flush();
    Util.sleep(20);
  }
}