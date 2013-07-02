/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.converter.math;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;

import org.jmin.jda.JdaTypeConvertException;
import org.jmin.jda.impl.converter.JdaTypeBaseConverter;

/**
 * 大型整形类字
 * 
 * @author Chris Liao
 */
public class BigIntegerConverter extends JdaTypeBaseConverter {
	
	/**
	 * 转换为目标类型
	 */
	public Object convert(Object value)throws JdaTypeConvertException{
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
			throw new JdaTypeConvertException("Doesn't support object conversion from type: "+ value.getClass().getName() + " to type: String");
		}	
	}
 }