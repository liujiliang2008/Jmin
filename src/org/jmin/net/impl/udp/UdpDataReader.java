/*
*Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.net.impl.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import org.jmin.net.impl.ConnectionConstants;
import org.jmin.net.impl.udp.packet.UdpDatagramPacketReader;
import org.jmin.net.impl.util.DigitUtil;

/**
 * UDP数据Reader
 *
 * @author Chris Liao 
 */

public class UdpDataReader {

  /**
   * read data from DatagramSocket
   */
  public static UdpDataPacket read(DatagramSocket socket) throws IOException {
  	UdpDataPacket udpData = null;
  	DatagramPacket packet = UdpDatagramPacketReader.read(socket);
  	int readLen = packet.getLength();//刚读出的数据实际长度
		int port = packet.getPort();//远程端口
		InetAddress addr = packet.getAddress();//远程的地址
  	
		if(readLen == 12){
	  	byte[] flagArray = new byte[8];
	  	byte[] dataLenArray = new byte[4];
	  	byte[] packetData = packet.getData();
	  	System.arraycopy(packetData,0,flagArray,0,8);
			System.arraycopy(packetData,8,dataLenArray,0,4);
			long flag = DigitUtil.getLong(flagArray);
			int targetLen = DigitUtil.getInt(dataLenArray);
		
			if(flag == ConnectionConstants.FLAG && targetLen > 0){//实际需要读取的数据长度不能太大，不能小于0
				int remainLen = targetLen; 
				byte[] targetData = new byte[targetLen];
				int receiveSize =	socket.getReceiveBufferSize();
			
				
				//**循环多次读取完毕随后所有数据			
				while (remainLen > 0) {
					packet = UdpDatagramPacketReader.read(socket,addr,port);
				  packetData = packet.getData();
				  readLen = packet.getLength();
		
					if (remainLen >= receiveSize) {
						System.arraycopy(packetData, 0, targetData, targetLen - remainLen,receiveSize);
						remainLen = remainLen - receiveSize;
					} else {
						System.arraycopy(packetData, 0, targetData, targetLen - remainLen, remainLen);
						remainLen = 0;
					}
				}
				udpData = new UdpDataPacket(addr, port);
				udpData.setData(targetData);
				return udpData;
			}else {
			 throw new IOException("Data head Error");
			}
		}else {
			 throw new IOException("Data head read error");
		}
	}
	
  /**
   * read data from DatagramSocket
   */
  public static UdpDataPacket read(DatagramSocket socket,InetAddress remoteAddress,int remoteWritePort) throws IOException {
  	UdpDataPacket udpData = null;
  	DatagramPacket packet = UdpDatagramPacketReader.read(socket,remoteAddress,remoteWritePort);
  	int readLen = packet.getLength();//刚读出的数据实际长度
  	
		if(readLen == 12){
	  	byte[] flagArray = new byte[8];
	  	byte[] dataLenArray = new byte[4];
	  	byte[] packetData = packet.getData();
	  	System.arraycopy(packetData,0,flagArray,0,8);
			System.arraycopy(packetData,8,dataLenArray,0,4);
			long flag = DigitUtil.getLong(flagArray);
			int targetLen = DigitUtil.getInt(dataLenArray);
			
			if(flag == ConnectionConstants.FLAG && targetLen > 0){//实际需要读取的数据长度不能太大，不能小于0
				int remainLen = targetLen;  
				byte[] targetData = new byte[targetLen];
				int receiveSize =	socket.getReceiveBufferSize();
				while (remainLen > 0) {
					packet = UdpDatagramPacketReader.read(socket,remoteAddress,remoteWritePort);
				  packetData = packet.getData();
				  readLen = packet.getLength();
				 
					if (remainLen >= receiveSize) {
						System.arraycopy(packetData, 0, targetData, targetLen - remainLen,receiveSize);
						remainLen = remainLen - receiveSize;
					} else {
						System.arraycopy(packetData, 0, targetData, targetLen - remainLen, remainLen);
						remainLen = 0;
					}
				}
				
				udpData = new UdpDataPacket(remoteAddress,remoteWritePort);
				udpData.setData(targetData);
				return udpData;
			}else {
			 throw new IOException("Data head Error");
			}
		}else {
			 throw new IOException("Data head read error");
		}
  	
  	
  	
  	
//			UdpData udpData = null;
//			DatagramPacket packet = UdpPacketReader.read(socket,remoteAddress,remoteWritePort);
//			byte[] packetData = packet.getData();//获得刚读取的数据
//			int packetDataLen = packet.getLength();//读取包中数据实际长度，该长度应该是小于或等于数据长度
//			
//			
//			if(packetDataLen >= 12){//一定要大于4才有意义，数据的前四位为随后数据的总长度
//			 	byte[] flagArray = new byte[8];
//			 	byte[] dataLenArray = new byte[4];
//				System.arraycopy(packetData,0,flagArray,0,8);
//				System.arraycopy(packetData,8,dataLenArray,0,4);
//				long flag =DigitUtil.getLong(flagArray);
//				int needReadLen = DigitUtil.getInt(dataLenArray);
//				
//				if(flag == ConnectionConstants.FLAG && needReadLen > 0 && needReadLen <= Integer.MAX_VALUE){//实际需要读取的数据长度不能太大，不能小于0
//					byte[] needReadData = new byte[needReadLen];//需要读出的数据
//					int remainLen = needReadLen;//还差多少长度没有被读取
//					if (remainLen >= packetData.length) {//数据比较长，需要多次读取
//						System.arraycopy(packetData, 12, needReadData, 0, packetDataLen-12);
//						remainLen = remainLen - (packetDataLen-12);
//					} else {//数据比较短，一次已经读取完毕
//						System.arraycopy(packetData, 12, needReadData, 0, packetDataLen-12);
//						remainLen = 0;
//					}
//					
//					//**循环多次读取完毕随后所有数据
//					while (remainLen > 0) {
//						packet = UdpPacketReader.read(socket,remoteAddress,remoteWritePort);
//						  packetData = packet.getData();
//						  packetDataLen = packet.getLength();
//						if (remainLen >= packetDataLen) {
//							System.arraycopy(packetData, 0, needReadData, needReadLen - remainLen,packetDataLen);
//							remainLen = remainLen - packetDataLen;
//						} else {
//							System.arraycopy(packetData, 0, needReadData, needReadLen - remainLen, packetDataLen);
//							remainLen = 0;
//						}
//				 }
//					
//				udpData = new UdpData(remoteAddress, remoteWritePort);
//				udpData.setData(needReadData);
//				return udpData;
//				}
//			}
//		throw new IOException("read exception");
	}
}
