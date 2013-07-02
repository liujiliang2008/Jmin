/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.converter.base;

import java.util.Calendar;
import java.util.Date;

import org.jmin.jda.JdaTypeConvertException;
import org.jmin.jda.impl.converter.JdaTypeBaseConverter;

/**
 * Long转换
 * 
 * @author chris
 */

public class LongConverter extends JdaTypeBaseConverter{

	/**
	 * 转换为目标类型
	 */
	public Object convert(Object value)throws JdaTypeConvertException{
		if(value ==null){
			return null;
		}else if(value instanceof String){
		 return Long.valueOf((String)value);
		}else if(value instanceof Character){
			return new Long((char)((Character)value).charValue());
		}else if(value instanceof Number){
			return new Long(((Number)value).longValue());
		}else if(value instanceof Date){
			return new Long(new Long(((Date)value).getTime()).longValue());
		}else if(value instanceof Calendar){
			return new Long(new Long(((Calendar)value).getTime().getTime()).longValue());
		}else{
			throw new JdaTypeConvertException("Doesn't support object conversion from type: "+ value.getClass().getName() + " to type: Long.class");
		}
	}
}
