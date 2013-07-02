/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.parameter;

import org.jmin.ioc.BeanParameter;
import org.jmin.ioc.BeanParameterException;

/**
 * An abstract class, which represents primitive type in java.
 *
 * @author Chris Liao
 * @version 1.0
 */

public abstract class PrimitiveParameter extends BeanParameter {

  /**
   * constructor
   */
  public PrimitiveParameter(Object primitiveValue)throws BeanParameterException{
    super(primitiveValue);
  }

  /**
   * Return class for Primitive
   */
  public abstract Class getTypeClass();

}