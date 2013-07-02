/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.converter.base;

import java.sql.Clob;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import org.jmin.jda.JdaTypeConvertException;
import org.jmin.jda.impl.converter.JdaTypeBaseConverter;

/**
 * 转换为字符串类型
 * 
 * @author chris
 */
public class StringConverter extends JdaTypeBaseConverter{

	/**
	 * 转换为目标类型
	 */
	public Object convert(Object value)throws JdaTypeConvertException{
		if(value ==null){
			return null;
		}else if(value instanceof String){
		 return value;
		}else if(value instanceof Character){
			return ((Character)value).toString();
		}else if(value instanceof Number){
			return ((Number)value).toString();
		}else if(value instanceof Date){
			return ((Date)value).toString();
		}else if(value instanceof Calendar){
			return ((Calendar)value).toString();
		}else if(value instanceof Boolean || Boolean.TYPE.isInstance(value)){
			return value.toString();
		}else if(value instanceof Clob){
			try {
				Clob clob =(Clob)value;
				int len = (int)	clob.length();
				if(len < 1)
					return null;
				else
				  return clob.getSubString(1,len);
			} catch (SQLException e) {
				throw new JdaTypeConvertException("Fail to get text from clob:"+e.getMessage(),e);
			}
		}else if(value instanceof Object){
			return value.toString();
		
		}else{
			throw new JdaTypeConvertException("Doesn't support object conversion from type: "+ value.getClass().getName() + " to type: String");
		}
	}
}
