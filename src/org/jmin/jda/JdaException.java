/*
 *Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.sql.SQLException;

/**
 * Jdbc异常
 *
 * @author Chris Liao
 * @version 1.0
 */

public class JdaException extends SQLException {
	
	/**
	 * 产生异常的SQL Id
	 */
	private String sqlId;
	
	/**
	 * 异常触发原因
	 */
	private Throwable cause=null;

	/**
	 * 构造函数
	 */
	public JdaException(String sqlId){
		this(sqlId,(String)null);
	}
	
	/**
	 * 构造函数
	 */
	public JdaException(String sqlId,String message) {
		this(sqlId,message,null);
	}
	
	/**
	 * 构造函数
	 */
	public JdaException(String sqlId,Throwable cause) {
		this(sqlId,null,cause);
	}
	
	/**
	 * 构造函数
	 */
	public JdaException(String sqlId,String message,Throwable cause) {
		super(message);
		this.sqlId =sqlId;
		this.cause = cause;
	}
	
	/**
	 * 获得触发原因
	 */
	public String getsqlId() {
		return this.sqlId;
	}
	
	/**
	 * 获得触发原因
	 */
	public Throwable getCauseException() {
		return cause;
	}
	
	/**
	 * 重写消息
	 */
  public String getMessage() {
  	if(sqlId!=null && sqlId.length() >0)
  		return "SQL("+sqlId+")" + ((super.getMessage()==null)?"":super.getMessage());
  	else
  		return (super.getMessage()==null)?"":super.getMessage();
	}
  
	/**
	 * 打印堆栈，重写方法
	 */
	public void printStackTrace() {
		this.printStackTrace(System.err);
	}
	
	/**
	 * 打印堆栈，重写方法
	 */
	public void printStackTrace(PrintStream s) {
		synchronized (s) {
			 super.printStackTrace(s);
			 if(cause!=null){
					s.print("Caused by: ");
					cause.printStackTrace(s);
			 }
		}
	}
	
	/**
	 * 打印堆栈，重写方法
	 */
	public void printStackTrace(PrintWriter w) {
		synchronized (w) {
			 super.printStackTrace(w);
			 if(cause!=null){
					w.print("Caused by: ");
					cause.printStackTrace(w);
			 }
		}
	}
}