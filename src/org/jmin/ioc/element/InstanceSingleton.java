/*
*Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.element;

import org.jmin.ioc.BeanElement;
import org.jmin.ioc.BeanElementException;

/**
 * 是否单例模式
 *
 * <singleton>true</singleton>
 * <singleton>false</singleton>
 *
 * @author Chris Liao
 * @version 1.0
 */

public final class InstanceSingleton implements BeanElement {

  /**
   * 单利标记
   */
  private boolean singleton =true;

  /**
   *构造函数
   */
  public InstanceSingleton(boolean singleton)throws BeanElementException {
    this.singleton = singleton;
  }

  /**
   * 是否单利
   */
  public boolean isSingleton() {
    return singleton;
  }
  
  /**
   * Return hash code for this interception
   */
  public int hashCode() {
  	return singleton?1:0;
  }
  
  /**
   * Override method
   */
  public String toString() {
    return "Singleton:"+ singleton;
  }
  
  /**
   * Override method
   */
  public boolean equals(Object obj) {
    if(obj instanceof InstanceSingleton) {
    	InstanceSingleton other = (InstanceSingleton) obj;
      return this.singleton == other.singleton;
    } else {
      return false;
    }
  }
}