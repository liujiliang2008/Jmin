/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.parameter.collection;

import java.util.List;

import org.jmin.ioc.BeanParameterException;

/**
 * 容器类型的Value
 *
 * @author Chris Liao
 * @version 1.0
 */
public class ListParameter extends CollectionParameter {

  /**
   * Constructor with a IoC description object.
   */
  public ListParameter(List list)throws BeanParameterException {
  	super(list);
  }
}