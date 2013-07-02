/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.converter;
import org.jmin.jda.JdaTypeConvertException;
import org.jmin.jda.JdaTypeConverter;

/**
 * 对象持久器
 * 
 * @author Chris
 */

public class JdaTypeBaseConverter implements JdaTypeConverter {
	
	/**
	* 将对象转换为目标类型，如将结果转换为当前类型
	*/
	public Object convert(Object value)throws JdaTypeConvertException{
		return value;
	}
}
