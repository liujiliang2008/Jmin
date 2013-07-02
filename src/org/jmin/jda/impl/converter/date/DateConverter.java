/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.converter.date;

import java.sql.Date;
import java.util.Calendar;

import org.jmin.jda.JdaTypeConvertException;
import org.jmin.jda.impl.converter.JdaTypeBaseConverter;

/**
 * 日历转换
 * 
 * @author chris
 */

public class DateConverter extends JdaTypeBaseConverter{

	/**
	 * 转换为目标类型
	 */
	public Object convert(Object value)throws JdaTypeConvertException{
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
			throw new JdaTypeConvertException("Doesn't support object conversion from type: "+ value.getClass().getName() + " to type: java.sql.Date");
		}
	}
}
