/*
*Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc;

/**
 * IOC Parameter.
 *
 * @author Chris Liao
 * @version 1.0
 */

public class BeanParameter{

	 /**
   * parameter Content
   */
  private Object parameterContent = null;

  /**
   * Constructor with a IoC description object.
   */
  public BeanParameter(Object parameterContent)throws BeanParameterException {
  	this.checkParameterObject(parameterContent);
    this.parameterContent = parameterContent;
  }

  /**
   * Return description object for ioc value
   */
  public Object getParameterContent() {
    return this.parameterContent;
  }

  /**
   * Override method
   */
  public String toString() {
     return "" + parameterContent;
  }

  /**
   * Return hash code for parameter
   */
  public int hashCode() {
    return parameterContent.hashCode();
  }

  /**
   * Override method
   */
  public boolean equals(Object obj) {
    if (obj instanceof BeanParameter) {
    	BeanParameter other = (BeanParameter) obj;
      return this.parameterContent.equals(other.parameterContent);
    } else {
      return false;
    }
  }
  
 /**
  * Parameter check
  */
 private void checkParameterObject(Object parameterContent)throws BeanParameterException{
	 if(parameterContent == null)
     throw new BeanParameterException("Parameter content can't be null");
 }
}