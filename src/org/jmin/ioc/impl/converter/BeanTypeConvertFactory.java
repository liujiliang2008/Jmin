/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.impl.converter;

import java.util.HashMap;
import java.util.Map;

import org.jmin.ioc.BeanException;
import org.jmin.ioc.BeanTypeConvertException;
import org.jmin.ioc.BeanTypeConverter;

/**
 * 对象转换工厂
 * 
 * @author Chris
 */

public class BeanTypeConvertFactory {
	
	/**
	* store converter Map
	*/
	private Map converterMap;
	
	/**
	 * 构造函数
	 */
	public BeanTypeConvertFactory(){
		this.converterMap= new HashMap();
	}
	
	/**
	 * check contains converter by type
	 */
	public boolean containsTypeConverter(Class type)throws BeanException{
		return this.converterMap.containsKey(type);
	}
	 
	/**
	 * remove type converter
	 */
	public void removeTypeConverter(Class type)throws BeanException{
		this.converterMap.remove(type);
	}
	
	/**
	 * get type converter
	 */
	public BeanTypeConverter getTypeConverter(Class type)throws BeanException{
	  return(BeanTypeConverter)this.converterMap.get(type);
	}
	
	/**
	 * add type converter
	 */
	public void addTypeConverter(Class type,BeanTypeConverter converter)throws BeanException{
		this.converterMap.put(type,converter);
	}
	
	/**
	 * 转换对象
	 */
	public Object convert(Object ob,Class type)throws BeanException{
		BeanTypeConverter converter = (BeanTypeConverter)converterMap.get(type);
	  if(converter==null)
		 throw new BeanTypeConvertException("Not found matched type converter for class["+type.getName()+"]");
	  else
	   return converter.convert(ob);
	}
}
