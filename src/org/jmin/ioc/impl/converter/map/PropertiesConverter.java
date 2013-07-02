/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.impl.converter.map;

import java.util.Map;
import java.util.Properties;

import org.jmin.ioc.BeanTypeConvertException;
import org.jmin.ioc.impl.converter.BeanTypeBaseConverter;

/**
 * Properties转换器
 * 
 * @author Liao
 */
public class PropertiesConverter extends BeanTypeBaseConverter{
	
	/**
	* 将对象转换为目标类型，如将结果转换为当前类型
	*/
	public Object convert(Object value)throws BeanTypeConvertException{
		if(value==null){
			return null;
	  }else if(value instanceof Properties){
			return value;
		}else if(value instanceof Map){
			Map oldMap = (Map)value;
			Map newMap = new Properties();
			newMap.putAll(oldMap);
		  return newMap;
		}else{
			throw new BeanTypeConvertException("Doesn't support object conversion from type: "+ value.getClass().getName() + " to type:"+Properties.class.getName());
		}
	}
}
