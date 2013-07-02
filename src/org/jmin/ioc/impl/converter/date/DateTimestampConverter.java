/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.impl.converter.date;

import java.sql.Timestamp;
import java.util.Calendar;

import org.jmin.ioc.BeanTypeConvertException;
import org.jmin.ioc.impl.converter.BeanTypeBaseConverter;

/**
 * 日历转换
 * 
 * @author chris
 */

public class DateTimestampConverter extends BeanTypeBaseConverter{
	
	/**
	 * 转换为目标类型
	 */
	public Object convert(Object value)throws BeanTypeConvertException{
		if(value ==null){
			return null;
		}else if(value instanceof Timestamp){
			return value;
		}else if(value instanceof java.util.Date){
			return new Timestamp(((java.util.Date)value).getTime());
		}else if(value instanceof Number){
			Number numValue =(Number)value;
			return new Timestamp(numValue.longValue());
		}else if(value instanceof Calendar){
			Calendar calendar =(Calendar)value;
			return new Timestamp(calendar.getTime().getTime());
		}else if(value instanceof String){
			try{
				return new Timestamp(Long.parseLong((String)value));
			}catch(Throwable e){
				return new Timestamp((UtilDateConverter.stringToDate((String)value)).getTime());
			}
		}else{
			throw new BeanTypeConvertException("Doesn't support object conversion from type: "+ value.getClass().getName() + " to type: java.sql.Timestamp");
		}
	}
}