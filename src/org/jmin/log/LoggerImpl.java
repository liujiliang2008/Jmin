/*
*Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.log;

import org.jmin.log.printer.LogCenter;
 
/**
 * 日记输出
 * 
 * @author Chris Liao 
 */
public class LoggerImpl extends Logger{
	
	/**
	 *打印配置信息
	 */
	private LoggerInfo sourceInfo = null;
	
	/**
	 * 日记代理
	 */
	private LogCenter logPrintCenter = null;
	
	/**
	 * 构造
	 */
  public LoggerImpl(String className,LogCenter logPrintCenter){
  	this.logPrintCenter = logPrintCenter;
  	this.sourceInfo = new LoggerInfo(className);
  	String category = logPrintCenter.getCategory(className);
		this.sourceInfo.setCategory(category);
  }
  
  /**
	 *打印debug级别消息
	 */
	public void debug(Object obj) {
		logPrintCenter.debug(obj,sourceInfo);
	}
	
  /**
	 *打印debug级别消息
	 */
	public void debug(Object OID,Object obj) {
		logPrintCenter.debug(OID,obj,sourceInfo);
	}
	
	/**
	 *打印debug级别消息
	 */
	public void debug(Object obj,Throwable e){
		logPrintCenter.debug(obj,e,sourceInfo);
	}
	
	/**
	 *打印debug级别消息
	 */
	public void debug(Object oid,Object obj,Throwable e){
		logPrintCenter.debug(oid,obj,e,sourceInfo);
	}
	
	/**
	 * 打印info级别消息
	 */
	public void info(Object obj) {
		logPrintCenter.info(obj,sourceInfo);
	}
	
	/**
	 * 打印info级别消息
	 */
	public void info(Object oid,Object obj) {
		logPrintCenter.info(oid,obj,sourceInfo);
	}
	
	/**
	 * 打印info级别消息
	 */
	public void info(Object obj,Throwable e){
		logPrintCenter.info(obj,e,sourceInfo);
	}
	
	/**
	 * 打印info级别消息
	 */
	public void info(Object oid,Object obj,Throwable e){
		logPrintCenter.info(oid,obj,e,sourceInfo);
	}
	
	/**
	 * 打印warn级别消息
	 */
	public void warn(Object obj) {
		logPrintCenter.warn(obj,sourceInfo);
	}
	
	/**
	 * 打印warn级别消息
	 */
	public void warn(Object oid,Object obj) {
		logPrintCenter.warn(oid,obj,sourceInfo);
	}
	
	/**
	 * 打印warn级别消息
	 */
	public void warn(Object obj,Throwable e) {
		logPrintCenter.warn(obj,e,sourceInfo);
	}
	
	/**
	 * 打印warn级别消息
	 */
	public void warn(Object oid,Object obj,Throwable e) {
		logPrintCenter.warn(oid,obj,e,sourceInfo);
	}
	
	/**
	 * 打印error级别消息
	 */
	public void error(Object obj) {
		logPrintCenter.error(obj,sourceInfo);
	}
	
	/**
	 * 打印error级别消息
	 */
	public void error(Object oid,Object obj) {
		logPrintCenter.error(oid,obj,sourceInfo);
	}
	
	/**
	 * 打印error级别消息
	 */
	public void error(Object obj,Throwable e) {
		logPrintCenter.error(obj,e,sourceInfo);
	}
	
	/**
	 * 打印error级别消息
	 */
	public void error(Object oid,Object obj,Throwable e) {
		logPrintCenter.error(oid,obj,e,sourceInfo);
	}
	
	/**
	 * 打印fatal级别消息
	 */
	public void fatal(Object obj) {
		logPrintCenter.fatal(obj,sourceInfo);
	}
	
	/**
	 * 打印fatal级别消息
	 */
	public void fatal(Object oid,Object obj) {
		logPrintCenter.fatal(oid,obj,sourceInfo);
	}
	
	/**
	 * 打印fatal级别消息
	 */
	public void fatal(Object obj,Throwable e) {
		logPrintCenter.fatal(obj,e,sourceInfo);
	}
	
	/**
	 * 打印fatal级别消息
	 */
	public void fatal(Object oid,Object obj,Throwable e) {
		logPrintCenter.fatal(oid,obj,e,sourceInfo);
	}
}