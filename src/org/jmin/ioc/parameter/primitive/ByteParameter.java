/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.parameter.primitive;

import org.jmin.ioc.BeanParameterException;
import org.jmin.ioc.parameter.PrimitiveParameter;

/**
 * byte primitive Type
 *
 * @author Chris Liao
 * @version 1.0
 */

public final class ByteParameter extends PrimitiveParameter {

  /**
   * Constructor
   */
  public ByteParameter(byte value)throws BeanParameterException {
    super(new Byte(value));
  }

  /**
   * Constructor
   */
  public ByteParameter(Byte value)throws BeanParameterException {
    super(value);
  }

  /**
   * Return class for Primitive
   */
  public Class getTypeClass() {
    return Byte.TYPE;
  }
}