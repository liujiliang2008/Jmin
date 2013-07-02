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

public final class ShortParameter extends PrimitiveParameter {

  /**
   * constructor
   */
  public ShortParameter(short value)throws BeanParameterException {
    super(new Short(value));
  }

  /**
   * constructor
   */
  public ShortParameter(Short value)throws BeanParameterException {
    super(value);
  }

  /**
   * Return class for Primitive
   */
  public Class getTypeClass() {
    return Short.TYPE;
  }
}