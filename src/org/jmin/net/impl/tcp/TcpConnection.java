/*
*Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.net.impl.tcp;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.Socket;
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
import org.jmin.net.impl.util.CloseUtil;

/**
 * TCP Connection 
 *
 *@author Chris
 */

public class TcpConnection extends ProtocolConnection {

  /**
   * associated socket
   */
  private Socket socket;
 
	/**
	 * 构造函数
	 */
	public TcpConnection(Socket socket){
		this.socket = socket;
		this.remoteHost = new ConnectionAddress(socket.getInetAddress(),socket.getPort());
	  this.localHost = new ConnectionAddress(socket.getLocalAddress(),socket.getLocalPort());
	}

  /**
   * Return the associated socket with the connector
   */
	public Socket getSocket(){
    return this.socket;
  }

	/**
	 * 让连接运行起来
	 */
	public void run()throws IOException{
		if(this.isRunning()){
			 throw new SocketException("Connection is running");
		}else{
	    this.connectionThread = new TcpConnectionThread(this);
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
	  CloseUtil.close(this.socket);
	  if(this.connectionThread!=null)
	  	this.connectionThread.interrupt();
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
		 
		 TcpDataWriter.write(socket,buff);
		 
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
			 
      byte[] data = TcpDataReader.read(this.socket);
      
      this.validateAfterReadData(this.getRemoteHost(),data);
      if(Thread.currentThread() == connectionThread){
      	DataReadOutEvent event = new DataReadOutEvent(this,data);
      	this.getSocketEventPublisher().publish(event);
      	byte[] replyData = event.getReplyByteArrayData();
        if(replyData!=null && replyData.length >0){
         this.write(replyData);
        }
		  }
      return data;
		}catch(SecurityException e){
		  if(Thread.currentThread() == connectionThread){
		  	this.getSocketEventPublisher().publish(new ErrorEvent(this, e));
		  }
    	throw e;
    }catch(SocketException e){
    	CloseUtil.close(this);
    	if(Thread.currentThread() == connectionThread){
	    	this.getSocketEventPublisher().publish(new ErrorEvent(this,e));
	    	this.getSocketEventPublisher().publish(new DisconnectEvent(this));
    	}
    	throw e;
    }catch(java.io.EOFException e){
    	CloseUtil.close(this);
    	if(Thread.currentThread() == connectionThread){
	    	this.getSocketEventPublisher().publish(new ErrorEvent(this,e));
	    	this.getSocketEventPublisher().publish(new DisconnectEvent(this));
    	}
    	throw e;
    }catch(IOException e){
    	if(Thread.currentThread() == connectionThread){
    		this.getSocketEventPublisher().publish(new ErrorEvent(this, e));
    	}
    	throw e;
    } catch (Throwable e) {
    	CloseUtil.close(this);
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
		int timeout = this.getSocket().getSoTimeout();
		try{
			 this.getSocket().setSoTimeout(synWaitTime);
			 return this.read();
		}catch(InterruptedIOException e){
			throw new ConnectionTimeoutException();
		}catch(IOException e){
			throw e;
		}finally{
			this.getSocket().setSoTimeout(timeout);
		}
	}
}