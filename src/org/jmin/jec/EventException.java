/*
 *Copyright (c) jmin Organization. All rights reserved.
 */
package org.jmin.jec;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * 事件异常
 *
 * @author Chris Liao
 * @version 1.0
 */

public class EventException extends Exception {

	/**
	 * 异常触发原因
	 */
	private Throwable cause;
	
	/**
	 * 构造函数
	 */
	public EventException(String message){
		this(message,null);
	}
	
	/**
	 * 构造函数
	 */
	public EventException(Throwable cause) {
		this(null,cause);
	}
	
	/**
	 * 构造函数
	 */
	public EventException(String message,Throwable cause) {
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