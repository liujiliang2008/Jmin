/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.test.job;

import org.jmin.job.JobScheduler;
import org.jmin.job.TimeUnit;

/**
 * 作业启动
 *
 * @author Chris Liao
 */

public class JobTest {
  /**
   * 启动方法
   */
  public static void main(String[] args)  {
    JobScheduler schedule = new JobScheduler();
    schedule.schedule(new TimeJob("Job1"), 1,TimeUnit.SECOND,1,TimeUnit.MINUTE);
    schedule.schedule(new TimeJob("Job2"), 2000,1000);
    schedule.schedule(new TimeJob("Job3"), 8000,1000);
    schedule.schedule(new TimeJob("Job4"), 6000);
    schedule.schedule(new TimeJob("Job5"), 5000);
  }
}