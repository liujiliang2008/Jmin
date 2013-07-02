/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.parameter.primitive;

import org.jmin.ioc.BeanParameterException;
import org.jmin.ioc.parameter.PrimitiveParameter;

/**
 * boolean primitive Type
 *
 * @author Chris Liao
 * @version 1.0
 */

public final class IntegerParameter extends PrimitiveParameter {

  /**
   * constructor
   */
  public IntegerParameter(int value)throws BeanParameterException {
   super(new Integer(value));
  }

  /**
   * constructor
   */
  public IntegerParameter(Integer value)throws BeanParameterException {
  	super(value);
  }
  
  /**
   * Return class for Primitive
   */
  public Class getTypeClass() {
    return Integer.TYPE;
  }

}