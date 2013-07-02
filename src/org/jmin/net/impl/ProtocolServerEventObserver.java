/*
 *Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.net.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import org.jmin.net.NetworkServerListener;
import org.jmin.net.event.ConnectEvent;
import org.jmin.net.event.ConnectedEvent;
import org.jmin.net.event.DisconnectEvent;
import org.jmin.net.event.ServerClosedEvent;
import org.jmin.net.event.ServerCreatedEvent;

/**
 * sub implementation of observer interface.
 *
 * @author chris
 */

public class ProtocolServerEventObserver implements Observer {
	
	/**
	 * 监听者列表
	 */
	private List listenerList = new ArrayList();
	
	/**
	 * 增加一个Server事件监听者
	 */
	public synchronized void addSocketServerEventListener(NetworkServerListener listener) {
	 if(listener!=null && !listenerList.contains(listener)){
		 listenerList.add(listener);
	 }
	}
	
	/**
	 * 删除一个Server事件监听者
	 */
	public synchronized void removeSocketServerEventListener(NetworkServerListener listener) {
	 if(listenerList.contains(listener)){
		 listenerList.remove(listener);
	 }
	}

	/**
	 * Implementation method from observer interface.
	 */
	public void update(Observable o, Object arg) {
		Iterator itor = listenerList.iterator();
		while(itor.hasNext()){
			NetworkServerListener listener =(NetworkServerListener)itor.next();
			try{
				if (arg instanceof ServerCreatedEvent) {
					listener.onCreated((ServerCreatedEvent) arg);
				} else if (arg instanceof ServerClosedEvent) {
					listener.onClosed((ServerClosedEvent) arg);
				} else if (arg instanceof ConnectEvent) {
					listener.onConnect((ConnectEvent) arg);
				} else if (arg instanceof ConnectedEvent) {
					listener.onConnected((ConnectedEvent) arg);
				} else if (arg instanceof DisconnectEvent) {
					listener.onDisconnected((DisconnectEvent) arg);
				}
			}catch(Throwable e){
				e.printStackTrace();
			}
		}
	}
}