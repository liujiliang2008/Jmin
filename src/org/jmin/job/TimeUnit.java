/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.job;

import java.util.Calendar;

/**
 * 时间单元
 *
 * @author Chris Liao
 * @version 1.0
 */

public class TimeUnit {

  /**
   * 时间单元Code
   */
  private int unitCode;

  /**
   * 单元描述
   */
  private String unitName;

  /**
   * 构造函数
   */
  TimeUnit(int unitCode, String unitName) {
    this.unitCode = unitCode;
    this.unitName = unitName;
  }

  /**
   * 时间单元Code
   */
  public int getUnitCode() {
    return unitCode;
  }

  /**
   * 单元描述
   */
  public String getUnitName() {
    return unitName;
  }

  public static boolean isValidUnit(int unit) {
    boolean isPassed = false;
    switch (unit) {
      case Calendar.MILLISECOND:
      case Calendar.SECOND:
      case Calendar.MINUTE:
      case Calendar.HOUR:
      case Calendar.DAY_OF_MONTH:
      case Calendar.WEEK_OF_MONTH:
      case Calendar.MONTH:
      case TimeCalendar.QUARTER:
      case TimeCalendar.HALF_YEAR:
      case Calendar.YEAR:

      case Calendar.HOUR_OF_DAY:
      case Calendar.DAY_OF_WEEK:
      case Calendar.DAY_OF_WEEK_IN_MONTH:
      case Calendar.DAY_OF_YEAR:
      case Calendar.WEEK_OF_YEAR:
        isPassed = true;
        break;
      default:
        isPassed = false;
        break;
    }
    return isPassed;
  }

  public static TimeUnit toUnit(String unitName) {
    TimeUnit timeUnit = null;
    TimeUnit[] units = getAll();
    for (int i = 0; i < units.length; i++) {
      if (units[i].getUnitName().equalsIgnoreCase(unitName)) {
        timeUnit = units[i];
        break;
      }
    }
    return timeUnit;
  }

  public static TimeUnit toUnit(int unit) {
    TimeUnit timeUnit = null;
    switch (unit) {
      case Calendar.MILLISECOND:
        timeUnit = MILLISECOND;
        break;
      case Calendar.SECOND:
        timeUnit = SECOND;
        break;
      case Calendar.MINUTE:
        timeUnit = MINUTE;
        break;
      case Calendar.HOUR:
        timeUnit = HOUR;
        break;
      case Calendar.DATE:
        timeUnit = DAY;
        break;
      case Calendar.WEEK_OF_MONTH:
        timeUnit = WEEK;
        break;
      case Calendar.MONTH:
        timeUnit = MONTH;
        break;
      case TimeCalendar.QUARTER:
        timeUnit = YEAR;
        break;
      case TimeCalendar.HALF_YEAR:
        timeUnit = HALF_YEAR;
        break;
      case Calendar.YEAR:
        timeUnit = YEAR;
        break;
//      case Calendar.HOUR:
//        timeUnit = HOUR;
//        break;
//      case Calendar.DAY_OF_WEEK:
//        timeUnit = DAY_OF_WEEK;
//        break;
//      case Calendar.DAY_OF_WEEK_IN_MONTH:
//        timeUnit = DAY_OF_WEEK_IN_MONTH;
//        break;
//      case Calendar.DAY_OF_YEAR:
//        timeUnit = DAY_OF_YEAR;
//        break;
//      case Calendar.WEEK_OF_YEAR:
//        timeUnit = WEEK_OF_YEAR;
//        break;

      default:
        break;
    }
    return timeUnit;
  }

  public static final TimeUnit MILLISECOND = new TimeUnit(Calendar.MILLISECOND,"Millisecond");
  public static final TimeUnit SECOND = new TimeUnit(Calendar.SECOND, "Second");
  public static final TimeUnit MINUTE = new TimeUnit(Calendar.MINUTE, "Minute");
  public static final TimeUnit HOUR = new TimeUnit(Calendar.HOUR, "Hour");

  public static final TimeUnit WEEK = new TimeUnit(Calendar.WEEK_OF_MONTH, "Week");
  public static final TimeUnit DAY = new TimeUnit(Calendar.DATE, "Day");
  public static final TimeUnit MONTH = new TimeUnit(Calendar.MONTH, "Month");

  public static final TimeUnit QUARTER = new TimeUnit(TimeCalendar.QUARTER, "Quarter");
  public static final TimeUnit HALF_YEAR = new TimeUnit(TimeCalendar.HALF_YEAR, "Half_Year");
  public static final TimeUnit YEAR = new TimeUnit(Calendar.YEAR, "Year");

//  public static final TimeUnit HOUR_OF_DAY = new TimeUnit(Calendar.HOUR,"Hour_Of_Day");
//  public static final TimeUnit DAY_OF_WEEK = new TimeUnit(Calendar.DAY_OF_WEEK,"Day_Of_Week");
//  public static final TimeUnit DAY_OF_WEEK_IN_MONTH = new TimeUnit(Calendar.DAY_OF_WEEK_IN_MONTH, "DAY_OF_WEEK_IN_MONTH");
//  public static final TimeUnit DAY_OF_YEAR = new TimeUnit(Calendar.DAY_OF_YEAR,"Day_Of_Year");
//  public static final TimeUnit WEEK_OF_YEAR = new TimeUnit(Calendar.WEEK_OF_YEAR, "Week_Of_Year");


  private static TimeUnit[] all = new TimeUnit[] {
      MILLISECOND,
      SECOND,
      MINUTE,
      HOUR,
      DAY,
      WEEK,
      MONTH,
      QUARTER,
      HALF_YEAR,
      YEAR

//      HOUR_OF_DAY,
//      DAY_OF_WEEK,
//      DAY_OF_WEEK_IN_MONTH,
//      DAY_OF_YEAR,
//      WEEK_OF_YEAR
  };

  public static TimeUnit[] getAll() {
    return all;
  }

  /**
   * Hash code
   */
  public int hashCode() {
    return unitCode;
  }

  /**
   * equals override
   */
  public boolean equals(Object obj) {
    if (obj instanceof TimeUnit) {
      TimeUnit other = (TimeUnit) obj;
      return this.unitCode == other.unitCode;
    }
    else {
      return false;
    }
  }

}