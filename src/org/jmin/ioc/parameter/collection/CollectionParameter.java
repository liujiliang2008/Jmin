/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.parameter.collection;

import java.util.Collection;

import org.jmin.ioc.BeanParameterException;
import org.jmin.ioc.parameter.ContainerParameter;

/**
 * 容器类型的Value
 *
 * @author Chris Liao
 * @version 1.0
 */
public class CollectionParameter extends ContainerParameter {

  /**
   * Constructor with a IoC description object.
   */
  public CollectionParameter(Collection collection)throws BeanParameterException {
  	super(collection);
  }
}