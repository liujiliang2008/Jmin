/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.impl.converter.date;

import java.sql.Date;
import java.util.Calendar;

import org.jmin.ioc.BeanTypeConvertException;
import org.jmin.ioc.impl.converter.BeanTypeBaseConverter;

/**
 * 日历转换
 * 
 * @author chris
 */

public class DateConverter extends BeanTypeBaseConverter{

	/**
	 * 转换为目标类型
	 */
	public Object convert(Object value)throws BeanTypeConvertException{
		if(value ==null){
			return null;
		}else if(value instanceof Date){
			return value;
		}else if(value instanceof java.util.Date){
			return new Date(((java.util.Date)value).getTime());
		}else if(value instanceof Number){
			Number numValue =(Number)value;
			return new Date(numValue.longValue());
		}else if(value instanceof Calendar){
			Calendar calendar =(Calendar)value;
			return new Date(calendar.getTime().getTime());
		}else if(value instanceof String){
			try{
				return new Date(Long.parseLong((String)value));
			}catch(Throwable e){
				return new Date((UtilDateConverter.stringToDate((String)value)).getTime());
			}
		}else{
			throw new BeanTypeConvertException("Doesn't support object conversion from type: "+ value.getClass().getName() + " to type: java.sql.Date");
		}
	}
}
