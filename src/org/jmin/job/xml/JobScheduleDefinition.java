/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.job.xml;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import org.jmin.job.Job;
import org.jmin.job.JobScheduler;
import org.jmin.job.TimeCalendar;
import org.jmin.job.TimeUnit;
import org.jmin.log.Logger;
/**
 * 系统应用作业调度
 *
 * @author Chris Liao
 */

public class JobScheduleDefinition {
  
  /**
   * Logger
   */
  private static Logger logger = Logger.getLogger(JobScheduleDefinition.class);

  /**
   * 存放作业定义
   */
  private List jobDefinitionList;

  /**
   * 是否是后台运行作业调度
   */
  private boolean daemon = false;

  /**
   *作业定义
   */
  public void setJobDefinitionList(List jobDefinitionList) {
    this.jobDefinitionList = jobDefinitionList;
  }

  /**
   * 是否是后台运行作业调度
   */
  public void setDaemon(boolean daemon) {
    this.daemon = daemon;
  }

  /**
   * 运行作业调度
   */
  public void run(){
    JobScheduler schedule = new JobScheduler(this.daemon);
    for(int i=0;i<jobDefinitionList.size();i++){
      this.add(schedule,(JobDefinition)jobDefinitionList.get(i));
    }
  }

  /**
   * 加入作业调度
   */
  private void add(JobScheduler scheduler,JobDefinition definition){
    String jobName = definition.getName();
    String dateFormat =definition.getDateFormat();
    String jobClassName = definition.getClassName();
    String firstRunTime = definition.getFirstRunDateTime();
    String terminateDateTime = definition.getTerminateDateTime();
    int interval = definition.getInterval();
    String intervalTimeUnit = definition.getIntervalTimeUnit();
    boolean supportMothEnd = definition.isSupportMonthLastDay();

    Job job = definition.getJob();
    TimerTask task = definition.getTask();

    //long launchTime = -1;
    TimeUnit timeUnit = null;

    Date terminateTime = null;
    TimeCalendar launchCalendar = TimeCalendar.getTimeCalendar();

    SimpleDateFormat formater =null;
    if(!this.isNull(dateFormat))
      formater = new SimpleDateFormat(dateFormat.trim());
    else
      formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 作业实例构造
     */
    if(job == null && task == null){
      if(!this.isNull(jobClassName)){
        try{
          Object obj = createJobInstance(jobClassName.trim(),true,this.getClass().getClassLoader());
          if(obj instanceof Job){
          	job = (Job)obj;
          }else if(obj instanceof TimerTask){
          	task = (TimerTask)obj;
          }else {
          	throw new RuntimeException("Class name is not a valid job type");
          }
        }catch(Exception e){
          logger.error("Job[" + jobName+"] has been abandon,can't create instance from class:"+ jobClassName);
          return;
        }
      }else {
        logger.error("Job[" + jobName+"] has been abandon,job class not be configed " );
        return;
      }
    }

    if(job!=null){
	    try{
	    	Method setNameMethod =job.getClass().getMethod("setName",new Class[]{String.class});
	    	setNameMethod.invoke(job,new Object[]{jobName});
	    }catch(Throwable e ){
	    }
  	}

    if(task!=null){
    try{
    	Method setNameMethod =task.getClass().getMethod("setName",new Class[]{String.class});
    	setNameMethod.invoke(task,new Object[]{jobName});
    }catch(Throwable e ){
    }
   }
    
    /**
     * 作业终止时间
     */
    if(!this.isNull(terminateDateTime)){
      try{
        terminateTime = formater.parse(terminateDateTime);
        if(terminateTime.getTime()< System.currentTimeMillis()){//已经失效
          logger.error("Job[" + jobName+"] has been abandon,reason: termination time is less current time");
          return;
        }
      }catch(ParseException e){
        e.printStackTrace();
        return;
      }
    }

    /**
     * 作业执行间隔执行时间
     */
    if(interval > 0 && !this.isNull(intervalTimeUnit)){
      timeUnit = TimeUnit.toUnit(intervalTimeUnit);
    }
   
    try{
    	long launchTime = 0;
      if(this.isNull(firstRunTime)){
      	launchCalendar.add(TimeCalendar.SECOND,10);
      	launchTime = launchCalendar.getTimeInMillis();
      }else{
      	 Date date = formater.parse(firstRunTime);
         launchTime = date.getTime();
      }
      
      launchCalendar.setTimeInMillis(launchTime);
      launchCalendar.setSupportMonthLastDay(supportMothEnd);
    
      if(interval >0 && timeUnit!= null){//间隔多多次执行
        while(launchCalendar.getTimeInMillis() < System.currentTimeMillis())
        	launchCalendar.add(timeUnit.getUnitCode(),interval);
      }else {//单次执行
        if(launchTime < System.currentTimeMillis()){
          logger.error("Job[" + jobName+"] has been abandon, reason: launch time is less current time");
          return;
        }
      }
    }catch(ParseException e){
      e.printStackTrace();
      return;
    }
    
   
    String firstRuntime = formater.format(launchCalendar.getTime());

    if(interval > 0 && timeUnit !=null){//间隔执行
      if(terminateTime == null){
        if(job!=null)
          scheduler.schedule(job,launchCalendar.getTime(),interval,timeUnit);
        else if(task!=null)
          scheduler.schedule(task,launchCalendar.getTime(),interval,timeUnit);
          logger.info("Job[" + jobName+"] has been scheduled at time: "+ firstRuntime);
      }else{
        if(job!=null)
          scheduler.schedule(job,launchCalendar.getTime(),interval,timeUnit,terminateTime);
        else if(task!=null)
          scheduler.schedule(task,launchCalendar.getTime(),interval,timeUnit,terminateTime);
          logger.info("Job[" + jobName+"] has been scheduled at time: "+ firstRuntime);
      }
    }else {//只执行一次

      if(job!=null)
        scheduler.schedule(job,launchCalendar.getTime());
      else if(task!=null)
        scheduler.schedule(task,launchCalendar.getTime());
        logger.info("Job[" + jobName+"] has been scheduled at time: "+ firstRuntime);
    }
  }
  
  /**
   * instantiate a object by name
   */
  public static Object createJobInstance(String name, boolean initialize, ClassLoader loader)
      throws ClassNotFoundException,InstantiationException, IllegalAccessException {

    Class cls = Class.forName(name, initialize, loader);

    return cls.newInstance();
  }
  
	/**
	 * 判断字符是否为空
	 */
	private boolean isNull(String value) {
		return value == null || value.trim().length()==0;
	}
}