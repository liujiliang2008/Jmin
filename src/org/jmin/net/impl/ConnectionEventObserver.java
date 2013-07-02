/*
 *Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.net.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import org.jmin.net.ConnectionListener;
import org.jmin.net.event.DataReadEvent;
import org.jmin.net.event.DataReadOutEvent;
import org.jmin.net.event.DataWriteEvent;
import org.jmin.net.event.DataWriteInEvent;
import org.jmin.net.event.ErrorEvent;

/**
 * Sub implementation of observer interface.
 *
 * @author Chris Liao
 */

public class ConnectionEventObserver implements Observer {

  /**
   * 监听者列表
   */
  private List listenerList = new ArrayList();

  /**
   * 增加一个连接事件监听者
   */
  public synchronized void addConnectionEventListener(ConnectionListener listener) {
    if (listener!=null && !listenerList.contains(listener)) {
      listenerList.add(listener);
    }
  }

  /**
   * 删除一个连接事件监听者
   */
  public synchronized void removeConnectionEventListener(ConnectionListener listener) {
    if (listenerList.contains(listener)) {
      listenerList.remove(listener);
    }
  }
  /**
   * Implementation method from observer interface.
   */
  public void update(Observable o, Object arg) {
    Iterator itor = listenerList.iterator();
    while(itor.hasNext()){
      ConnectionListener listener = (ConnectionListener)itor.next();
      try {
        if (arg instanceof DataReadEvent) {
          listener.onRead((DataReadEvent) arg);
        }else if (arg instanceof DataReadOutEvent) {
          listener.onReadOut((DataReadOutEvent) arg);
        } else if (arg instanceof DataWriteEvent) {
          listener.onWrite((DataWriteEvent) arg);
        } else if (arg instanceof DataWriteInEvent) {
          listener.onWriteIn((DataWriteInEvent) arg);
        } else if (arg instanceof ErrorEvent) {
          listener.onError((ErrorEvent) arg);
        }
      } catch (Throwable e) {
              e.printStackTrace();
      }
    }
  }
}