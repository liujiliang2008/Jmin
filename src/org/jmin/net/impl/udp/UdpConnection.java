/*
*Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.net.impl.udp;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.List;

import org.jmin.net.ConnectionAddress;
import org.jmin.net.event.DataReadEvent;
import org.jmin.net.event.DataReadOutEvent;
import org.jmin.net.event.DataWriteEvent;
import org.jmin.net.event.DataWriteInEvent;
import org.jmin.net.event.DisconnectEvent;
import org.jmin.net.event.ErrorEvent;
import org.jmin.net.impl.ConnectionEventPublisher;
import org.jmin.net.impl.ConnectionTimeoutException;
import org.jmin.net.impl.ProtocolConnection;

/**
 * Udp Connection
 *
 *@author Chris
 */

public class UdpConnection extends ProtocolConnection {

  /**
   * 远程的读端口
   */
  private int remoteDataReadPort;
  
  /**
   * 远程的写端口
   */
  private int remoteDataWritePort;
 
  /**
   * 对方脉搏监听Port
   */
  private int remotePlusReadPort;
  
  /**
   * 远程的脉搏Write端口
   */
  private int remotePlusWritePort;
  
  /**
   * read socket
   */
  private DatagramSocket localDataReadSocket;
  
  /**
   * write socket
   */
  private DatagramSocket localDataWriteSocket;
  
	/**
	 * 本地脉搏读Socket;
	 */
	private DatagramSocket localPulseReadSocket;
	
	/**
	 * 本地脉搏写Socket;
	 */
	private DatagramSocket localPulseWriteSocket;
	
  /**
   * 线程
   */
	private UdpConnectionPulseThread pulseThread;
  
  
	/**
	 * 构造函数
	 */
	public UdpConnection(DatagramSocket localDataReadSocket,DatagramSocket localDataWriteSocket,DatagramSocket localPulseReadSocket,DatagramSocket localPulseWriteSocket,ConnectionAddress remoteAddress){
		this.localDataReadSocket = localDataReadSocket;
		this.localDataWriteSocket = localDataWriteSocket;
		this.localPulseReadSocket = localPulseReadSocket;
		this.localPulseWriteSocket = localPulseWriteSocket;
		
		this.remoteHost = remoteAddress;
	  this.localHost = new ConnectionAddress(localDataReadSocket.getInetAddress(),localDataReadSocket.getLocalPort());
	}
	
	/**
	 * 本地数据读Socket
	 */
	public DatagramSocket getLocalDataReadSocket(){
		return this.localDataReadSocket;
	}
	
	/**
	 * 本地数据写Socket
	 */
	public DatagramSocket getLocalDataWriteSocket(){
		return this.localDataWriteSocket;
	}
	
	/**
	 * 脉搏读Socket 
	 */
	public DatagramSocket getLocalPulseReadSocket(){
		return this.localPulseReadSocket;
	}
	
	/**
	 * 脉搏写Socket 
	 */
	public DatagramSocket getLocalPulseWriteSocket(){
		return this.localPulseWriteSocket;
	}
	
  
	/**
	 * 获取连接对方的读端口
	 */
  public int getRemoteDataReadPort(){
  	return this.remoteDataReadPort;
  }
 
	/**
	 * 设置连接对方的读端口
	 */
  public void setRemoteDataReadPort(int remoteDataReadPort){
  	this.remoteDataReadPort = remoteDataReadPort;
  }
 
	/**
	 * 设置连接对方的写端口
	 */
  public int getRemoteDataWritePort(){
  	return this.remoteDataWritePort;
  }
  
	/**
	 * 设置连接对方的写端口
	 */
  public void setRemoteDataWritePort(int remoteDataWritePort){
  	this.remoteDataWritePort = remoteDataWritePort;
  }
  
	/**
	 *  对方脉搏监听Port
	 */
  public int getRemotePlusReadPort(){
  	return this.remotePlusReadPort;
  }
  
	/**
	 *  对方脉搏监听Port
	 */
  public void setRemotePlusReadPort(int remotePlusReadPort){
  	this.remotePlusReadPort = remotePlusReadPort;
  }
  
	/**
	 * 对方写脉搏Port
	 */
  public int getRemotePlusWritePort(){
  	return this.remotePlusWritePort;
  }
  
	/**
	 *  对方写脉搏Port
	 */
  public void setRemotePlusWritePort(int remotePlusWritePort){
  	this.remotePlusWritePort = remotePlusWritePort;
  }
  
	/**
	 * 让连接运行起来
	 */
	public void run()throws IOException{
		if(this.isRunning()){
			 throw new SocketException("Connection is running");
		}else{
	    this.connectionThread = new UdpConnectionThread(this);
		  this.connectionThread.setDaemon(true);
	  	this.connectionThread.start();
  	}
	}
	
	/**
	 * 关闭连接
	 */
	public void close()throws IOException{
	 if(this.isClosed()){
		 throw new SocketException("Connection has been closed");
	 }else{
	  this.isClosed = true;
		this.localDataReadSocket.close();
		this.localDataWriteSocket.close();
		this.localPulseReadSocket.close();
		this.localPulseWriteSocket.close();
		 if(this.connectionThread!=null)
			 connectionThread.interrupt();
	 }
	}
  
