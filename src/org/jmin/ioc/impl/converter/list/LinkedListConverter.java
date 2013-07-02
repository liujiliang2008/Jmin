/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.impl.converter.list;

import java.util.Collection;
import java.util.LinkedList;

import org.jmin.ioc.BeanTypeConvertException;

/**
 * LinkedList转换器
 * 
 * @author Liao
 */
public class LinkedListConverter extends AbstractConverter{
	
	/**
	* 将对象转换为目标类型，如将结果转换为当前类型
	*/
	public Object convert(Object value)throws BeanTypeConvertException{
		if(value==null){
			return null;
	  }else if(value instanceof LinkedList){
			return value;
		}else{
			Collection list = new LinkedList();
		  this.convert(list,value);
		  return list;
		}
	}
}
