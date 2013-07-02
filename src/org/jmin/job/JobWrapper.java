/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.job;

/**
 * 定时执行的作业的包裹类
 *
 * @author Chris Liao
 * @version 1.0
 */

public class JobWrapper implements Comparable{

  /**
   * 需要运行的定时作业
   */
  private Job job;

  /**
   * 作业终止时间
   */
  private long terminatedTime = -1;

  /**
   * 作业运行间隔时间
   */
  private int interval = -1;

  /**
   * 作业运行间隔时间单位
   */
  private TimeUnit intervalTimeUnit = null;

  /**
   * 作业的当前状态
   */
  private int currentState = JobStatus.VIRGIN;

  /**
   * 日历操作类
   */
  private TimeCalendar calendar = TimeCalendar.getTimeCalendar();
  
  /**
   * 构造函数
   */
  public JobWrapper(Job job) {
    this.job = job;
  }

  /**
   * 获取作业
   */
  public Job getJob() {
    return job;
  }


  /**
   * 作业是否需要重复运行
   */
  public boolean isRepeat() {
    return (interval > 0);
  }

  /**
   * 下一次运行时间
   */
  public long getNextRunTime() {
    return calendar.getTimeInMillis();
  }

  /**
   * 下一次运行时间
   */
  public void setNextRunTime(long nextRunTime) {
    this.calendar.setTimeInMillis(nextRunTime);
  }

  /**
   * 作业终止时间
   */
  public long getTerminatedTime() {
    return terminatedTime;
  }

  /**
   * 作业终止时间
   */
  public void setTerminatedTime(long terminatedTime) {
    this.terminatedTime = terminatedTime;
  }

  /**
   * 作业运行的间隔时间
   */
  public int getInterval() {
    return interval;
  }

  /**
   * 作业运行的间隔时间
   */
  public void setInterval(int interval) {
    this.interval = interval;
  }

  /**
   * 作业运行的间隔时间单位
   */
  public TimeUnit getIntervalTimeUnit() {
    return intervalTimeUnit;
  }

  /**
   * 作业运行的间隔时间单位
   */
  public void setIntervalTimeUnit(TimeUnit intervalTimeUnit) {
    this.intervalTimeUnit = intervalTimeUnit;
  }

  /**
   * 获得作业的当前状态
   */
  public int getCurrentStatus() {
    return currentState;
  }

  /**
   * 获得作业的当前状态
   */
  public void setCurrentStatus(int state) {
    this.currentState = state;
  }

  /**
   * 计算下次运行时间
   */
  public void calNextExectionTime() {
    if(interval != 0) {
      this.calendar.add(intervalTimeUnit.getUnitCode(),interval);
    }
  }

  /**
   * 判断当前作业是否为活动状态
   */
  public boolean isActive() {
    return currentState == JobStatus.WAITING
        || currentState == JobStatus.SUSPENDED
        || currentState == JobStatus.RUNNING;
  }

  /**
   * 判断是否相等
   */
  public boolean equals(Object obj) {
    if (obj instanceof JobWrapper) {
      JobWrapper other = (JobWrapper) obj;
      return this.job.equals(other.job);
    }
    else {
      return false;
    }
  }
  
  /**
   * 比较对象
   */
  public int compareTo(Object o) {
    JobWrapper other = (JobWrapper) o;
    if(other == null || !(other instanceof JobWrapper)){
      return -1; 
    }else{
      if (this.getNextRunTime() > other.getNextRunTime()) {
        return 1;   
      } else if (this.getNextRunTime() == other.getNextRunTime()) {
        return 0;    
      } else {
        return -1; 
      }
    }
  }
  
  /**
   * 重写方法
   */
  public String toString(){
    return this.job.toString();
  }
}
