/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.impl.converter.list;

import java.util.Collection;
import java.util.HashSet;

import org.jmin.ioc.BeanTypeConvertException;

/**
 * HashSet转换器
 * 
 * @author Liao
 */
public class HashSetConverter extends AbstractConverter{
	
	/**
	* 将对象转换为目标类型，如将结果转换为当前类型
	*/
	public Object convert(Object value)throws BeanTypeConvertException{
		if(value==null){
			return null;
	  }else if(value instanceof HashSet){
			return value;
		}else{
		  Collection set = new HashSet();
		  this.convert(set,value);
		  return set;
		}
	}
}