/*
* Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.app.web;

import org.jmin.jec.EventPublisher;

/**
 * 系统全局对象的Holder
 * 
 * @author Chris Liao
 */
public class WebGlobalHolder{
	
	/**
	 * 系统默认的web bean context
	 */
	static WebBeanContext defaultWebBeanContext;
	
	/**
	 * 事件发布者
	 */
	static EventPublisher defaultEventPublisher;

  /**
   * 获取事件发布者
   */
  public static EventPublisher getDefaultWebBeanContext(){
  	return defaultEventPublisher;
  }
	
  /**
   * 获取事件发布者
   */
  public static EventPublisher getDefaultEventPublisher(){
  	return defaultEventPublisher;
  }
}
