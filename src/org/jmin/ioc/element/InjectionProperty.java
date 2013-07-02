/*
*Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.element;

import org.jmin.ioc.BeanElement;
import org.jmin.ioc.BeanElementException;
import org.jmin.ioc.BeanParameter;

/**
 * IOC property description object in a bean,setter method will be called to inject parameter value
 *
 * @author Chris Liao
 * @version 1.0
 */

public final class InjectionProperty implements BeanElement {

  /**
   * Property name
   */
  private String propertyName;

  /**
   * Property value
   */
  private BeanParameter parameter;
  
  /**
   * 构造函数
   */
  public InjectionProperty(String propertyName,BeanParameter parameter)throws BeanElementException {
    this.propertyName = propertyName;
    this.parameter = parameter;
    this.checkPropertyName(propertyName);
    this.checkParameter(parameter);
  }

  /**
   * Return property name
   */
  public String getPropertyName() {
    return propertyName;
  }

  /**
   * Return IOC parameter
   */
  public BeanParameter getParameter() {
    return parameter;
  }

  /**
   * override method
   */
  public String toString(){
    return "Property:" + propertyName;
  }

  /**
   * Return hash code for this object
   */
  public int hashCode() {
  	return propertyName.trim().hashCode() ^ parameter.hashCode();
  }

  /**
   * Override method
   */
  public boolean equals(Object obj) {
    if (obj instanceof InjectionProperty) {
       InjectionProperty other = (InjectionProperty) obj;
       return this.propertyName.equals(other.propertyName);
    }else{
      return false;
    }
  }
  
  /**
   * 检查方法名
   */
  private void checkPropertyName(String methodName)throws BeanElementException{
  	if(methodName == null ||  methodName.trim().length()==0)
   		throw new BeanElementException("Property name can't be null");
  }
 
  /**
   * 检查参数
   */
  private void checkParameter(BeanParameter parameter)throws BeanElementException{
  	if(parameter==null)
  		throw new BeanElementException("Property parameter can't be null");
  }
}