/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.impl.converter.list;

import java.util.Collection;
import java.util.TreeSet;

import org.jmin.ioc.BeanTypeConvertException;

/**
 * TreeSet转换器
 * 
 * @author Liao
 */
public class TreeSetConverter extends AbstractConverter{
	
	/**
	* 将对象转换为目标类型，如将结果转换为当前类型
	*/
	public Object convert(Object value)throws BeanTypeConvertException{
		if(value==null){
			return null;
	  }else if(value instanceof TreeSet){
			return value;
		}else{
		  Collection set = new TreeSet();
		  this.convert(set,value);
		  return set;
		}
	}
}