/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.impl.converter.base;

import java.util.Calendar;
import java.util.Date;

import org.jmin.ioc.BeanTypeConvertException;
import org.jmin.ioc.impl.converter.BeanTypeBaseConverter;

/**
 * Integer转换
 * 
 * @author chris
 */

public class IntegerConverter extends BeanTypeBaseConverter{
	
	/**
	 * 转换为目标类型
	 */
	public Object convert(Object value)throws BeanTypeConvertException{
		if(value ==null){
			return null;
		}else if(value instanceof String){
		 return Integer.valueOf((String)value);
		}else if(value instanceof Character){
			return new Integer((int)((Character)value).charValue());
		}else if(value instanceof Number){
			return new Integer(((Number)value).intValue());
		}else if(value instanceof Date){
			return new Integer(new Long(((Date)value).getTime()).intValue());
		}else if(value instanceof Calendar){
			return new Integer(new Long(((Calendar)value).getTime().getTime()).intValue());
		}else{
			throw new BeanTypeConvertException("Doesn't support object conversion from type: "+ value.getClass().getName() + " to type: Integer.class");
		}
	}
}
