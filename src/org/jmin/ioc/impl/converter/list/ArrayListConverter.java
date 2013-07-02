/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.impl.converter.list;

import java.util.ArrayList;
import java.util.Collection;

import org.jmin.ioc.BeanTypeConvertException;

/**
 * ArrayList转换器
 * 
 * @author Liao
 */
public class ArrayListConverter extends AbstractConverter{
	
	/**
	* 将对象转换为目标类型，如将结果转换为当前类型
	*/
	public Object convert(Object value)throws BeanTypeConvertException{
		if(value==null){
			return null;
	  }else if(value instanceof ArrayList){
			return value;
		}else{
			Collection list = new ArrayList();
		  this.convert(list,value);
		  return list;
		}
	}
}
