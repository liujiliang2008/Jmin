/*
 *Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.log;

import org.jmin.log.printer.LogCenter;

/**
 * 日记输出工具
 * 
 * author Chris Liao 
 */

public abstract class Logger {

	/**
	 * proxy
	 */
	private static LogCenter printCenter = null;

  /**
   * static method
   */
  public synchronized static Logger getLogger(Class cls) {
    return getLogger(cls.getName());
  }

	/**
	 * 创建日记打印类实例
	 */
	public synchronized static Logger getLogger(String clasname) {
		if(printCenter == null)
			printCenter = new LogCenter();
		return new LoggerImpl(clasname,printCenter);
	}

	/**
	 *打印debug级别消息
	 */
	public abstract void debug(Object obj);
	
	/**
	 *打印debug级别消息
	 */
	public abstract void debug(Object oid,Object obj);
	
	/**
	 *打印debug级别消息
	 */
	public abstract void debug(Object obj,Throwable e);
	
	/**
	 *打印debug级别消息
	 */
	public abstract void debug(Object oid,Object obj,Throwable e);
	
	
	/**
	 * 打印info级别消息
	 */
	public abstract void info(Object obj);
	
	/**
	 * 打印info级别消息
	 */
	public abstract void info(Object oid,Object obj);
	
	/**
	 * 打印info级别消息
	 */
	public abstract void info(Object obj,Throwable e);
	
	/**
	 * 打印info级别消息
	 */
	public abstract void info(Object oid,Object obj,Throwable e);
	
	

	/**
	 * 打印warn级别消息
	 */
	public abstract void warn(Object obj);
	
	/**
	 * 打印warn级别消息
	 */
	public abstract void warn(Object oid,Object obj);
	
	/**
	 * 打印warn级别消息
	 */
	public abstract void warn(Object obj,Throwable e);
	
	/**
	 * 打印warn级别消息
	 */
	public abstract void warn(Object oid,Object obj,Throwable e);
	
	
	

	/**
	 * 打印error级别消息
	 */
	public abstract void error(Object obj);
	
	/**
	 * 打印error级别消息
	 */
	public abstract void error(Object oid,Object obj);
	
	/**
	 * 打印error级别消息
	 */
	public abstract void error(Object obj,Throwable e);
	
	/**
	 * 打印error级别消息
	 */
	public abstract void error(Object oid,Object obj,Throwable e);
	
	
	
	/**
	 * 打印fatal级别消息
	 */
	public abstract void fatal(Object obj);
	
	/**
	 * 打印fatal级别消息
	 */
	public abstract void fatal(Object oid,Object obj);
	
	/**
	 * 打印fatal级别消息
	 */
	public abstract void fatal(Object obj,Throwable e);
	
	/**
	 * 打印fatal级别消息
	 */
	public abstract void fatal(Object oid,Object obj,Throwable e);
}
