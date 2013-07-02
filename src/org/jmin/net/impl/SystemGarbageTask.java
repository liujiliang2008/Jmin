/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.net.impl;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 垃圾回收唤醒
 * 
 * @author Chris Liao
 */
class SystemGarbageTask extends TimerTask{
	
	/**
	 * 构造函数:每一分钟触发一次
	 */
	public SystemGarbageTask(){
		 Timer timer = new Timer(true);
		 timer.schedule(this,30000);
	}
	
	/**
	 * 任务方法
	 */
	public void run() {
		this.gc();
	}
	
	/**
	 * 垃圾回收
	 */
	public void gc(){
		System.gc();
		System.runFinalization();
	}
}