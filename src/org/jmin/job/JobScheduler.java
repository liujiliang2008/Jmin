/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.job;

import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;

/**
 * 作业排期
 *
 * @author Chris Liao
 * @version 1.0
 */

public class JobScheduler {

  /**
   * 日历操作类
   */
  private TimeCalendar calendar;

  /**
   * 作业排期线程
   */
  private JobCenter scheduleThread;

  /**
   * Timer
   */
  public JobScheduler() {
    this(false);
  }

  /**
   * Constructor with a thread mode
   */
  public JobScheduler(boolean isDaemon) {
    this.calendar = TimeCalendar.getTimeCalendar();
    this.scheduleThread = new JobCenter(isDaemon);
    this.scheduleThread.setDaemon(isDaemon);
    this.scheduleThread.start();
  }

  /**
   * 扩展Java timer task
   */
  public void schedule(TimerTask task, int delay) {
    this.schedule(new TimerTaskJob(task),delay);
  }

  /**
   * schedule task
   */
  public void schedule(Job job, int delay) {
    this.schedule(job, delay, TimeUnit.MILLISECOND);
  }

  /**
   * 扩展Java timer task
   */
  public void schedule(TimerTask task, int delay, TimeUnit delayUnit) {
     this.schedule(new TimerTaskJob(task), delay, delayUnit);
  }

  /**
   * schedule job
   */
  public void schedule(Job job, int delay, TimeUnit delayUnit) {
    if (job == null) {
      throw new IllegalArgumentException("Target job can't be null!");
    }
    if (delay <= 0) {
      throw new IllegalArgumentException("Non-positive time delay.");
    }
    if (delayUnit == null) {
      throw new IllegalArgumentException("Invalid time unit.");
    }

    if (job instanceof org.jmin.job.TimerTaskJob) {
      throw new IllegalArgumentException("Conflict job type,you can't name your super job with name: org.jmin.job.TimerTaskJob");
    }
    
    this.calendar.setTimeInMillis(System.currentTimeMillis());
    this.calendar.add(delayUnit.getUnitCode(), delay);
    long executionTime = calendar.getTimeInMillis();
    if (executionTime < System.currentTimeMillis()) {
      throw new IllegalArgumentException(
          "Execution time must be more than current time!");
    }

    JobWrapper wrapper = new JobWrapper(job);
    wrapper.setNextRunTime(executionTime);
    this.schedule(wrapper);
  }


  /**
   * 扩展Java timer task
   */
  public void schedule(TimerTask task, Date time) {
    this.schedule(new TimerTaskJob(task),time);
  }

  /**
   * schedule job
   */
  public void schedule(Job job,Date date) {
    if (job == null) {
      throw new IllegalArgumentException("Target job can't be null!");
    }
    if (date == null) {
      throw new IllegalArgumentException("Invalid execution time!");
    }
    if (date.getTime() <= System.currentTimeMillis()) {
      throw new IllegalArgumentException(
          "Execution time must be more than current time!");
    }
    
    if (job instanceof org.jmin.job.TimerTaskJob) {
      throw new IllegalArgumentException("Conflict job type,you can't name your super job with name: org.jmin.job.TimerTaskJob");
    }
    
    JobWrapper wrapper = new JobWrapper(job);
    wrapper.setNextRunTime(date.getTime());
    this.schedule(wrapper);
  }

  /**
   * 扩展Java timer task
   */
  public void schedule(TimerTask task, int delay, int timeInterval) {
    this.schedule(new TimerTaskJob(task),delay,timeInterval);
  }

  /**
   * schedule job
   */
  public void schedule(Job job, int delay, int timeInterval) {
    this.schedule(job, delay, TimeUnit.MILLISECOND, timeInterval,TimeUnit.MILLISECOND);
  }

  /**
   * 扩展Java timer task
   */
  public void schedule(TimerTask task, Date firstTime, int timeInterval) {
    this.schedule(new TimerTaskJob(task),firstTime,timeInterval);
  }

  /**
   * schedule job
   */
  public void schedule(Job job, Date firstTime,int timeInterval) {
    this.schedule(job,firstTime,timeInterval,TimeUnit.MILLISECOND);
  }


  /**
   * schedule job
   */
  public void schedule(TimerTask task, Date date, int timeInterval,TimeUnit timeIntervalUnit) {
    this.schedule(new TimerTaskJob(task),date,timeInterval,timeIntervalUnit);
  }

