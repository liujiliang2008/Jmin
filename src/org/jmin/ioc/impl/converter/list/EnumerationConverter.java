/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.impl.converter.list;

import java.util.Enumeration;
import java.util.Vector;

import org.jmin.ioc.BeanTypeConvertException;

/**
 * Enumeration转换器
 * 
 * @author Liao
 */
public class EnumerationConverter extends AbstractConverter{
	
	/**
	* 将对象转换为目标类型，如将结果转换为当前类型
	*/
	public Object convert(Object value)throws BeanTypeConvertException{
		if(value==null){
			return null;
	  }else if(value instanceof Enumeration){
			return value;
		}else{
			Vector vector = new Vector();
		  this.convert(vector,value);
		  return vector.elements();
		}
	}
}
