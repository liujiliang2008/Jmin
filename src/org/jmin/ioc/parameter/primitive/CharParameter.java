/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.parameter.primitive;

import org.jmin.ioc.BeanParameterException;
import org.jmin.ioc.parameter.PrimitiveParameter;

/**
 * Char primitive Type
 *
 * @author Chris Liao
 * @version 1.0
 */

public final class CharParameter extends PrimitiveParameter {

  /**
   * Constructor
   */
  public CharParameter(char value)throws BeanParameterException {
    super(new Character(value));
  }

  /**
   * Constructor
   */
  public CharParameter(Character value)throws BeanParameterException {
    super(value);
  }

  /**
   * Return class for Primitive
   */
  public Class getTypeClass() {
    return Character.TYPE;
  }
}