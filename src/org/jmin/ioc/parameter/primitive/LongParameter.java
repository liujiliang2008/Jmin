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

public final class LongParameter extends PrimitiveParameter {

  /**
   * constructor
   */
  public LongParameter(long value)throws BeanParameterException {
    super(new Long(value));
  }
  
  /**
   * constructor
   */
  public LongParameter(Long value)throws BeanParameterException {
  	super(value);
  }

  /**
   * Return class for Primitive
   */
  public Class getTypeClass() {
    return Long.TYPE;
  }
}