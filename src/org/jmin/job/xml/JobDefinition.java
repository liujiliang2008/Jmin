/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.job.xml;

import java.util.TimerTask;
import org.jmin.job.Job;

/**
 * 作业的定义类
 *
 * @author Chris Liao
 * @version 1.0
 */
public class JobDefinition {

  /**
   * 作业名
   */
  private String name;
  
  /**
   * 作业
   */
  private Job job;

  /**
   * 作业
   */
  private TimerTask task;

  /**
   * 作业类名
   */
  private String className;

  /**
   * 日期格式
   */
  private String dateFormat;
  
  /**
   * 是否支持月末算法
   */
  private boolean supportMonthLastDay;

  /**
   * 第一次运行时间
   */
  private String firstRunDateTime;

  /**
   * 作业终止时间
   */
  private String terminateDateTime;

  /**
   * 作业运行间隔时间
   */
  private int interval = -1;

  /**
   * 作业运行间隔时间单位
   */
  private String intervalTimeUnit = null;

  /**
   * 作业名
   */
  public String getName() {
    return name;
  }

  /**
   * 作业名
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * 任务
   */
  public TimerTask getTask() {
    return task;
  }

  /**
   * 任务
   */
  public void setTask(TimerTask task) {
    this.task = task;
  }

  /**
   * 作业
   */
  public Job getJob() {
    return job;
  }

  /**
   * 作业
   */
  public void setJob(Job job) {
    this.job = job;
  }

  /**
   * 作业类名
   */
  public String getClassName() {
    return className;
  }

  /**
   * 作业类名
   */
  public void setClassName(String className) {
    this.className = className;
  }

  /**
   * 日期格式
   */
  public String getDateFormat() {
    return dateFormat;
  }

  /**
   * 日期格式
   */
  public void setDateFormat(String dateFormat) {
    this.dateFormat = dateFormat;
  }
  
  /**
   * 是否需要支持月末
   */
  public boolean isSupportMonthLastDay() {
		return supportMonthLastDay;
	}
  
  /**
   * 是否需要支持月末
   */
	public void setSupportMonthLastDay(boolean support) {
		this.supportMonthLastDay = support;
	}
  
  /**
   * 第一次运行时间
   */
  public String getFirstRunDateTime() {
    return firstRunDateTime;
  }

  /**
   * 第一次运行时间
   */
  public void setFirstRunDateTime(String firstRunDateTime) {
    this.firstRunDateTime = firstRunDateTime;
  }

  /**
   * 作业终止时间
   */
  public String getTerminateDateTime() {
    return terminateDateTime;
  }

  /**
   * 作业终止时间
   */
  public void setTerminateDateTime(String terminateDateTime) {
    this.terminateDateTime = terminateDateTime;
  }

  /**
   * 作业运行间隔时间
   */
  public int getInterval() {
    return interval;
  }

  /**
   * 作业运行间隔时间
   */
  public void setInterval(int interval) {
    this.interval = interval;
  }

  /**
   * 作业运行间隔时间单位
   */
  public String getIntervalTimeUnit() {
    return intervalTimeUnit;
  }

  /**
   * 作业运行间隔时间单位
   */
  public void setIntervalTimeUnit(String intervalTimeUnit) {
    this.intervalTimeUnit = intervalTimeUnit;
  }


}