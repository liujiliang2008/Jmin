/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.impl.converter.map;

import java.util.Hashtable;
import java.util.Map;

import org.jmin.ioc.BeanTypeConvertException;
import org.jmin.ioc.impl.converter.BeanTypeBaseConverter;

/**
 * Hashtable转换器
 * 
 * @author Liao
 */
public class HashtableConverter extends BeanTypeBaseConverter{
	
	/**
	* 将对象转换为目标类型，如将结果转换为当前类型
	*/
	public Object convert(Object value)throws BeanTypeConvertException{
		if(value==null){
			return null;
	  }else if(value instanceof Hashtable){
			return value;
		}else if(value instanceof Map){
			Map oldMap = (Map)value;
			Map newMap = new Hashtable();
			newMap.putAll(oldMap);
		  return newMap;
		}else{
			throw new BeanTypeConvertException("Doesn't support object conversion from type: "+ value.getClass().getName() + " to type:"+Hashtable.class.getName());
		}
	}
}
