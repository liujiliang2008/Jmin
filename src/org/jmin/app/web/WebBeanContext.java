/*
* Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.app.web;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.jmin.ioc.BeanException;
import org.jmin.ioc.impl.config.BeanContextImpl;

/**
 * web bean context
 * 
 * @author Chris Liao 
 */

public class WebBeanContext extends BeanContextImpl{
	
  /**
   * constructor
   */
  public WebBeanContext()throws BeanException{
  	super();
  }
 
  /**
	 * constructor
	 */
	public WebBeanContext(File file) throws BeanException {
		super(file);
	}

	/**
	 * constructor
	 */
	public WebBeanContext(File[] files) throws BeanException {
		super(files);
	}

	/**
	 * constructor
	 */
	public WebBeanContext(String filename) throws BeanException {
		super(filename);
	}

	/**
	 * constructor
	 */
	public WebBeanContext(String[] filenames) throws BeanException {
		super(filenames);
	}

	/**
	 * constructor
	 */
	public WebBeanContext(URL fileURL) throws BeanException {
		super(fileURL);
	}

	/**
	 * constructor
	 */
	public WebBeanContext(URL[] fileURLs) throws BeanException {
		super(fileURLs);
	}
	
	/**
	 * 全局Ioc context
	 */
	static Map globalLogicContextMap = new HashMap();
	
  /**
   * logic context name
   */
	public static String DEFAULT_CONTEXT_NAME = WebBeanContext.class.getName();
	
  /**
   * 获取逻辑Context
   */
  public static WebBeanContext getLogicContext(String contextName){
  	WebBeanContext context = (WebBeanContext)globalLogicContextMap.get(contextName);
  	if(context != null)
  		return context;
  	else
      throw new NullPointerException("Not found logic context with name: " + contextName);
  }
  
  /**
   * 放置默认逻辑Context
   */
  static void putDefaultLogicContext(WebBeanContext context){
   	WebGlobalHolder.defaultWebBeanContext=context;
  	putLogicContext(DEFAULT_CONTEXT_NAME,context);
  }
  
  /**
   * 获取默认逻辑Context
   */
  public static WebBeanContext getDefaultWebBeanContext(){
  	return WebGlobalHolder.defaultWebBeanContext;
  }
 
  /**
   * 删除逻辑Context
   */
  static void removeLogicContext(String contextName){
  	globalLogicContextMap.remove(contextName);
  }
  
  /**
   * 放置默认逻辑Context
   */
  static void putLogicContext(String contextName,WebBeanContext context){
  	globalLogicContextMap.put(contextName,context);
  }
}
