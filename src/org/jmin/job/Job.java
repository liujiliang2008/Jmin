/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.job;

/**
 * 定时执行的作业,该作业需要能够快速运行完毕.
 *
 * @author Chris Liao
 * @version 1.0
 */
public interface Job extends Runnable {

  /**
   * 作业方法
   */
  public void run();

}