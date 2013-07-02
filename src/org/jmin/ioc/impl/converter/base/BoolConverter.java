/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.impl.converter.base;

import org.jmin.ioc.BeanTypeConvertException;
import  org.jmin.ioc.impl.converter.BeanTypeBaseConverter;

/**
 * 布尔类型转换
 * 
 * @author chris
 */
public class BoolConverter extends BeanTypeBaseConverter{
	
	/**
	 * 转换为目标类型
	 */
	public Object convert(Object value)throws BeanTypeConvertException{
		if(value ==null){
			return null;
		}else if(value instanceof Boolean){
			 return value;
		}else if(value instanceof String){
			String strValue =((String)value).trim();
			if(strValue.equalsIgnoreCase("Y") || strValue.equalsIgnoreCase("TRUE")){
			 return Boolean.TRUE;
			}else{
			 	try {
					long testValue = Long.parseLong(strValue);
					return (testValue > 0) ? Boolean.TRUE : Boolean.FALSE;
				} catch (NumberFormatException e) {
				}
				return Boolean.FALSE;
			}
		}else if(value instanceof Character){
			Character charValue =(Character)value;
			if(charValue.toString().equalsIgnoreCase("Y")){
				return Boolean.TRUE;
			}else{
				try {
					char testValue = charValue.charValue();
					return (testValue > 0) ? Boolean.TRUE : Boolean.FALSE;
				} catch (NumberFormatException e) {
				}
				return Boolean.FALSE;
			}
		}else if(value instanceof Number){
			Number numValue =(Number)value;
			return (numValue.intValue()>0)?Boolean.TRUE:Boolean.FALSE;
		}else{
			throw new BeanTypeConvertException("Doesn't support object conversion from type: "+ value.getClass().getName() + " to type: Boolean.class");
		}
	}
}
