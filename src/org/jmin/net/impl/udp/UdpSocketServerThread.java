/*
*Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.net.impl.udp;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.jmin.net.ConnectionAddress;
import org.jmin.net.event.ConnectEvent;
import org.jmin.net.event.ConnectedEvent;
import org.jmin.net.event.DisconnectEvent;
import org.jmin.net.event.ErrorEvent;
import org.jmin.net.impl.ProtocolServerThread;
import org.jmin.net.impl.udp.packet.PacketInputStream;
import org.jmin.net.impl.udp.packet.PacketOutputStream;
import org.jmin.net.impl.udp.packet.UdpCommands;
import org.jmin.net.impl.udp.packet.UdpPulsePacketUtil;
import org.jmin.net.impl.util.CloseUtil;

/**
 * UDP Server线程
 *
 * @author Chris
 */
public class UdpSocketServerThread extends ProtocolServerThread{
	
  /**
   *定时器
   */
  private Timer timer;
  
  /**
   * timer task
   */
  private TimerTask task;
  
	
  /**
   * 存放当前处于与Server保持连接的socket
   */
  private Map connectionMap;
 
  /**
   * server thread
   */
  public UdpSocketServerThread(UdpSocketServer udpServer){
  	super(udpServer);
    this.connectionMap = new HashMap();
    this.timer = new Timer(true);
    this.task = new UdpConnectionPulseTask(this);
  }

  /**
   * 检查连接的的脉搏是否跳动
   */
  synchronized void checkRemotePlus(){
  	Iterator itor = this.connectionMap.values().iterator();
  	int i = 0;
  	IOException tempException  = null;
  	while(itor.hasNext()){
  		UdpConnection udpConnection = (UdpConnection)itor.next();
  		DatagramSocket socket = udpConnection.getLocalPulseWriteSocket();
  		InetAddress inetAddress = udpConnection.getRemoteHost().getRemoteAddress();
  	  int remotePlusPort = udpConnection.getRemotePlusReadPort();
  	  for(i=0;i<3;i++){
	  	  try{
	  	  	UdpPulsePacketUtil.sendPulseQuery(socket, inetAddress, remotePlusPort);
	  	  	break;
	  	  }catch(IOException e){
	  	  	tempException = e;
	  	  }
  	  }
  	  
  	  if(i==3 && tempException!=null){
  	  	try{
	  	  	udpConnection.close();
  	  	}catch(Throwable e){
  	  	}
  	  	itor.remove();
  	  }
  	  i=0;
  	  tempException = null;
  	}
  }
  
	/**
	 * 增加一个新的连接
	 */
  synchronized void addConnection(ConnectionAddress address,UdpConnection udpConnection){
		this.connectionMap.put(address,udpConnection);
	}
	
	/**
	 * 是否存在某个连接
	 */
  synchronized boolean containsConnection(ConnectionAddress address){
		return this.connectionMap.containsKey(address);
	}
 