  /**
   * 运行脉搏
   */
  public void runPulse(){
  	if(this.pulseThread == null){
  		this.pulseThread = new UdpConnectionPulseThread(this.getLocalPulseReadSocket());
  		this.pulseThread.setDaemon(true);
  		this.pulseThread.start();
  	}
  }
 
	/**
	 * 从外部设定一个事件发布者,当Connection位于Server端的时候，那么Server会赋予一个ConnectionEventPublisher
	 */
  void setConnectionEventPublisherFromServer(ConnectionEventPublisher connectionEventPublisher){
		super.setConnectionEventPublisher(connectionEventPublisher);
	}
	
	/**
	 * 从外部设定一个事件发布者,当Connection位于Server端的时候，那么Server会赋予一个ConnectionEventPublisher
	 */
  void setConnectionDataFilterListFromServer(List connectionDataFilterList){
  	super.setConnectionDataFilterList(connectionDataFilterList);
	}

	/**
	* 发送数据到对方
	*/
  public void write(byte[] buff)throws IOException {
	 try{
		 this.validateBeforeWriteData(this.getRemoteHost(),buff);
		 if(Thread.currentThread() == connectionThread){
			 DataWriteEvent event = new DataWriteEvent(this,buff);
		   this.getSocketEventPublisher().publish(event);
		 }
		 
		 UdpDataWriter.write(this.localDataWriteSocket,this.remoteHost.getRemoteAddress(),this.remoteDataReadPort,buff);
		 
		 this.validateAfterWriteData(this.getRemoteHost(),buff);
		 if(Thread.currentThread() == connectionThread){
			 DataWriteInEvent event = new DataWriteInEvent(this,buff);
		   this.getSocketEventPublisher().publish(event);
		 }
	 }catch(SecurityException e){
		 if(Thread.currentThread() == connectionThread){
			 this.getSocketEventPublisher().publish(new ErrorEvent(this,e));
		 }
	 }catch(IOException e){
		 if(Thread.currentThread() == connectionThread){
		   this.getSocketEventPublisher().publish(new ErrorEvent(this,e));
		   this.getSocketEventPublisher().publish(new DisconnectEvent(this));
		 }
	 }catch(Throwable e){
		 if(Thread.currentThread() == connectionThread){
			 this.getSocketEventPublisher().publish(new ErrorEvent(this,e));
		 }
	 }
 }
  
	/**
	 * 从socket读取数
	 */
	public synchronized byte[]read()throws IOException{
		try {
			this.validateBeforeReadData(this.getRemoteHost());
			 if(Thread.currentThread() == connectionThread){
				 DataReadEvent event = new DataReadEvent(this);
	       this.getSocketEventPublisher().publish(event);
			 }
			 
		  UdpDataPacket udpData = UdpDataReader.read(this.localDataReadSocket,this.remoteHost.getRemoteAddress(),this.remoteDataWritePort);
      
		  this.validateAfterReadData(this.getRemoteHost(),udpData.getByteArrayData());
	      if(Thread.currentThread() == connectionThread){
	      	DataReadOutEvent event = new DataReadOutEvent(this,udpData.getByteArrayData());
	      	this.getSocketEventPublisher().publish(event);
	      	byte[] replyData = event.getReplyByteArrayData();
	        if(replyData!=null && replyData.length >0){
	         this.write(replyData);
	      }
			}
	    return udpData.getByteArrayData();
		}catch(SecurityException e){
		  if(Thread.currentThread() == connectionThread){
		  	this.getSocketEventPublisher().publish(new ErrorEvent(this,e));
		  }
    	throw e;
    }catch(SocketException e){
    	if(Thread.currentThread() == connectionThread){
	    	this.getSocketEventPublisher().publish(new ErrorEvent(this,e));
	    	this.getSocketEventPublisher().publish(new DisconnectEvent(this));
    	 }
    	throw e;
    }catch(IOException e){
    	if(Thread.currentThread() == connectionThread){
    		this.getSocketEventPublisher().publish(new ErrorEvent(this,e));
    	}
    	throw e;
    } catch (Exception e) {
    	if(Thread.currentThread() == connectionThread){
	    	this.getSocketEventPublisher().publish(new ErrorEvent(this, e));
	    	this.getSocketEventPublisher().publish(new DisconnectEvent(this));
    	}
    	throw new IOException(e.getMessage());
    }
	}
	
	/**
	 * 在规定的时间范围内从连接上读出远程发送过来的数据,否则算超过时间
	 */
	public synchronized byte[] read(int synWaitTime) throws IOException {
		int timeout = this.localDataReadSocket.getSoTimeout();
		try{
			 this.localDataReadSocket.setSoTimeout(synWaitTime);
			 return this.read();
		}catch(InterruptedIOException e){
			throw new ConnectionTimeoutException();
		}catch(IOException e){
			throw e;
		}finally{
			this.localDataReadSocket.setSoTimeout(timeout);
		}
	}
}