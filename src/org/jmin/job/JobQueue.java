/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.job;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

/**
 * 存放定时作业的容器
 *
 * @author Chris Liao
 * @version 1.0
 */
public class JobQueue {

  /**
   * 存放将要执行的作业
   */
  private ArrayList pollList;

  /**
   * 内部存放活动作业的列表
   */
  private ArrayList jobWrapperList;

  /**
   * 内部存放活动作业与Wrapper的包装
   */
  private Map jobWrapperMap;

  /**
   * 是否存在新增的作业
   */
  private boolean newJobMayBeScheduled = false;

  /**
   * 构造函数
   */
  public JobQueue() {
    this.pollList = new ArrayList();
    this.jobWrapperList = new ArrayList();
    this.jobWrapperMap = new HashMap();
  }

  /**
   * 将对象压入队列的尾部
   */
  public synchronized void add(JobWrapper wrapper) {
    if (!jobWrapperList.contains(wrapper)) {
      this.jobWrapperList.add(wrapper);
      wrapper.setCurrentStatus(JobStatus.WAITING);
      if(wrapper.getJob() instanceof TimerTaskJob){
      	this.jobWrapperMap.put(((TimerTaskJob)wrapper.getJob()).getTimerTask(),wrapper);
      }else {
      	this.jobWrapperMap.put(wrapper.getJob(), wrapper);
      }
      this.newJobMayBeScheduled = true;
      this.resort();
    }
  }

  /**
   * 将队列中元素首作业取出,顺便也将与它在下一时间并发的作业也取出
   */
  public synchronized List poll() {
    JobWrapper jobWrapper=null;
    this.pollList.clear();
    int size = this.size();
    if(size >0){
      JobWrapper headJobWrapper =(JobWrapper)this.jobWrapperList.get(0);
      this.pollList.add(headJobWrapper);
      if(size > 1){
        Iterator itor = jobWrapperList.iterator();
        while(itor.hasNext()){
          jobWrapper =(JobWrapper)itor.next();
          if(headJobWrapper!=jobWrapper){
            if(headJobWrapper.getNextRunTime() == jobWrapper.getNextRunTime()){
              this.pollList.add(jobWrapper);
            }else {
              break;
            }
          }
        }
      }
    }
    return this.pollList;
  }

  /**
   * 将队列依据运行下次时间进行先后排序
   */
  public synchronized void resort() {
    Collections.sort(this.jobWrapperList);
  }

  /**
   * 是否存在新的作业
   */
  public synchronized boolean newJobMayBeScheduled() {
    return this.newJobMayBeScheduled;
  }

  /**
   * 是否存在新的作业
   */
  public synchronized void resetNewJob() {
    this.newJobMayBeScheduled = false;
  }

  /**
   * 是否存在新的作业
   */
  public synchronized int size() {
    return this.jobWrapperList.size();
  }

  /**
   * 取消作业
   */
  public synchronized void cancel(Job job) {
    JobWrapper wraper = this.getWrapper(job);
    if (wraper != null) {
      wraper.setCurrentStatus(JobStatus.DEAD);
      this.jobWrapperMap.remove(job);
      this.jobWrapperList.remove(wraper);
    }
  }

  /**
   * 挂起作业
   */
  public synchronized void suspend(Job job) {
    JobWrapper wraper = this.getWrapper(job);
    if (wraper != null) {
      wraper.setCurrentStatus(JobStatus.SUSPENDED);
    }
  }

  /**
   *恢复作业
   */
  public synchronized void resume(Job job) {
    JobWrapper wraper = this.getWrapper(job);
    if (wraper != null) {
      wraper.setCurrentStatus(JobStatus.WAITING);
    }
  }

  /**
   *删除作业
   */
  public synchronized void remove(Job job) {
    JobWrapper wraper = (JobWrapper) jobWrapperMap.remove(job);
    if (wraper != null) {
      wraper.setCurrentStatus(JobStatus.DEAD);
      jobWrapperList.remove(wraper);
    }
  }
  
  
  /**
   * 取消作业
   */
  public synchronized void cancel(TimerTask task) {
    JobWrapper wraper = this.getWrapper(task);
    if (wraper != null) {
      wraper.setCurrentStatus(JobStatus.DEAD);
      this.jobWrapperMap.remove(task);
      this.jobWrapperList.remove(wraper);
    }
  }

  /**
   * 挂起作业
   */
  public synchronized void suspend(TimerTask task) {
    JobWrapper wraper = this.getWrapper(task);
    if (wraper != null) {
      wraper.setCurrentStatus(JobStatus.SUSPENDED);
    }
  }

  /**
   *恢复作业
   */
  public synchronized void resume(TimerTask task) {
    JobWrapper wraper = this.getWrapper(task);
    if (wraper != null) {
      wraper.setCurrentStatus(JobStatus.WAITING);
    }
  }

  /**
   *恢复作业
   */
  public synchronized void remove(TimerTask task) {
    JobWrapper wraper = (JobWrapper) jobWrapperMap.remove(task);
    if (wraper != null) {
      wraper.setCurrentStatus(JobStatus.DEAD);
      jobWrapperList.remove(wraper);
    }
  }
  
  /**
   *清空对列
   */
  public synchronized void clear() {
    Iterator itor = jobWrapperList.iterator();
    while (itor.hasNext()) {
      JobWrapper wrapper = (JobWrapper) itor.next();
      wrapper.setCurrentStatus(JobStatus.DEAD);
    }

    jobWrapperMap.clear();
    jobWrapperList.clear();
    this.newJobMayBeScheduled = false;
  }

  /**
   * 找出对应的Wrapper
   */
  private synchronized JobWrapper getWrapper(Object job) {
    return (JobWrapper) jobWrapperMap.get(job);
  }

  /**
   * 判断队列是否为空
   */
  public synchronized boolean isEmpty() {
    return jobWrapperList.isEmpty();
  }
}