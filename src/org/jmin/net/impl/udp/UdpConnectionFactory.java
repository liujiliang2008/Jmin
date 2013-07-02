/*
 *Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.net.impl.udp;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import org.jmin.net.Connection;
import org.jmin.net.ConnectionAddress;
import org.jmin.net.ConnectionListener;
import org.jmin.net.impl.ProtocolConnectionFactory;
import org.jmin.net.impl.udp.packet.PacketInputStream;
import org.jmin.net.impl.udp.packet.PacketOutputStream;
import org.jmin.net.impl.udp.packet.UdpCommands;
import org.jmin.net.impl.util.CloseUtil;

/**
 * UDP网络连接工厂
 *
 * @author Chris Liao
 */

public class UdpConnectionFactory extends  ProtocolConnectionFactory{
	
  /**
   * 创建连接到Server的连接
   */
  public Connection openConnection(String host,int port) throws IOException{
  	return this.openConnection(host,port,null);
  }
	
	/**
	 * 连接到UDP Server
	 */
	public Connection openConnection(String serverHost,int serverPort,ConnectionListener listener)throws IOException{
		 return this.connect(serverHost, serverPort,listener);
	}
	
	/**
	 * 连接到远程Server
	 */
	private Connection connect(String serverHost,int serverPort,ConnectionListener listener)throws IOException{
		DatagramSocket localDataReadSocket = null;
		DatagramSocket localDataWriteSocket= null;
		DatagramSocket localPlusReadSocket = null;
		DatagramSocket localPlusWriteSocket = null;
		PacketOutputStream writeStream = null;
		PacketInputStream readStream = null;
		try{
				localDataReadSocket  = UdpSocketFactory.getInstance().getNextDatagramSocket();
				localDataWriteSocket = UdpSocketFactory.getInstance().getNextDatagramSocket();
				localPlusReadSocket  = UdpSocketFactory.getInstance().getNextDatagramSocket();
				localPlusWriteSocket = UdpSocketFactory.getInstance().getNextDatagramSocket();
				localDataWriteSocket.setSoTimeout(UdpConstants.Client_Default_SO_Timeout);
				localPlusWriteSocket.setSoTimeout(UdpConstants.Client_Default_SO_Timeout);
				
				int localDataReadPort =   localDataReadSocket.getLocalPort();
				int loacalDataWritePort = localDataWriteSocket.getLocalPort();
				int localPlusReadPort = localPlusReadSocket.getLocalPort();
				int localPlusWritePort = localPlusWriteSocket.getLocalPort();
//			  log.debug("crate local read port: " + localDataReadPort);
//     	  log.debug("ceate local wirte port: " + loacalDataWritePort);
//    	  log.debug("ceate local plus read port: " + localPlusReadPort);
//    	  log.debug("ceate local plus write port: " + localPlusWritePort);
				
				InetAddress serverInetAddress = InetAddress.getByName(serverHost); 
				writeStream = new PacketOutputStream();
				writeStream.writeUTF(UdpCommands.Connection_Request);
				writeStream.writeInt(localDataReadPort);
				writeStream.writeInt(loacalDataWritePort);
				writeStream.writeInt(localPlusReadPort);
				writeStream.writeInt(localPlusWritePort);
				byte[] data = writeStream.toByteArray();
				UdpDataWriter.write(localDataWriteSocket,serverInetAddress,serverPort,data);
			 
				UdpDataPacket packet = UdpDataReader.read(localDataWriteSocket,serverInetAddress,serverPort);
				readStream = new PacketInputStream(packet.getByteArrayData());
				String flag = readStream.readUTF();
				if(UdpCommands.Connection_Accept.equals(flag)){
					int serverDataReadPort =  readStream.readInt();
					int serverDataWritePort = readStream.readInt();
					int serverPlusReadPort = readStream.readInt();
					int serverPlusWritePort = readStream.readInt();
					 
//					log.debug("get server reply: server read port " + serverDataWritePort);
//					log.debug("get server reply: server write port " + serverDataWritePort);
					 
					ConnectionAddress connectionAddress = new ConnectionAddress(serverInetAddress,serverPort);
					UdpConnection connection = new UdpConnection(localDataReadSocket,localDataWriteSocket,localPlusReadSocket,localPlusWriteSocket,connectionAddress);
					connection.setRemoteDataReadPort(serverDataReadPort);
					connection.setRemoteDataWritePort(serverDataWritePort);
					connection.setRemotePlusWritePort(serverPlusReadPort);
					connection.setRemotePlusWritePort(serverPlusWritePort);
					if(listener!=null)
						connection.addConnectionEventListener(listener);
					connection.runPulse();
					return connection;
			 }else {
				 	throw new SocketException("Connection refused");
			}
		}catch(IOException e){
			CloseUtil.close(localDataReadSocket);
			CloseUtil.close(localDataWriteSocket);
			CloseUtil.close(localPlusReadSocket);
			CloseUtil.close(localPlusWriteSocket);
			throw new SocketException("Connection refused");
		}finally{
			CloseUtil.close(readStream);
			CloseUtil.close(writeStream);
		}
	}
}