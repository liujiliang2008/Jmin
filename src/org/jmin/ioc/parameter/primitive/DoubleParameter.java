/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.parameter.primitive;

import org.jmin.ioc.BeanParameterException;
import org.jmin.ioc.parameter.PrimitiveParameter;

/**
 * Double primitive Type
 *
 * @author Chris Liao
 * @version 1.0
 */

public final class DoubleParameter extends PrimitiveParameter {

  /**
   * Constructor
   */
  public DoubleParameter(double value)throws BeanParameterException {
    super(new Double(value));
  }
  
  /**
   * Constructor
   */
  public DoubleParameter(Double value)throws BeanParameterException {
  	super(value);
  }
 
  /**
   * Return class for Primitive
   */
  public Class getTypeClass() {
    return Double.TYPE;
  }
}