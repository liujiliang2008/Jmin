/*
 *Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.net.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jmin.net.ConnectionDataFilter;
import org.jmin.net.ConnectionListener;
import org.jmin.net.ConnectionFilter;
import org.jmin.net.NetworkServer;
import org.jmin.net.NetworkServerListener;

/**
 * server定义
 * 
 * @author Chris Liao 
 */
public abstract class ProtocolServer implements NetworkServer {
	
	/**
	 * 是否处于关闭状态
	 */
	protected boolean isClosed;
	
	/**
	 * 服务器端口
	 */
	private int serverPort;
	
  /**
   * 网络connection拦截，该拦截器可以决定是否被接受
   */
	private List connectionFilterList;
	
  /**
   * connection数据拦截器
   */
	private List connectionDataFilterList;

	/**
	 * Server事件发布者
	 */
	private ProtocolServerEventPublisher serverEventPublisher;
	
  /**
   * Server事件观察者
   */
	private ProtocolServerEventObserver serverEventObserver;

	/**
	 * Socket事件发布者
	 */
	private ConnectionEventPublisher connectionEventPublisher;
	
	/**
	 * Socket事件观察者
	 */
	private ConnectionEventObserver connectionEventObserver;
	
	/**
	 * 服务器等待接受线程
	 */
	protected ProtocolServerThread socketServerThread;
  
	/**
	 * 构造函数
	 */
	public ProtocolServer(int serverPort){
		this.isClosed = false;
		this.serverPort  = serverPort;
		this.connectionFilterList = new ArrayList();
		this.connectionDataFilterList = new ArrayList();

		this.serverEventPublisher = new ProtocolServerEventPublisher();
		this.serverEventObserver  = new ProtocolServerEventObserver();
		this.serverEventPublisher.addObserver(this.serverEventObserver);
		
		this.connectionEventPublisher = new ConnectionEventPublisher();
		this.connectionEventObserver = new ConnectionEventObserver();
		this.connectionEventPublisher.addObserver(this.connectionEventObserver);
		this.connectionEventPublisher.addObserver(this.serverEventObserver);
	}
	
	/**
	 * 获得服务器端口
	 */
	public int getServerPort(){
		return this.serverPort;
	}
	
	/**
	 * Server事件发布者
	 */
	public ProtocolServerEventPublisher getServerEventPublisher(){
		return this.serverEventPublisher;
	}

	/**
	 * Socket事件发布者
	 */
	public ConnectionEventPublisher getConnectionEventPublisher() {
		return this.connectionEventPublisher;
	}

	/**
	 * 添加一个过滤器，可以决定来自哪些机器的请求是可以接受的,担当Firewall
	 */
	public void addConnectionFilter(ConnectionFilter filter){
	 if(!this.connectionFilterList.contains(filter))
		 this.connectionFilterList.add(filter);
	}
	
	/**
	 * 添加一个过滤器，可以决定来自哪些机器的请求是可以接受的,担当Firewall
	 */
	public void removeConnectionFilter(ConnectionFilter filter){
 	 if(this.connectionFilterList.contains(filter))
 		this.connectionFilterList.remove(filter);
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
	 * 增加一个服务器事件监听者
	 */
	public void addServerEventListener(NetworkServerListener listener){
		this.serverEventObserver.addSocketServerEventListener(listener);
	}
	
	/**
	 * 删除一个服务器事件监听者
	 */
	public void removeServerEventListener(NetworkServerListener listener){
		this.serverEventObserver.removeSocketServerEventListener(listener);
	}

	/**
   * Socket数据拦截器
	 */
	public synchronized List getConnectionDataFilterList() {
		return this.connectionDataFilterList;
	}
		
	/**
	 * 判断远程Server是否为合法地址
	 */
	public synchronized void isAcceptable(ProtocolConnection connection){
		if(connectionFilterList!=null){
			Iterator itor = connectionFilterList.iterator();
			while(itor.hasNext()){
				ConnectionFilter filter =(ConnectionFilter)itor.next();
			  filter.validate(connection);
	    }
		}
	}
	
	/**
	 * 是否已经关闭
	 */
	public boolean isClosed(){
		return this.isClosed;
	}
	
  /**
   * 是否处于运行状态
   */
  public boolean isRunning(){
  	return (socketServerThread!=null && socketServerThread.isAlive());
  }
}
