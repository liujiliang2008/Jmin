/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.impl.converter.base;

import java.util.Calendar;
import java.util.Date;

import org.jmin.ioc.BeanTypeConvertException;
import  org.jmin.ioc.impl.converter.BeanTypeBaseConverter;

/**
 * Byte转换器
 * 
 * @author Chris Liao
 */
public class ByteConverter extends BeanTypeBaseConverter{

	/**
	 * 转换为目标类型
	 */
	public Object convert(Object value)throws BeanTypeConvertException{
		if(value ==null){
			return null;
		}else if(value instanceof String){
			return new Byte(((String)value).getBytes()[0]);
		}else if(value instanceof Character){
			return new Byte((byte)((Character)value).charValue());
		}else if(value instanceof Number){
			return new Byte(((Number)value).byteValue());
		}else if(value instanceof Date){
			return new Byte(new Long(((Date)value).getTime()).byteValue());
		}else if(value instanceof Calendar){
			return new Byte(new Long(((Calendar)value).getTime().getTime()).byteValue());
		}else{
			throw new BeanTypeConvertException("Doesn't support object conversion from type: "+ value.getClass().getName() + " to type: Byte.class");
		}
	}
}
