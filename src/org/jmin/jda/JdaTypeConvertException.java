/*
 *Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * 转换异常
 *
 * @author Chris Liao
 * @version 1.0
 */

public class JdaTypeConvertException extends JdaException {
	
	/**
	 * 异常触发原因
	 */
	private Throwable cause=null;

 
	/**
	 * 构造函数
	 */
	public JdaTypeConvertException(String message) {
		this(message,null);
	}
	
	/**
	 * 构造函数
	 */
	public JdaTypeConvertException(Throwable cause) {
		this(null,cause);
	}
	
	/**
	 * 构造函数
	 */
	public JdaTypeConvertException(String message,Throwable cause) {
		super(message);
		this.cause = cause;
	}
	
	/**
	 * 获得触发原因
	 */
	public Throwable getCauseException() {
		return cause;
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