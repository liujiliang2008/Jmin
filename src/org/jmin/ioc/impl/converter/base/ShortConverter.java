/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.impl.converter.base;

import java.util.Calendar;
import java.util.Date;

import org.jmin.ioc.BeanTypeConvertException;
import org.jmin.ioc.impl.converter.BeanTypeBaseConverter;

/**
 * Short转换
 * 
 * @author chris
 */

public class ShortConverter extends BeanTypeBaseConverter{
	
	/**
	 * 转换为目标类型
	 */
	public Object convert(Object value)throws BeanTypeConvertException{
		if(value ==null){
			return null;
		}else if(value instanceof String){
		 return Short.valueOf((String)value);
		}else if(value instanceof Character){
			return new Short((short)((Character)value).charValue());
		}else if(value instanceof Number){
			return new Short(((Number)value).shortValue());
		}else if(value instanceof Date){
			return new Short(new Long(((Date)value).getTime()).shortValue());
		}else if(value instanceof Calendar){
			return new Short(new Long(((Calendar)value).getTime().getTime()).shortValue());
		}else{
			throw new BeanTypeConvertException("Doesn't support object conversion from type: "+ value.getClass().getName() + " to type: Short.class");
		}
	}
}
