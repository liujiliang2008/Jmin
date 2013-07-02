/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.job;

import java.util.TimerTask;

/**
 * 定时执行的作业,该作业需要能够快速运行完毕.
 *
 * @author Chris Liao
 * @version 1.0
 */

class TimerTaskJob implements Job {

  /**
   * Timer task
   */
  private TimerTask task;

  /**
   * 构造函数
   */
  public TimerTaskJob(TimerTask task) {
    this.task = task;
  }

  /**
   * Timer task
   */
  public TimerTask getTimerTask() {
    return task;
  }

  /**
   * 作业方法
   */
  public void run() {
    this.task.run();
  }
}