/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.parameter;

import org.jmin.ioc.BeanParameter;
import org.jmin.ioc.BeanParameterException;

/**
 * 容器类型的Value
 *
 * @author Chris Liao
 * @version 1.0
 */
public abstract class ContainerParameter extends BeanParameter {

  /**
   * Constructor with a IoC description object.
   */
  public ContainerParameter(Object content)throws BeanParameterException {
  	super(content);
  }
}