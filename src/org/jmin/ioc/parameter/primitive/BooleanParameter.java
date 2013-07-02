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

public final class BooleanParameter extends PrimitiveParameter {

  /**
   * Constructor
   */
  public BooleanParameter(boolean value)throws BeanParameterException {
    super(new Boolean(value));
  }

  /**
   * Constructor
   */
  public BooleanParameter(Boolean value)throws BeanParameterException {
    super(value);
  }
  /**
   * Return class for Primitive
   */
  public Class getTypeClass() {
    return Boolean.TYPE;
  }
}