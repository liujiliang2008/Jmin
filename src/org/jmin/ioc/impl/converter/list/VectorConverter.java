/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.impl.converter.list;

import java.util.Collection;
import java.util.Vector;

import org.jmin.ioc.BeanTypeConvertException;

/**
 * Vector转换器
 * 
 * @author Liao
 */
public class VectorConverter extends AbstractConverter{
	
	/**
	* 将对象转换为目标类型，如将结果转换为当前类型
	*/
	public Object convert(Object value)throws BeanTypeConvertException{
		if(value==null){
			return null;
	  }else if(value instanceof Vector){
			return value;
		}else{
			Collection list = new Vector();
		  this.convert(list,value);
		  return list;
		}
	}
}