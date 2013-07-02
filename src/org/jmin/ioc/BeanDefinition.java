/*
*Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc;

import org.jmin.ioc.element.InjectionField;
import org.jmin.ioc.element.InjectionInvocation;
import org.jmin.ioc.element.InjectionProperty;
import org.jmin.ioc.element.InvocationInterception;

/**
 * A ioc wrapper for a registered bean instance or a bean class.
 * Some bean definition elements are contained here.
 *
 * @author Chris Liao
 * @version 1.0
 */

public interface BeanDefinition {

  /**
   * Retrieve the key associated with the bean.
   */
  public Object getBeanID();

  /**
   * return implimetation class
   */
  public Class getBeanClass();
  
  /**
   * set destroy method
   */
  public String getInitMethodName()throws BeanException;
  
  /**
   * set destroy method
   */
  public void setInitMethodName(String methodName)throws BeanException;
  
  /**
   * set destroy method
   */
  public String getDestroyMethodName()throws BeanException;
  
  /**
   * set destroy method
   */
  public void setDestroyMethodName(String methodName)throws BeanException;
  
  /**
   * set super refID
   */
  public Object getParentReferenceId()throws BeanException;
  
  /**
   * set super refID
   */
  public void setParentReferenceId(Object superRefID)throws BeanException;
  
  /**
   * set singleton for bean
   */
  public boolean isInstanceSingleton()throws BeanException;
 
  /**
   * set singleton for bean
   */
  public void setInstanceSingleton(boolean singleton)throws BeanException;

  /**
   * set proxy interface class on 
   */
  public void setProxyInterfaces(Class[] proxyInterfaces)throws BeanException;
  
  /**
   * set proxy interface class on 
   */
  public Class[] getProxyInterfaces()throws BeanException;
  
  
  
  /**
   * add a field
   */
  public void addInjectionField(InjectionField field)throws BeanException;

  /**
   * remove a field
   */
  public void removeInjectionField(InjectionField field)throws BeanException;

  /**
   * add a properties
   */
  public void addInjectionProperty(InjectionProperty property)throws BeanException;
  
  /**
   * remove a properties
   */
  public void removeInjectionProperty(InjectionProperty property)throws BeanException;
  
  /**
   * Add a method invocation for the bean class
   */
  public void addInjectionInvocation(InjectionInvocation invocation)throws BeanException;
  
  /**
   * Add a method invocation for the bean class
   */
  public void removeInjectionInvocation(InjectionInvocation invocation)throws BeanException;
  
  /**
   * add a interception
   */
  public void addInvocationInterception(InvocationInterception interception)throws BeanException;
  
  /**
   * remove a interception
   */
  public void removeInvocationInterception(InvocationInterception interception)throws BeanException;
}