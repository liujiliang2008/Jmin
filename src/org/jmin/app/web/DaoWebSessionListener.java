/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.app.web;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Web Session监听,如果发现某个Web Session关闭后，需要立即关闭Session对应的Jda Session
 * 
 * @author chris
 */
public class DaoWebSessionListener implements HttpSessionListener{
	
	/**
	 * Web Session创建
	 */
  public void sessionCreated(HttpSessionEvent httpsessionevent){}

  /**
   * 需要关闭Session
   */
  public void sessionDestroyed(HttpSessionEvent httpsessionevent){
  	HttpSession session = httpsessionevent.getSession();
  	DaoTemplateUtil.removeDaoSession(session.getId());
  }
}
