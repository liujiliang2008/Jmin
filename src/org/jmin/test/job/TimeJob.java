/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.test.job;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.jmin.job.Job;
/**
 * 时钟作业测试
 *
 * @author Chris Liao
 */

public class TimeJob implements Job{

  /**
   * 名字
   */
  private String name;

  /**
   * date formater
   */
  private SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  /**
   * 构造
   */
  public TimeJob(){
    this("");
  }

  /**
   * 构造
   */
  public TimeJob(String name){
    setName(name);
  }

  /**
   * 名字
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * 名字
   */
  public String getName() {
    return name;
  }

  /**
   * 作业方法
   */
  public void run() {
   System.out.println("Job["+ this.name +"]get Current Time: " +formater.format(new Date()));
  }
  
  /**
   * 重写方法
   */
  public String toString(){
    return this.name;
  }
 
}