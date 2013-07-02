/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.job;

/**
 * 作业工作线程
 * 
 * @author Chris
 */
public class JobWorker extends Thread {
 
	/**
	 * 当前线程的父线程
	 */
	private Thread parentThread;
	
	/**
	 * 最近活动时间
	 */
	private long recentActiveTime;

	/**
	 * 当前线程是否在运行
	 */
	private boolean running = false;

	/**
	 * 当前线程是否在运行
	 */
	private boolean terminated = false;
 
  /**
   * 需要运行的作业
   */
  private JobWrapper targetWrapper = null;
  
	/**
	 * 构造函数,设置父线程
	 */
	public JobWorker() {
    this.recentActiveTime = System.currentTimeMillis();
		this.parentThread = Thread.currentThread();
	}

	/**
	 * 标志量,是否处在运行状态:内部使用
	 */
	private synchronized void setRunning(boolean running) {
		this.running = running;
	}

	/**
	 * 标志量,是否处在运行状态:供调度线程调用
	 */
	public synchronized boolean isRunning() {
		return this.running;
	}

	/**
	 * 终止当前线程：供调度线程调用
	 */
	public synchronized boolean isTerminated() {
		return this.terminated;
	}
	
	/**
	 * 最近依次运行时间
	 */
	public synchronized long getRecentActiveTime() {
		return recentActiveTime;
	}
	
	/**
	 * 终止当前线程：供池守护线程调用
	 */
	public synchronized void terminate() {
		this.terminated = true;
		this.wakeup();
		this.interrupt();
	}

	/**
	 * 从外部设置进来一个作业，并请求当前Thread来运行该作业：供调度线程调用
	 */
	public synchronized void processJob(JobWrapper jobWrapper) {
		this.targetWrapper = jobWrapper;
		this.recentActiveTime = System.currentTimeMillis();
		this.setRunning(true);
		this.wakeup();
	}

	/**
	 * 线程方法
	 */
	public void run() {
		while (this.parentThread.isAlive() && !this.isTerminated()) {
			if (this.targetWrapper != null) {
				this.executeJob(this.targetWrapper);
				this.targetWrapper = null;
				this.setRunning(false);
        this.recentActiveTime = System.currentTimeMillis();
			}
			this.sleep();
		}
	}

	/**
	 * 真正执行作业
	 */
	private void executeJob(JobWrapper jobWrapper) {
		try {
			jobWrapper.getJob().run();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	/**
	 * 唤醒当前线程开始运行
	 */
	private void wakeup() {
		synchronized (this) {
			this.notify();
		}
	}

	/**
	 * 当前Thread开始睡眠
	 */
	private void sleep() {
		synchronized (this) {
			try {
				this.wait();
			} catch (Exception e) {
			}
		}
	}
}
