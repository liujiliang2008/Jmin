/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.converter;

import java.util.HashMap;
import java.util.Map;

import org.jmin.jda.JdaTypeConverter;
import org.jmin.jda.JdaTypeConverterMap;

/**
 * 类型转换器列表
 * 
 * Chris Liao 
 */

public class JdaTypeConverterMapImpl implements JdaTypeConverterMap{
	
	/**
	 * 列表
	 */
	private Map map = new HashMap();
	
	/**
	 * 是否包含转换器
	 */
	public boolean supportsType(Class type){
	  return 	this.map.containsKey(type);
	}
	
	/**
	 * 删除转换器
	 */
	public void removeTypeConverter(Class type){
		this.map.remove(type);
	}
	
	/**
	 * 放置类的转换器
	 */
	public void putTypeConverter(Class type,JdaTypeConverter converter){
		this.map.put(type,converter);
	}
	
	/**
	 * 获取类的转换器
	 */
	public JdaTypeConverter getTypeConverter(Class type){
		 return (JdaTypeConverter)this.map.get(type);
	}
}
