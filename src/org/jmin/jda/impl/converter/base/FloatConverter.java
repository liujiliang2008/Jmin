/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.converter.base;

import java.util.Calendar;
import java.util.Date;

import org.jmin.jda.JdaTypeConvertException;
import org.jmin.jda.impl.converter.JdaTypeBaseConverter;

/**
 * Float转换
 * 
 * @author chris
 */

public class FloatConverter extends JdaTypeBaseConverter{

	/**
	 * 转换为目标类型
	 */
	public Object convert(Object value)throws JdaTypeConvertException{
		if(value ==null){
			return null;
		}else if(value instanceof String){
		 return Float.valueOf((String)value);
		}else if(value instanceof Character){
			return new Float((char)((Character)value).charValue());
		}else if(value instanceof Number){
			return new Float(((Number)value).floatValue());
		}else if(value instanceof Date){
			return new Float(new Long(((Date)value).getTime()).floatValue());
		}else if(value instanceof Calendar){
			return new Float(new Long(((Calendar)value).getTime().getTime()).floatValue());
		}else{
			throw new JdaTypeConvertException("Doesn't support object conversion from type: "+ value.getClass().getName() + " to type: Float.class");
		}
	}
}
