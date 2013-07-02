/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda;


/**
 * 类型转换器
 * 
 * Chris Liao 
 */

public interface JdaTypeConverter {

	/**
	 * 转换为目标类型
	 */
	public Object convert(Object value)throws JdaTypeConvertException;
}
