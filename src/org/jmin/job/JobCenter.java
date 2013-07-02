/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.job;

import java.util.Iterator;
import java.util.List;
import java.util.TimerTask;

/**
 * 作业运行控制器,将即将运行的作业分发到各个作业线程去运行
 *
 * @author Chris Liao
 * @version 1.0
 */

public class JobCenter extends Thread {

  /**
   * 作业队列
   */
  private JobQueue jobQueue;

  /**
   * 工作线池
   */
  private JobWorkerPool workerPool;

  /**
   * 当前线程是否在运行
   */
  private boolean terminated = false;

  /**
   * 构造函数
   */
  public JobCenter(boolean isDaemon){
    this.jobQueue = new JobQueue();
  }

  /**
   * 将作业加入执行队列
   */
  public void schedule(JobWrapper wrapper) {
    this.getJobQueue().add(wrapper);
    Locker.unlock(jobQueue);
  }

  /**
   * 取消作业,从队列中清理出去
   */
  public void cancel(Job job) {
    this.getJobQueue().cancel(job);
  }

  /**
   * 挂起作业
   */
  public void suspend(Job job) {
    this.getJobQueue().suspend(job);
  }

  /**
   *恢复作业
   */
  public void resume(Job job) {
    this.getJobQueue().resume(job);
  }

  /**
   * 取消作业,从队列中清理出去
   */
  public void cancel(TimerTask task) {
    this.getJobQueue().cancel(task);
  }

  /**
   * 挂起作业
   */
  public void suspend(TimerTask task) {
    this.getJobQueue().suspend(task);
  }

  /**
   *恢复作业
   */
  public void resume(TimerTask task) {
    this.getJobQueue().resume(task);
  }


  /**
   *清空对列
   */
  public void shutdown() {
    this.terminated = true;
    this.interrupt();
    this.getJobQueue().clear();
    Locker.unlock(this.jobQueue);
    this.workerPool.terminate();
  }

  /**
   * 获取队列
   */
  private JobQueue getJobQueue() {
    return jobQueue;
  }

  /**
   * 线程方法
   */
  public void run() {
    try {
      while(!this.terminated)
        mainLoop();
    }finally {
      //有人终止了当前线程
      this.getJobQueue().clear();
    }
  }
  
  /**
   * The main job loop.
   */
  private void mainLoop() {
      while (this.jobQueue.isEmpty())
        Locker.lock(jobQueue);

      if(this.jobQueue.isEmpty())
        return;

      List concurrentList = getJobQueue().poll();//作业的并发列表
      if (!concurrentList.isEmpty()) {
        JobWrapper headJobWrapper = (JobWrapper)concurrentList.get(0);//获取并发的第一个作业
        long nextRunTime = headJobWrapper.getNextRunTime();

        /**
         * 1：让主线程等待到运行时刻,才将作业以任务的方式放入任务池，等待并发线程来处理
         * 2：如果等待因为加入新的作业而中断，那么将终止运行下去，需要重新获取作业
         */
        if(nextRunTime > System.currentTimeMillis())
          Locker.lock(jobQueue, nextRunTime - System.currentTimeMillis());
        if (jobQueue.newJobMayBeScheduled()) {// 有新的作业加入了队列,需要重新获取
          jobQueue.resetNewJob();
          return;
        }

        /**
         * 将作业分别放入等待处理池，并唤醒处理线程来处理
         */
        Iterator itor = concurrentList.iterator();

        while (itor.hasNext()) {
          JobWrapper jobWrapper =(JobWrapper) itor.next();
          if(this.needExecute(jobWrapper))
            this.executeJob(jobWrapper);//让作业的时间点运做为的方式提交给任务池
        }

        /**
         * 作业队列按下次运行时间运行的先后次序再排一次
         */
        this.getJobQueue().resort();
     }
 }

  /**
   * 检查当前作业是否运行
   */
  private boolean needExecute(JobWrapper wrapper) {
    if (wrapper.isActive()) {
      if (wrapper.isRepeat()) {
        wrapper.calNextExectionTime();// 计算下一次运行时间
        while (wrapper.getNextRunTime() <= System.currentTimeMillis()) {
          wrapper.calNextExectionTime();// 计算下一次运行时间
        }

        if (wrapper.getTerminatedTime() != -1&& wrapper.getNextRunTime() > wrapper.getTerminatedTime()) {
          wrapper.setCurrentStatus(JobStatus.DEAD);
          if(wrapper.getJob() instanceof TimerTaskJob){
          	this.getJobQueue().remove(((TimerTaskJob)wrapper.getJob()).getTimerTask());// 从活动等待队列中删除
          }else {
          	this.getJobQueue().remove(wrapper.getJob());// 从活动等待队列中删除
          }
        } else {
          if (wrapper.getCurrentStatus() != JobStatus.SUSPENDED)
            wrapper.setCurrentStatus(JobStatus.WAITING);
        }
      } else {//作业只运行一次
      	 if(wrapper.getJob() instanceof TimerTaskJob){
         	this.getJobQueue().remove(((TimerTaskJob)wrapper.getJob()).getTimerTask());// 从活动等待队列中删除
         }else {
         	this.getJobQueue().remove(wrapper.getJob());// 从活动等待队列中删除
         }
      }
    }

    if (wrapper.getCurrentStatus() == JobStatus.SUSPENDED)
      return false;
    else
      return true;
  }

  /**
   * 开始处理该作业，将处于等待的作业才放入处理池，放入池中的作业必须立即处理
   */
  private void executeJob(JobWrapper wrapper) {
    if(this.workerPool==null){
      this.workerPool = new JobWorkerPool();
      this.workerPool.setDaemon(this.isDaemon());
      this.workerPool.start();
    }
    
  	this.workerPool.getWorker().processJob(wrapper);
  }
}
