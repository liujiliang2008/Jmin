/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.job;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 工作线程池，当调度器需要运行作业的时候，先从池获取一个线程，
 * 然后将作业交给作业线程去运行
 *
 * @author Chris
 */
public class JobWorkerPool extends Thread{

  /**
   * 工作线程列表
   */
  private List workerList;

  /**
   * 当前线程的父线程
   */
  private Thread parentThread;

  /**
   * 当前线程是否在运行
   */
  private boolean terminated;

  /**
   * 最小工人数：10个
   */
  private int minWokerCount=10;

  /**
   * 最大工人闲置时间：十分钟
   */
  private long maxWorkIdleTime=600000;

  /**
   * 构造函数
   */
  public JobWorkerPool(){
    this.workerList = new ArrayList();
    this.parentThread = Thread.currentThread();
  }
  
  /**
   * 终止当前线程：供池守护线程调用
   */
  public synchronized void terminate() {
    this.terminated = true;
    this.interrupt();
  }
  
  /**
   * 线程方法
   */
  public void run(){
    Object SYNObject = new Object();
    while (this.parentThread.isAlive()&& !this.terminated) {
      this.clearIdleWorkers();
      Locker.lock(SYNObject,this.maxWorkIdleTime);//锁定十分钟
    }
  }
  
  /**
   * 获得闲置的工作线程
   */
  public synchronized JobWorker getWorker(){
    JobWorker targetWorker = null;
    Iterator itor = this.workerList.iterator();
    while(itor.hasNext()){
      JobWorker worker = (JobWorker)itor.next();
      if(!worker.isRunning()&& !worker.isTerminated()){
        targetWorker = worker;
        break;
      }
    }

    if(targetWorker == null){
      targetWorker = new JobWorker();
      targetWorker.setDaemon(parentThread.isDaemon());
      this.workerList.add(targetWorker);
      targetWorker.start();
    }
    return targetWorker;
  }
  
  /**
   * 清理一些长时间闲置的worker
   */
  private synchronized void clearIdleWorkers() {
    int currentWorkerCount = this.workerList.size(); 
    if (currentWorkerCount > this.minWokerCount) {
      Iterator itor = this.workerList.iterator();
      while (itor.hasNext()) {
        JobWorker worker = (JobWorker) itor.next();
        if (!worker.isRunning()&& !worker.isTerminated()&& System.currentTimeMillis() - worker.getRecentActiveTime() >= maxWorkIdleTime) {
          worker.terminate();
          itor.remove();          
          if(--currentWorkerCount <= this.minWokerCount)
            break;
        }
      }
    }
  }
}