  /**
   * schedule job
   */
  public void schedule(Job job, Date date, int timeInterval,TimeUnit timeIntervalUnit) {
    if (job == null) {
      throw new IllegalArgumentException("Target job can't be null!");
    }
    if (timeInterval <= 0) {
      throw new IllegalArgumentException("Non-positive time interval.");
    }
    if (timeIntervalUnit == null) {
      throw new IllegalArgumentException("Invalid interval time unit.");
    }
    if (date == null) {
      throw new IllegalArgumentException("Invalid first execution time.");
    }
    if (date.getTime() <= System.currentTimeMillis()) {
      throw new IllegalArgumentException(
          "The first execution time must be more than current time!");
    }
    
    if (job instanceof org.jmin.job.TimerTaskJob) {
      throw new IllegalArgumentException("Conflict job type,you can't name your super job with name: org.jmin.job.TimerTaskJob");
    }
    
    JobWrapper wrapper = new JobWrapper(job);
    wrapper.setNextRunTime(date.getTime());
    wrapper.setInterval(timeInterval);
    wrapper.setIntervalTimeUnit(timeIntervalUnit);
    this.schedule(wrapper);
  }


  /**
   * schedule job
   */
  public void schedule(TimerTask task,int delay, TimeUnit delayUnit, int timeInterval,TimeUnit timeIntervalUnit) {
    this.schedule(new TimerTaskJob(task),delay,delayUnit,timeInterval,timeIntervalUnit);
  }

  /**
   * schedule job
   */
  public void schedule(Job job, int delay, TimeUnit delayUnit, int timeInterval,TimeUnit timeIntervalUnit) {
    if (job == null) {
      throw new IllegalArgumentException("Target job can't be null!");
    }
    if (delay <= 0) {
      throw new IllegalArgumentException("Non-positive delay time.");
    }
    if (timeInterval <= 0) {
      throw new IllegalArgumentException("Non-positive interval time.");
    }
    if (delayUnit == null) {
      throw new IllegalArgumentException("Invalid delay time unit.");
    }
    if (timeIntervalUnit == null) {
      throw new IllegalArgumentException("Invalid interval time unit.");
    }
    
    if (job instanceof org.jmin.job.TimerTaskJob) {
      throw new IllegalArgumentException("Conflict job type,you can't name your super job with name: org.jmin.job.TimerTaskJob");
    }
    
    calendar.setTimeInMillis(System.currentTimeMillis());
    calendar.add(delayUnit.getUnitCode(), delay);
    long firsttime = calendar.getTimeInMillis();
    if (firsttime < System.currentTimeMillis()) {
      throw new IllegalArgumentException(
          "The first execution time must be more than current time!");
    }

    JobWrapper wrapper = new JobWrapper(job);
    wrapper.setNextRunTime(firsttime);
    wrapper.setInterval(timeInterval);
    wrapper.setIntervalTimeUnit(timeIntervalUnit);
    this.schedule(wrapper);
  }

  /**
   * schedule job
   */
  public void schedule(TimerTask task,Date date, int timeInterval,TimeUnit timeIntervalUnit, Date terminatedDate) {
    this.schedule(new TimerTaskJob(task),date,timeInterval,timeIntervalUnit,terminatedDate);
  }

  /**
   * schedule job
   */
  public void schedule(Job job, Date date, int timeInterval,TimeUnit timeIntervalUnit, Date terminatedDate) {
    if (job == null) {
      throw new IllegalArgumentException("Target job can't be null!");
    }
    if (timeInterval <= 0) {
      throw new IllegalArgumentException("Non-positive time interval.");
    }
    if (timeIntervalUnit == null) {
      throw new IllegalArgumentException("Invalid time unit.");
    }
    if (date == null) {
      throw new IllegalArgumentException("Invalid first execution time.");
    }
    if (terminatedDate == null) {
      throw new IllegalArgumentException("Invalid expired time.");
    }
    if (date.getTime() <= System.currentTimeMillis()) {
      throw new IllegalArgumentException(
          "The first execution time must be more than current time!");
    }
    if (terminatedDate.getTime() <= System.currentTimeMillis()) {
      throw new IllegalArgumentException(
          "The expired time must be more than current time!");
    }
    if (terminatedDate.getTime() <= date.getTime()) {
      throw new IllegalArgumentException(
          "The expired time must be more than the first execution time!");
    }
    
    if (job instanceof org.jmin.job.TimerTaskJob) {
      throw new IllegalArgumentException("Conflict job type,you can't name your super job with name: org.jmin.job.TimerTaskJob");
    }
    
    JobWrapper wrapper = new JobWrapper(job);
    wrapper.setNextRunTime(date.getTime());
    wrapper.setInterval(timeInterval);
    wrapper.setIntervalTimeUnit(timeIntervalUnit);
    wrapper.setTerminatedTime(terminatedDate.getTime());
    this.schedule(wrapper);
  }


