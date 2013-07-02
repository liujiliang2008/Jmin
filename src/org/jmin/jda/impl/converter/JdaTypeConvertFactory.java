/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.converter;

import org.jmin.jda.JdaTypeConvertException;
import org.jmin.jda.JdaTypeConverter;
import org.jmin.jda.JdaTypeConverterMap;

/**
 * 对象持久器
 * 
 * @author Chris
 */

public class JdaTypeConvertFactory {
  
	/**
	 * 转换对象
	 */
	public static Object convert(Object ob,Class type,JdaTypeConverterMap map)throws JdaTypeConvertException{
		JdaTypeConverter converter = map.getTypeConverter(type);
	  if(converter==null)
		 throw new JdaTypeConvertException("Not found matched type converter for class["+type.getName()+"]");
	  else
	   return converter.convert(ob);
	}
}
