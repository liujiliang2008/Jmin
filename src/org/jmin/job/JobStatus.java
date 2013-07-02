/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.job;

/**
 * 作业状态的定义类
 *
 * @author Chris Liao
 * @version 1.0
 */

public final class JobStatus {

   /**
    * 作业刚新建,还未进入排期状态(WAITING)
    */
   static final int VIRGIN = 0;
   
   /**
    * 作业正在运行
    */
   static final int RUNNING = 1;
   
   /**
    * 作业正在等待运行
    */
   static final int WAITING = 2;
   
   /**
    * 作业处于挂起状态,通过调用JobSchedule.suspend,处于队列的作业时间依然累计
    */
   static final int SUSPENDED = 3;
   
   /**
    * 表示作业处于死亡状态,将不再运行,无法确定是正常结束,还是被取消
    */
   static final int DEAD = 4;

   /**
    * Return description for status flag
    */
   public static String statusToString(int status){
      String desc = "";
      switch(status){
         case VIRGIN:    desc ="Created";   break;
         case WAITING:   desc ="Waiting";   break;
         case RUNNING:   desc ="Running";   break;
         case SUSPENDED: desc ="Suspended"; break;
         case DEAD: 		 desc ="Dead"; break;
         default:break;
      }

      return desc;
   }
}