  /**
   * schedule timer task
   */
  public void schedule(TimerTask task,Date launchDate, int timeInterval,TimeUnit timeIntervalUnit,int termianteDelayValue,TimeUnit terminateIntervalUnit) {
    this.schedule(new TimerTaskJob(task),launchDate,timeInterval,timeIntervalUnit,termianteDelayValue,terminateIntervalUnit);
  }

  /**
   * schedule job
   */
  public void schedule(Job job, Date launchDate, int timeInterval,TimeUnit timeIntervalUnit,int termianteDelayValue,TimeUnit terminateIntervalUnit) {
    if (job == null) {
      throw new IllegalArgumentException("Target job can't be null!");
    }
    if (timeInterval <= 0) {
      throw new IllegalArgumentException("Non-positive time interval.");
    }
    if (timeIntervalUnit == null) {
      throw new IllegalArgumentException("Null interval time unit.");
    }
    if (launchDate == null) {
      throw new IllegalArgumentException("Invalid first launch time.");
    }
    if (termianteDelayValue < 0) {
      throw new IllegalArgumentException("Invalid terminated delay time.");
    }
    if (terminateIntervalUnit == null) {
      throw new IllegalArgumentException("Null termination delay time unit.");
    }

    Calendar calendar = Calendar.getInstance();
    calendar.add(terminateIntervalUnit.getUnitCode(), termianteDelayValue);
    long temTeminatedDelayTime = calendar.getTime().getTime();

    if (launchDate.getTime() <= System.currentTimeMillis()) {
      throw new IllegalArgumentException(
          "The first execution time must be more than current time!");
    }
    if (temTeminatedDelayTime <= System.currentTimeMillis()) {
      throw new IllegalArgumentException(
          "The expired time must be more than current time!");
    }
    if (temTeminatedDelayTime <= launchDate.getTime()) {
      throw new IllegalArgumentException(
          "The expired time must be more than the first execution time!");
    }
    
    if (job instanceof org.jmin.job.TimerTaskJob) {
      throw new IllegalArgumentException("Conflict job type,you can't name your super job with name: org.jmin.job.TimerTaskJob");
    }
  
    JobWrapper wrapper = new JobWrapper(job);
    wrapper.setNextRunTime(launchDate.getTime());
    wrapper.setInterval(timeInterval);
    wrapper.setIntervalTimeUnit(timeIntervalUnit);
    wrapper.setTerminatedTime(temTeminatedDelayTime);
    this.schedule(wrapper);
  }

  /**
   * schedule job
   */
  public void schedule(TimerTask task,int launchDelayValue,TimeUnit launchIntervalUnit, int timeInterval,TimeUnit timeIntervalUnit, int termianteDelayValue,TimeUnit terminateIntervalUnit) {
     this.schedule(new TimerTaskJob(task),launchDelayValue,launchIntervalUnit,timeInterval,timeIntervalUnit,termianteDelayValue,terminateIntervalUnit);
  }

  /**
   * schedule job
   */
  public void schedule(Job job, int launchDelayValue,TimeUnit launchIntervalUnit, int timeInterval,TimeUnit timeIntervalUnit, int termianteDelayValue,TimeUnit terminateIntervalUnit) {
    if (job == null) {
      throw new IllegalArgumentException("Target job can't be null!");
    }
    if (timeInterval <= 0) {
      throw new IllegalArgumentException("Non-positive time interval.");
    }
    if (timeIntervalUnit == null) {
      throw new IllegalArgumentException("Null interval time unit.");
    }
    if (launchDelayValue < 0) {
      throw new IllegalArgumentException("Invalid launch delay time value.");
    }
    if (launchIntervalUnit == null) {
      throw new IllegalArgumentException("Null delay time unit.");
    }
    if (termianteDelayValue < 0) {
      throw new IllegalArgumentException("Invalid terminated delay time value.");
    }
    if (terminateIntervalUnit == null) {
      throw new IllegalArgumentException("Null delay time unit.");
    }

    Calendar launchCalendar = Calendar.getInstance();
    launchCalendar.add(launchIntervalUnit.getUnitCode(), launchDelayValue);
    long launchTime = launchCalendar.getTime().getTime();

    Calendar endCalendar = Calendar.getInstance();
    endCalendar.add(terminateIntervalUnit.getUnitCode(), termianteDelayValue);
    long endTime = endCalendar.getTime().getTime();

    if (launchTime <= System.currentTimeMillis()) {
      throw new IllegalArgumentException(
          "The first execution time must be more than current time!");
    }
    if (endTime <= System.currentTimeMillis()) {
      throw new IllegalArgumentException(
          "The expired time must be more than current time!");
    }
    if (endTime <= launchTime) {
      throw new IllegalArgumentException(
          "The expired time must be more than the first execution time!");
    }
    
    if (job instanceof org.jmin.job.TimerTaskJob) {
      throw new IllegalArgumentException("Conflict job type,you can't name your super job with name: org.jmin.job.TimerTaskJob");
    }
    
    JobWrapper wrapper = new JobWrapper(job);
    wrapper.setNextRunTime(launchTime);
    wrapper.setInterval(timeInterval);
    wrapper.setIntervalTimeUnit(timeIntervalUnit);
    wrapper.setTerminatedTime(endTime);
    this.schedule(wrapper);
  }


