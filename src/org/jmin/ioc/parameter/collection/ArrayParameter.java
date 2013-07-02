/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.parameter.collection;

import org.jmin.ioc.BeanParameterException;
import org.jmin.ioc.parameter.ContainerParameter;

/**
 * 容器类型的Value
 *
 * @author Chris Liao
 * @version 1.0
 */
public final class ArrayParameter extends ContainerParameter {
	
	/**
	 * 数组元素类型
	 */
	private Class arrayType;
	
  /**
   * Constructor with a IoC description object.
   */
  public ArrayParameter(Class arrayType,Object array)throws BeanParameterException {
  	super(array);
  	this.arrayType = arrayType;
  
    if(arrayType==null)
    	throw new BeanParameterException("Parameter array type can't be null");
    if(array==null)
    	throw new BeanParameterException("Parameter array can't be null");
    if(!array.getClass().isArray())
    	throw new BeanParameterException("Parameter is not an array");
  }
  
  /**
	 * 数组元素类型
	 */
  public Class getArrayType(){
  	return this.arrayType;
  }
}