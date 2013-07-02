/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.impl.definition;

import org.jmin.ioc.BeanException;

/**
 * A container store instance of a java beans
 *
 * @author Chris Liao
 * @version 1.0
 */

public class InstanceDefinition extends BaseDefinition {
	
  /**
   * 代理实例
   */
  private Object proxyInstance;

  /**
   * 构造函数
   */
  public InstanceDefinition(Object id,Object instance) {
    super(id);
    super.setSingletonInstance(instance);
  }
  
  /**
   * Bean类
   */
  public Class getBeanClass() {
   return super.getSingletonInstance().getClass();
  }
  
  /**
   * is singleton instance
   */
  public boolean isInstanceSingleton() throws BeanException{
    return true;
  }

	/**
   * 代理实例
   */
  public Object getBeanProxyInstance() {
		return proxyInstance;
	}
  
  /**
   * 代理实例
   */
	public void setProxyInstance(Object proxyInstance) {
		this.proxyInstance = proxyInstance;
	}
}