/*
 *Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.net.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jmin.net.Connection;
import org.jmin.net.ConnectionAddress;
import org.jmin.net.ConnectionDataFilter;
import org.jmin.net.ConnectionListener;

/**
 * 网络协议连接
 * 
 * @author Chris Liao 
 */
public abstract class ProtocolConnection implements Connection {
	
	static {
		new SystemGarbageTask();
	}

	/**
	 * 默认读取消息的超时:1个小时
	 */
	private static final int DefaultTimeout = 3600000;
	
  /**
   * remote host
   */
  protected ConnectionAddress remoteHost;

  /**
   * local host
   */
  protected ConnectionAddress localHost;
	
  /**
   * connection数据拦截器
   */
	private List connectionDataFilterList;
	
	/**
	 * Socket事件发布者
	 */
	private ConnectionEventPublisher connectionEventPublisher;
	
	/**
	 * Socket事件观察者
	 */
	private ConnectionEventObserver connectionEventObserver;
	
	/**
	 * 网络连接是否关闭
	 */
	protected boolean isClosed = false;
	
	/**
	 * 连接的Thread
	 */
	protected ProtocolConnectionThread connectionThread;
	
	
	/**
	 * 构造函数
	 */
	public ProtocolConnection(){
		this.isClosed = false;
		this.connectionDataFilterList = new ArrayList();
		this.connectionEventPublisher = new ConnectionEventPublisher();
		this.connectionEventObserver = new ConnectionEventObserver();
		this.connectionEventPublisher.addObserver(this.connectionEventObserver);
	}
	
	/**
	 * 连接是否关闭
	 */
	public boolean isClosed(){
		return this.isClosed;
	}
	
	/**
	 * 是否处于
	 */
	public boolean isRunning(){
		return (connectionThread!=null && connectionThread.isAlive());
	}

	/**
   * Return local host
   */
  public ConnectionAddress getLocalHost(){
  	return this.localHost;
  }

  /**
   * Retturn remote host
   */
  public ConnectionAddress getRemoteHost(){
  	return this.remoteHost;
  }
	
	/**
	 * 事件发布者
	 */
	public ConnectionEventPublisher getSocketEventPublisher(){
		return this.connectionEventPublisher;
	}
	
	/**
	 * 从外部设定一个事件发布者,当Connection位于Server端的时候，那么Server会赋予一个ConnectionEventPublisher
	 */
  protected void setConnectionEventPublisher(ConnectionEventPublisher connectionEventPublisher){
		this.connectionEventPublisher = connectionEventPublisher;
	}
 
	/**
	 * 增加一个数据过滤器，可以拦截一些哪些信息是不可以传送或被接受
	 */
	public void addConnectionDataFilter(ConnectionDataFilter filter){
		if(!this.connectionDataFilterList.contains(filter))
			this.connectionDataFilterList.add(filter);
	}
	
	/**
	 * 增加一个数据过滤器，可以拦截一些哪些信息是不可以传送或被接受
	 */
	public void removeConnectionDataFilter(ConnectionDataFilter filter){
		if(this.connectionDataFilterList.contains(filter))
			this.connectionDataFilterList.remove(filter);
	}
	
	/**
	 * 从外部设定一个事件发布者,当Connection位于Server端的时候，那么Server会赋予一个ConnectionEventPublisher
	 */
  protected void setConnectionDataFilterList(List connectionDataFilterList){
		this.connectionDataFilterList = connectionDataFilterList;
	}

	/**
	 * 增加一个连接事件监听者,一个Server下的所有连接的事件都只对应一个监听
	 */
	public void addConnectionEventListener(ConnectionListener listener){
		this.connectionEventObserver.addConnectionEventListener(listener);
	}
	
	/**
	 * 删除一个连接事件监听者
	 */
	public void removeConnectionEventListener(ConnectionListener listener){
		this.connectionEventObserver.removeConnectionEventListener(listener);
	}
	
	
	/**
	 * 检查数据的合法性
	 */
	public void validateBeforeReadData(ConnectionAddress addr){
		if(connectionDataFilterList!=null){
			Iterator itor = this.connectionDataFilterList.iterator();
			while(itor.hasNext()){
				ConnectionDataFilter filter =(ConnectionDataFilter)itor.next();
				filter.beforeRead(addr);
			}
		}
	}
  
	/**
	 * 检查数据的合法性
	 */
	public void validateAfterReadData(ConnectionAddress addr,byte[] data){
		if(connectionDataFilterList!=null){
			Iterator itor = this.connectionDataFilterList.iterator();
			while(itor.hasNext()){
				ConnectionDataFilter filter =(ConnectionDataFilter)itor.next();
				filter.afterRead(addr,data);
			}
		}
	}

	/**
	 * 检查数据的合法性
	 */
	public void validateBeforeWriteData(ConnectionAddress addr,byte[] data){
		if(connectionDataFilterList!=null){
			Iterator itor = this.connectionDataFilterList.iterator();
			while(itor.hasNext()){
				ConnectionDataFilter filter =(ConnectionDataFilter)itor.next();
				filter.beforeWrite(addr,data);
			}
		}
	}
	
	/**
	 * 检查数据的合法性
	 */
	public void validateAfterWriteData(ConnectionAddress addr,byte[] data){
		if(connectionDataFilterList!=null){
			Iterator itor = this.connectionDataFilterList.iterator();
			while(itor.hasNext()){
				ConnectionDataFilter filter =(ConnectionDataFilter)itor.next();
				filter.afterWrite(addr,data);
			}
		}
	}

	/**
	 * 将数据通过连接输送给远程连接方
	 */
	public synchronized void write(byte[] data)throws IOException{
		throw new IOException("Not Implemented");
	}
	
	/**
	 * 将数据通过连接输送给远程连接方
	 */
	public synchronized byte[] read()throws IOException{
		throw new IOException("Not Implemented");
	}

	/**
	 * 将数据通过连接输送给远程连接方
	 */
	public synchronized byte[] read(int timeout)throws IOException{
		throw new IOException("Not Implemented");
	}
	
	/**
	 * 将数据同步写给对方，并需要对方给出回复
	 */
	public synchronized byte[] writeSyn(byte[] data)throws IOException{
		return this.writeSyn(data,DefaultTimeout);
	}
	
	/**
	 * 将数据同步写给对方，并需要对方给出回复
	 */
	public synchronized byte[] writeSyn(byte[] data,int timeout)throws IOException{
		this.write(data);
		return this.read(timeout);
	}
	
	/**
	 * Override method
	 */
	public int hasCode() {
		return this.getRemoteHost().hashCode();
	}
	
	/**
	 * Override method
	 */
	public String toString() {
		return this.getRemoteHost().toString();
	}

	/**
	 * override mthod
	 */
	public boolean equals(Object obj) {
		if (obj instanceof ProtocolConnection) {
			ProtocolConnection other = (ProtocolConnection) obj;
			return this.getRemoteHost().equals(other.getRemoteHost());
		} else {
			return false;
		}
	}
}