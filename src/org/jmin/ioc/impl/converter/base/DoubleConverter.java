/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.impl.converter.base;

import java.util.Calendar;
import java.util.Date;

import org.jmin.ioc.BeanTypeConvertException;
import org.jmin.ioc.impl.converter.BeanTypeBaseConverter;


/**
 * double转换
 * 
 * @author chris
 */

public class DoubleConverter extends BeanTypeBaseConverter{

	/**
	 * 转换为目标类型
	 */
	public Object convert(Object value)throws BeanTypeConvertException{
		if(value ==null){
			return null;
		}else if(value instanceof String){
		 return Double.valueOf((String)value);
		}else if(value instanceof Character){
			return new Double((char)((Character)value).charValue());
		}else if(value instanceof Number){
			return new Double(((Number)value).doubleValue());
		}else if(value instanceof Date){
			return new Double(new Long(((Date)value).getTime()).doubleValue());
		}else if(value instanceof Calendar){
			return new Double(new Long(((Calendar)value).getTime().getTime()).doubleValue());
		}else{
			throw new BeanTypeConvertException("Doesn't support object conversion from type: "+ value.getClass().getName() + " to type: Double.class");
		}
	}
}
