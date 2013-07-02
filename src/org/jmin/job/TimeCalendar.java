/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.job;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 日历类
 *
 * @author Chris Liao
 * @version 1.0
 */

public class TimeCalendar extends GregorianCalendar{
  
  /**
   * 季度
   */
  public final static int QUARTER = 50;
  
  /**
   * 半年
   */
  public final static int HALF_YEAR = 51;
  
  /**
   * 是否需要支持月末
   */
  private boolean supportMonthLastDay;
  
	/**
   *静态构造方法
   */
  public static synchronized TimeCalendar getTimeCalendar(){
    return new TimeCalendar();
  }

  /**
   * 静态构造方法
   */
  public static synchronized TimeCalendar getTimeCalendar(TimeZone zone){
    return new TimeCalendar(zone, Locale.getDefault());
  }

  /**
   * 静态构造方法
   */
  public static synchronized TimeCalendar getTimeCalendar(Locale aLocale){
    return new TimeCalendar(TimeZone.getDefault(), aLocale);
  }

  /**
   * 静态构造方法
   */
  public static synchronized TimeCalendar getTimeCalendar(TimeZone zone,Locale aLocale){
    return new TimeCalendar(zone, aLocale);
  }

  /**
   * 构造方法
   */
  public TimeCalendar() {
    super();
  }

  /**
   * 构造方法
   */
  public TimeCalendar(TimeZone zone) {
    this(zone, Locale.getDefault());
  }

  /**
   * 构造方法
   */
  public TimeCalendar(Locale aLocale) {
    this(TimeZone.getDefault(), aLocale);
  }

  /**
   * 构造方法
   */
  public TimeCalendar(TimeZone zone, Locale aLocale) {
    super(zone, aLocale);
    setTimeInMillis(System.currentTimeMillis());
  }

  /**
   * 构造方法
   */
  public TimeCalendar(int year, int month, int date) {
    super(TimeZone.getDefault(), Locale.getDefault());
    this.set(ERA, AD);
    this.set(YEAR, year);
    this.set(MONTH, month);
    this.set(DATE, date);
  }

  /**
   * 构造方法
   */
  public TimeCalendar(int year, int month, int date, int hour,int minute) {
    super(TimeZone.getDefault(), Locale.getDefault());
    this.set(ERA, AD);
    this.set(YEAR, year);
    this.set(MONTH, month);
    this.set(DATE, date);
    this.set(HOUR_OF_DAY, hour);
    this.set(MINUTE, minute);
  }

  /**
   * 构造方法
   */
  public TimeCalendar(int year, int month, int date, int hour,int minute, int second) {
    super(TimeZone.getDefault(), Locale.getDefault());
    this.set(ERA, AD);
    this.set(YEAR, year);
    this.set(MONTH, month);
    this.set(DATE, date);
    this.set(HOUR_OF_DAY, hour);
    this.set(MINUTE, minute);
    this.set(SECOND, second);
  }

  /**
   * 获取毫秒时间
   */
  public long getTimeInMillis() {
    return super.getTimeInMillis();
  }

  /**
   * 设置毫秒时间
   */
  public void setTimeInMillis(long millis) {
    super.setTimeInMillis(millis);
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
   * 是否为周六
   */
   public boolean isSaturday() {
   	return (this.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY);
   }
   
  /**
   * 是否为周日
   */
   public boolean isSunday() {
   	return (this.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY);
   }
	
  /**
   * 判断日期是否为月最开始一天
   */
   public boolean isMonthFirstDay(Date date) {
     return this.get(TimeCalendar.DAY_OF_MONTH) == 1;
   }
  
  /**
   * 判断给定日期是否为月末的一天
   */
   public boolean isMonthLastDay() {
  	 return (this.get(TimeCalendar.DAY_OF_MONTH) == this.getActualMaximum(TimeCalendar.DAY_OF_MONTH));
   }
   
    /**
    * 计算累加
    */
   public void add(int field,int amount) {
    switch(field){
      case TimeCalendar.QUARTER:
        super.add(Calendar.MONTH, amount*3);
        break;
      case TimeCalendar.HALF_YEAR:
        super.add(Calendar.MONTH, amount*6);
        break;
      case TimeCalendar.MONTH:
      	 if(this.isSupportMonthLastDay() && this.isMonthLastDay()){
      		 super.add(Calendar.MONTH,amount);
        	this.set(TimeCalendar.DAY_OF_MONTH,this.getActualMaximum(TimeCalendar.DAY_OF_MONTH));
         }else{
        	 super.add(Calendar.MONTH,amount);
         }
        break;
      default: 
        super.add(field,amount);
        break;
    }
  }
}