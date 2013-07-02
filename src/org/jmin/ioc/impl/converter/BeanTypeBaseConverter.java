/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.impl.converter;
import org.jmin.ioc.BeanTypeConvertException;
import org.jmin.ioc.BeanTypeConverter;

/**
 * 对象持久器
 * 
 * @author Chris
 */

public class BeanTypeBaseConverter implements BeanTypeConverter {
	
	/**
	* 将对象转换为目标类型，如将结果转换为当前类型
	*/
	public Object convert(Object value)throws BeanTypeConvertException{
		return value;
	}
}