  /**
   * schedule job
   */
  public void schedule(TimerTask task, int delay, TimeUnit delayUnit, int timeInterval,TimeUnit timeIntervalUnit, Date terminatedDate) {
    this.schedule(new TimerTaskJob(task),delay,delayUnit,timeInterval,timeIntervalUnit,terminatedDate);
  }

  /**
   * schedule job
   */
  public void schedule(Job job, int delay, TimeUnit delayUnit, int timeInterval,TimeUnit timeIntervalUnit, Date terminatedDate) {
    if (job == null) {
      throw new IllegalArgumentException("Target job can't be null!");
    }
    if (delay <= 0) {
      throw new IllegalArgumentException("Non-positive delay time.");
    }
    if (timeInterval <= 0) {
      throw new IllegalArgumentException("Non-positive time interval.");
    }
    if (delayUnit == null) {
      throw new IllegalArgumentException("Invalid time unit.");
    }
    if (timeIntervalUnit == null) {
      throw new IllegalArgumentException("Invalid time unit.");
    }
    if (terminatedDate == null) {
      throw new IllegalArgumentException("Invalid expired time.");
    }

    calendar.setTimeInMillis(System.currentTimeMillis());
    calendar.add(delayUnit.getUnitCode(), delay);
    long firsttime = calendar.getTimeInMillis();
    if (firsttime < System.currentTimeMillis()) {
      throw new IllegalArgumentException(
          "The first execution time must be more than current time!");
    }
    if (terminatedDate.getTime() <= System.currentTimeMillis()) {
      throw new IllegalArgumentException(
          "The expired time must be more than current time!");
    }
    if (terminatedDate.getTime() <= firsttime) {
      throw new IllegalArgumentException(
          "The expired time must be more than the first execution time!");
    }
    
    if (job instanceof org.jmin.job.TimerTaskJob) {
      throw new IllegalArgumentException("Conflict job type,you can't name your super job with name: org.jmin.job.TimerTaskJob");
    }
    
    JobWrapper wrapper = new JobWrapper(job);
    wrapper.setNextRunTime(firsttime);
    wrapper.setInterval(timeInterval);
    wrapper.setIntervalTimeUnit(timeIntervalUnit);
    wrapper.setTerminatedTime(terminatedDate.getTime());
    this.schedule(wrapper);
  }

  /**
   *将作业放入执行队列
   */
  private void schedule(JobWrapper jobWrapper) {
    this.scheduleThread.schedule(jobWrapper);
  }

  /**
   * 从队列中取消作业
   */
  public void cancel(Job job) {
    this.scheduleThread.cancel(job);
  }

  /**
   * 从队列中取消作业
   */
  public void cancel(TimerTask task) {
    this.scheduleThread.cancel(task);
  }

  /**
   * 挂起作业
   */
  public void suspend(Job job) {
    this.scheduleThread.suspend(job);
  }

  /**
  * 挂起作业
  */
 public void suspend(TimerTask task) {
   this.scheduleThread.suspend(task);
  }

  /**
   * 恢复作业
   */
  public void resume(Job job) {
    this.scheduleThread.suspend(job);
  }

  /**
   * 恢复作业
   */
  public void resume(TimerTask task) {
    this.scheduleThread.suspend(task);
  }

  /**
   * 取消当前所有排期
   */
  public void shutdown() {
    this.scheduleThread.shutdown();
  }
}
