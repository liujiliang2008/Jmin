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

public final class InstanceParameter extends BeanParameter{

  /**
   * Constructor with a class name
   */
  public InstanceParameter(Object instance) throws BeanParameterException{
    super(instance);
  }
}