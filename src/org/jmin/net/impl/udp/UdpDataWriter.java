/*
*Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.net.impl.udp;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;

import org.jmin.net.impl.ConnectionConstants;
import org.jmin.net.impl.udp.packet.UdpDatagramPacketWriter;
import org.jmin.net.impl.util.DigitUtil;

/**
 * UDP数据Writer
 *
 * @author Chris Liao 
 */

public class UdpDataWriter {
  /**
   * 将数据写到自对方指定端口
   */
  public static void write(DatagramSocket socket,InetAddress remoteAddress,int remoteWritePort,byte[] targetData) throws IOException {
  	try{
    	
   	 //发送数据头
   	 byte[] flagData =  DigitUtil.getBytes(ConnectionConstants.FLAG);
   	 byte[] targetLen = DigitUtil.getBytes(targetData.length);
   	 byte[] headData = new byte[12];
   	 System.arraycopy(flagData,0,headData,0,8);
 		 System.arraycopy(targetLen,0,headData,8,4);
 		 UdpDatagramPacketWriter.write(socket,remoteAddress,remoteWritePort,headData);
 
 		 int remainLen = targetData.length;
     int buffsize = socket.getSendBufferSize();
	   byte[] sendBuff = new byte[socket.getSendBufferSize()];
 	   while (remainLen > 0) {
 		 	 if(remainLen >= buffsize){		 
 		 		 System.arraycopy(targetData,targetData.length-remainLen,sendBuff,0,sendBuff.length);
 		 		 UdpDatagramPacketWriter.write(socket, remoteAddress, remoteWritePort,sendBuff);
 		 		 remainLen = remainLen - buffsize;
 		 	 } else {
 		 		 System.arraycopy(targetData,targetData.length-remainLen,sendBuff,0,remainLen);
		 		 UdpDatagramPacketWriter.write(socket,remoteAddress,remoteWritePort,sendBuff,0,remainLen);
 		 		 remainLen = 0;
 		  }
 	  }
  
 	  Thread.sleep(1);
   	}catch(Throwable e){
   		e.printStackTrace();
   	}
  } 
}
