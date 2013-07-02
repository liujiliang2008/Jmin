/*
*Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.test.job;

import org.jmin.job.xml.JobScheduleXmlRunner;

/**
 * Job 测试例子
 */

public class JobXmlTest {

  /**
   * 测试方法入口
   */
  public static void main(String args[]) throws Exception {
  	JobScheduleXmlRunner starter = new JobScheduleXmlRunner();
    starter.run("org/jmin/test/job/job.xml","JobScheduler");
  }
}