  /**
   * Thread method
   */
  public void run(){
  	UdpConnection requestConnection = null;
  	DatagramSocket dataReadSocket = null;
  	DatagramSocket dataWriteSocket = null;
  	DatagramSocket plusReadSocket = null;
  	DatagramSocket plusWriteSocket = null;
  	this.timer.schedule(this.task,1000,1000);
  	
  	UdpSocketServer udpServer = (UdpSocketServer)this.getProtocolSocketServer();
    while(this.startThreadActive()&& !udpServer.isClosed()){
      try{
      	UdpConnectionRequest request = this.readConnectionRequest(udpServer.getReadSocket()); 
      	if(request != null&& !this.containsConnection(request.getRemoteConnectionAddress())){//活动列表中不存在这个客户端
      		ConnectionAddress connectionAddress = request.getRemoteConnectionAddress();
      		int remoteDataReadPort  = request.getRemoteDataReadPort();
      		int remoteDataWritePort = request.getRemoteDataWritePort(); 
      		int remotePlusReadPort  = request.getRemotePulseReadPort();
      		int remotePlusWritePort = request.getRemotePulseWritePort(); 
      		int remoteSendQuestPort = request.getRemoteQuestSendPort();
      		
//      		logger.debug("remote data read port: " + request.getRemoteDataReadPort());
//      		logger.debug("remote data wirte port: " + request.getRemoteDataWritePort());
//      		logger.debug("remote pulse read port: " + request.getRemotePulseReadPort());
//      		logger.debug("remote pulse write port: " + request.getRemotePulseWritePort());
//      		logger.debug("remote send quest port: " + request.getRemoteQuestSendPort());
      		
      		dataReadSocket  = UdpSocketFactory.getInstance().getNextDatagramSocket();
      		dataWriteSocket = UdpSocketFactory.getInstance().getNextDatagramSocket();
      		plusReadSocket =  UdpSocketFactory.getInstance().getNextDatagramSocket();
      		plusWriteSocket = UdpSocketFactory.getInstance().getNextDatagramSocket();
      		dataWriteSocket.setSoTimeout(UdpConstants.Server_Default_SO_Timeout);
      		plusWriteSocket.setSoTimeout(UdpConstants.Server_Default_SO_Timeout);
      		
     		  int localDataReadPort   = dataReadSocket.getLocalPort();
  			  int loacalDataWritePort = dataWriteSocket.getLocalPort();
  			  int localPlusReadPort   = plusReadSocket.getLocalPort();
  			  int localPlusWritePort  = plusWriteSocket.getLocalPort();
  				requestConnection = new UdpConnection(dataReadSocket,dataWriteSocket,plusReadSocket,plusWriteSocket,connectionAddress);
      		requestConnection.setRemoteDataReadPort(remoteDataReadPort);
      		requestConnection.setRemoteDataWritePort(remoteDataWritePort);
      		requestConnection.setRemotePlusReadPort(remotePlusReadPort);
      		requestConnection.setRemotePlusWritePort(remotePlusWritePort);
      		requestConnection.setConnectionEventPublisherFromServer(udpServer.getConnectionEventPublisher());
      		requestConnection.setConnectionDataFilterListFromServer(udpServer.getConnectionDataFilterList());
      		udpServer.getServerEventPublisher().publish(new ConnectEvent(requestConnection));
  		    	
      		this.writeAcceptionReply(udpServer.getReadSocket(),connectionAddress.getRemoteAddress(),remoteSendQuestPort,localDataReadPort,loacalDataWritePort,localPlusReadPort,localPlusWritePort);
	        //通知远程客户端：Server已经使用了新的端口来与之通讯。
      		
      		udpServer.isAcceptable(requestConnection);//是合法的才可以被接受
      		
	        ConnectedEvent event = new ConnectedEvent(requestConnection);
	        udpServer.getServerEventPublisher().publish(event);
	        this.addConnection(connectionAddress,requestConnection);
	        requestConnection.runPulse();
	        requestConnection.run();
	       }
      	 
      }catch(Throwable e){
      	e.printStackTrace();
      	ErrorEvent errorEvent = new ErrorEvent(requestConnection,e);
      	udpServer.getServerEventPublisher().publish(errorEvent);
      	udpServer.getConnectionEventPublisher().publish(errorEvent);
   
        DisconnectEvent disconnectEvent = new DisconnectEvent(requestConnection);
        udpServer.getServerEventPublisher().publish(disconnectEvent);
        
        CloseUtil.close(dataReadSocket);
        CloseUtil.close(dataWriteSocket);
        CloseUtil.close(plusReadSocket);
        CloseUtil.close(plusWriteSocket);
      }
    }
  }
  
  /**
   * 读取连接请求
   */
  private UdpConnectionRequest readConnectionRequest(DatagramSocket socket)throws IOException{
  	UdpConnectionRequest request = null;
  	UdpDataPacket packet = UdpDataReader.read(socket);
	  if(packet !=null){
	  	byte[] data = packet.getByteArrayData();
	  	PacketInputStream readStream =null;
	  	if(data!=null &&data.length >0){
	  		try{
		  		readStream = new PacketInputStream(data);
		  		String connectFlag = readStream.readUTF();
		  		if(UdpCommands.Connection_Request.equals(connectFlag)){
		  			int remoteDataReadPort  = readStream.readInt();
	      		int remoteDataWritePort = readStream.readInt();
	      		int remotePulseReadPort  =  readStream.readInt();
	      		int remotePulseWritePort = readStream.readInt();
		  			request = new UdpConnectionRequest(packet.getremoteAddress(),remoteDataReadPort);
		  			request.setRemoteDataReadPort(remoteDataReadPort);
		  			request.setRemoteDataWritePort(remoteDataWritePort);
		  			request.setRemotePulseReadPort(remotePulseReadPort);
		  			request.setRemotePulseWritePort(remotePulseWritePort);
		  			request.setRemoteQuestSendPort(packet.getRemotePort());
		  		}
	  		}finally{
	  			CloseUtil.close(readStream);
	  		}
	  	}
  	}
  	return request; 
  }
  
