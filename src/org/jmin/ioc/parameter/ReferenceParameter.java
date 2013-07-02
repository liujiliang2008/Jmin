/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.parameter;

import org.jmin.ioc.BeanParameter;
import org.jmin.ioc.BeanParameterException;

/**
 * This object indicates that a property value is anohter java bean.
 *
 * @author Chris Liao
 * @version 1.0
 */

public final class ReferenceParameter extends BeanParameter{

  /**
   * Constructor with a class name
   */
  public ReferenceParameter(Object referenceID) throws BeanParameterException{
    super(referenceID);
  }
  
  /**
   * 获得引用ID
   */
  public Object getReferenceId(){
  	return this.getParameterContent();
  }
  
}