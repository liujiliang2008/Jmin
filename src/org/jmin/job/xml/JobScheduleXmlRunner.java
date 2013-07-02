/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.job.xml;

import org.jmin.ioc.BeanContext;
import org.jmin.ioc.impl.config.BeanContextImpl;

/**
 * 系统应用作业调度启动
 *
 * @author Chris Liao
 */

public class JobScheduleXmlRunner{
	
	/**
	 * 默认Ioc Id
	 */
	private static final String JobSchedule="JobScheduler";
	
  /**
   * 运行默认排期计划
   */
  public void run(String file)throws JobException{
  	run(file,JobSchedule);
  }

  /**
   * 运行默认排期计划
   */
  public void run(String file,String jobSchedulerID)throws JobException{
    try{
    	BeanContext context = new BeanContextImpl(file);
      JobScheduleDefinition scheduleDefinition =(JobScheduleDefinition)(context.getBean(jobSchedulerID));
      scheduleDefinition.run();
    }catch(Exception e){
     throw new JobException(e.getMessage());
    }
  }
}