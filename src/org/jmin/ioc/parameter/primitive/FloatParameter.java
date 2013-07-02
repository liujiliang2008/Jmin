/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.parameter.primitive;

import org.jmin.ioc.BeanParameterException;
import org.jmin.ioc.parameter.PrimitiveParameter;

/**
 * Float primitive Type
 *
 * @author Chris Liao
 * @version 1.0
 */

public final class FloatParameter extends PrimitiveParameter {

  /**
   * constructor
   */
  public FloatParameter(float value)throws BeanParameterException {
    super(new Float(value));
  }
  
  /**
   * constructor
   */
  public FloatParameter(Float value)throws BeanParameterException {
  	super(value);
  }
  
  /**
   * Return class for Primitive
   */
  public Class getTypeClass() {
    return Float.TYPE;
  }

}