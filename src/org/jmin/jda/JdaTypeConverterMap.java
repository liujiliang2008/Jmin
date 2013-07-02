/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda;

/**
 * 类型转换器列表
 * 
 * Chris Liao 
 */

public interface JdaTypeConverterMap {
	
	/**
	 * 是否包含转换器
	 */
	public boolean supportsType(Class type);
	
	/**
	 * 获取类的转换器
	 */
	public JdaTypeConverter getTypeConverter(Class type);
	
}
