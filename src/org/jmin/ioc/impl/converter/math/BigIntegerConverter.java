/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.impl.converter.math;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;

import org.jmin.ioc.BeanTypeConvertException;
import org.jmin.ioc.impl.converter.BeanTypeBaseConverter;

/**
 * 大型整形类字
 * 
 * @author Chris Liao
 */
public class BigIntegerConverter extends BeanTypeBaseConverter {
	
	/**
	 * 转换为目标类型
	 */
	public Object convert(Object value)throws BeanTypeConvertException{
		if(value ==null){
			return null;
		}else if(value instanceof Number){
			return new BigInteger(""+((Number)value).longValue());
		}else if(value instanceof String){
			return new BigInteger((String)value);
		}else if(value instanceof Character){
			return new BigInteger(""+((Character)value).charValue());
		}else if(value instanceof Date){
			return new BigInteger(""+((Date)value).getTime());
		}else if(value instanceof Calendar){
			return new BigInteger(""+((Calendar)value).getTime().getTime());
		}else{
			throw new BeanTypeConvertException("Doesn't support object conversion from type: "+ value.getClass().getName() + " to type: String");
		}	
	}
 }