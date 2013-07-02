/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.impl.converter.date;

import java.util.Calendar;
import java.util.Date;

import org.jmin.ioc.BeanTypeConvertException;
import org.jmin.ioc.impl.converter.BeanTypeBaseConverter;

/**
 * 日历转换
 * 
 * @author chris
 */

public class CalendarConverter extends BeanTypeBaseConverter{

	/**
	 * 转换为目标类型
	 */
	public Object convert(Object value)throws BeanTypeConvertException{
		if(value ==null){
			return null;
		}else if(value instanceof Calendar){
			return value;
		}else if(value instanceof Date){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime((Date)value);
			return calendar;
		}else if(value instanceof Number){
			Number numValue =(Number)value;
			long time= numValue.longValue();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date(time));
			return calendar;
		}else if(value instanceof String){
			try{
				long longVale = Long.parseLong((String)value);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(new Date(longVale));
				return calendar;
			}catch(Throwable e){
				Date date = UtilDateConverter.stringToDate((String)value);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				return calendar;
			}
		}else{
			throw new BeanTypeConvertException("Doesn't support object conversion from type: "+ value.getClass().getName() + " to type: Calendar.class");
		}
	}
}
