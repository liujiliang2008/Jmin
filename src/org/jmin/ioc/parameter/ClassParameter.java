/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.parameter;

import org.jmin.ioc.BeanParameter;
import org.jmin.ioc.BeanParameterException;

/**
 * This object works as parameter type.Bofore injection,IOC container will load
 * class value from this object,and instantiant it.
 *
 * @author Chris Liao
 * @version 1.0
 */

public final class ClassParameter extends BeanParameter {

  /**
   * Constructor
   */
  public ClassParameter(Class cls)throws BeanParameterException {
    super(cls);
  }

  /**
   * Constructor
   */
  public ClassParameter(String className)throws BeanParameterException {
    super(className);
  }
}