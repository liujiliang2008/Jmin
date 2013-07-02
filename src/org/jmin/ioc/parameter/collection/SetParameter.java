/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.parameter.collection;

import java.util.Set;

import org.jmin.ioc.BeanParameterException;

/**
 * 容器类型的Value
 *
 * @author Chris Liao
 * @version 1.0
 */
public class SetParameter extends CollectionParameter {

  /**
   * Constructor with a IoC description object.
   */
  public SetParameter(Set set)throws BeanParameterException {
  	super(set);
  }
}