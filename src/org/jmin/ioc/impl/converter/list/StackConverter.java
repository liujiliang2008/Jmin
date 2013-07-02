/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.impl.converter.list;

import java.util.Collection;
import java.util.Stack;

import org.jmin.ioc.BeanTypeConvertException;

/**
 * Stack转换器
 * 
 * @author Liao
 */
public class StackConverter extends AbstractConverter{
	
	/**
	* 将对象转换为目标类型，如将结果转换为当前类型
	*/
	public Object convert(Object value)throws BeanTypeConvertException{
		if(value==null){
			return null;
	  }else if(value instanceof Stack){
			return value;
		}else{
			Collection list = new Stack();
		  this.convert(list,value);
		  return list;
		}
	}
}

