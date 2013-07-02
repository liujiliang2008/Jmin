/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.impl.converter.base;

import java.util.Calendar;
import java.util.Date;

import org.jmin.ioc.BeanTypeConvertException;
import org.jmin.ioc.impl.converter.BeanTypeBaseConverter;

/**
 * Char转换器
 * 
 * @author Chris Liao
 */
public class CharConverter extends BeanTypeBaseConverter{
	
	/**
	 * 转换为目标类型
	 */
	public Object convert(Object value)throws BeanTypeConvertException{
		if(value ==null){
			return null;
		}else if(value instanceof Character){
			return (Character)value;
		}else if(value instanceof Boolean){
			Boolean bool =(Boolean)value;
	    return bool.booleanValue()?"Y":"N";
		}else if(value instanceof String){
		  String tempStr =(String)value;
			return (tempStr.length()>=1)?new Character(tempStr.charAt(0)):null;
		}else if(value instanceof Number){
			return new Character((char)((Number)value).intValue());
		}else if(value instanceof Date){
			return new Character((char)((Date)value).getTime());
		}else if(value instanceof Calendar){
			return new Character((char)((Calendar)value).getTime().getTime());
		}else{
			throw new BeanTypeConvertException("Doesn't support object conversion from type: "+ value.getClass().getName() + " to type: Character.class");
		}
	}
}