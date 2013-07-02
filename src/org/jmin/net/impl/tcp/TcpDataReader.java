/*
*Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.net.impl.tcp;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

import org.jmin.net.impl.ConnectionConstants;
import org.jmin.net.impl.util.DigitUtil;

/**
 * 网络输出的读写辅助类
 *
 * @author Chris Liao
 * @version 1.0
 */
public class TcpDataReader {

  /**
   * read data from Socket
   */
  public static byte[] read(Socket socket) throws IOException {
    byte[] headData  = new byte[12];
    int readLen = socket.getInputStream().read(headData);

    if(readLen == -1){
      throw new SocketException("Connection reset");
    }if(readLen == 12){
      byte[] flagArray = new byte[8];
      byte[] dataLenArray = new byte[4];
      System.arraycopy(headData,0,flagArray,0,8);
      System.arraycopy(headData,8,dataLenArray,0,4);
      long flag = DigitUtil.getLong(flagArray);
      int targetLen = DigitUtil.getInt(dataLenArray);


      if(flag == ConnectionConstants.FLAG && targetLen > 0){//实际需要读取的数据长度不能太大，不能小于0
        byte[] targetData = new byte[targetLen];
        int buffsize = socket.getReceiveBufferSize();
        byte[] receiveBuff = new byte[buffsize];
        int remainLen = targetLen;
        while (remainLen > 0) {
          readLen = socket.getInputStream().read(receiveBuff);
          if(readLen > 0){
            if(remainLen >= readLen){
              System.arraycopy(receiveBuff,0,targetData,targetLen - remainLen,readLen);
              remainLen = remainLen - readLen;
            }else{
              System.arraycopy(receiveBuff,0,targetData,targetLen - remainLen,remainLen);
              remainLen = 0;
            }
          }
        }
        return targetData;
      }else {
        throw new IOException("Data head Error");
      }
    }else {
      throw new IOException("Data head read error");
    }
  }
}