  /**
   * 发送接受请求应答数据,告诉对方，Server安排了读写对方数据的端口是什么
   */
  private void writeAcceptionReply(DatagramSocket socket,InetAddress inetAddress, int remotePort,int localReadPort,int localWritePort,int localPulseReadPort,int localPulseWritePort)throws IOException{
  	PacketOutputStream writeStream = new PacketOutputStream();
  	try{
	  	writeStream.writeUTF(UdpCommands.Connection_Accept);
	  	writeStream.writeInt(localReadPort);
	  	writeStream.writeInt(localWritePort);
	  	writeStream.writeInt(localPulseReadPort);
	  	writeStream.writeInt(localPulseWritePort);
	  	UdpDataWriter.write(socket,inetAddress,remotePort,writeStream.toByteArray());
  	}finally{
			CloseUtil.close(writeStream);
		}
  }
  
  private class UdpConnectionRequest{
  	
  	/**
  	 * 对方发送请求的端口
  	 */
  	private int remoteQuestSendPort;
  	
	  /**
	   * 远程读数据端口
	   */
	  private int remoteDataReadPort;
	  
	  /**
	   * 远程写数据端口
	   */
	  private int remoteDataWritePort;
	  
	  /**
	   * 远程脉搏监听Port
	   */
	  private int remotePulseReadPort;
	  
	  /**
	   * 远程脉搏回复Port
	   */
	  private int remotePulseWritePort;
	  
	  /**
	   * 远程连接地址
	   */
	  private ConnectionAddress connectionAddress;
	
	  /**
	   * 构造函数
	   */
	  public UdpConnectionRequest(InetAddress remoteInetAddress,int remoteDataReadPort){
	  	this.remoteDataReadPort = remoteDataReadPort;
	  	this.connectionAddress = new ConnectionAddress(remoteInetAddress,remoteDataReadPort);
	  }
	
	  /**
  	 * 对方发送请求的端口
  	 */
		public int getRemoteQuestSendPort() {
			return remoteQuestSendPort;
		}
		
		/**
  	 * 对方发送请求的端口
  	 */
		public void setRemoteQuestSendPort(int remoteQuestSendPort) {
			this.remoteQuestSendPort = remoteQuestSendPort;
		}
		
		/**
		 * 远程读数据端口
		 */
		public int getRemoteDataReadPort() {
			return remoteDataReadPort;
		}
		
		/**
		 * 远程读数据端口
		 */
	  public void setRemoteDataReadPort(int remoteDataReadPort) {
			this.remoteDataReadPort = remoteDataReadPort;
		}
		
		/**
		 * 远程写数据端口
		 */
		public int getRemoteDataWritePort() {
			return remoteDataWritePort;
		}
		
		/**
		 * 远程写数据端口
		 */
		public void setRemoteDataWritePort(int remoteDataWritePort) {
			this.remoteDataWritePort = remoteDataWritePort;
		}
		
		/**
		 * 远程脉搏监听Port
		 */
		public int getRemotePulseReadPort() {
			return remotePulseReadPort;
		}
		
		/**
		 * 远程脉搏监听Port
		 */
		public void setRemotePulseReadPort(int remotePlusReadPort) {
			this.remotePulseReadPort = remotePlusReadPort;
		}
		
		/**
		 * 远程脉搏回复Port
		 */
		public int getRemotePulseWritePort() {
			return remotePulseWritePort;
		}
		
		/**
		 * 远程脉搏回复Port
		 */
		public void setRemotePulseWritePort(int remotePlusWritePort) {
			this.remotePulseWritePort = remotePlusWritePort;
		}
		
	  /**
	   * 连接对方的地址
	   */
		public InetAddress getRemoteInetAddress() {
		  return this.connectionAddress.getRemoteAddress();
		}

		/**
	   * 连接对方的地址
	   */
		public ConnectionAddress getRemoteConnectionAddress() {
			return this.connectionAddress;
		}
  }
}