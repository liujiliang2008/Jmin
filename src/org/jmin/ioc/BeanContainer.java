/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc;

import java.util.Map;

import org.jmin.ioc.element.DestroyMethodName;
import org.jmin.ioc.element.InitializeMethodName;
import org.jmin.ioc.element.InjectionField;
import org.jmin.ioc.element.InjectionInvocation;
import org.jmin.ioc.element.InjectionProperty;
import org.jmin.ioc.element.InstanceCreation;
import org.jmin.ioc.element.InstanceSingleton;
import org.jmin.ioc.element.InvocationInterception;
import org.jmin.ioc.element.ParentReferenceId;
import org.jmin.ioc.element.ProxyInterfaces;
/**
 * A micro-container registeres bean part definition,and which is called as elements,
 * the container will control bean instances lifecycle by their elements definition.
 * After elements registeration,bean instances can be gotten with registered keys from
 * the container.Some registeration methods are supplied to import elements to container.
 *
 * @author Chris Liao
 * @version 1.0
 */

public interface BeanContainer  {

  /**
   * contains beanID
   */
  public boolean containsId(Object id);

  /**
   * Find a bean instance from container by a id.
   * If not found, then return null.
   */
  public Object getBean(Object id)throws BeanException;

  /**
   * Find a bean instance with a class. If not found, then return null.
   */
  public Object getBeanOfType(Class cls)throws BeanException;

  /**
   * Find all bean instance map
   */
  public Map getBeansOfType(Class cls)throws BeanException;
  
  /**
   * register a classes
   */
  public void registerClass(Class beanClass)throws BeanException;
  
  /**
   * register a object instance in container
   */
  public void registerInstance(Object instance)throws BeanException;

  /**
   * register a object instance with a id
   */
  public void registerInstance(Object id, Object instance)throws BeanException;

  /**
   * register a classes
   */
  public void registerClass(Class beanClass, InjectionProperty[] properties)throws BeanException;

  /**
   * register a classes
   */
  public void registerClass(Class beanClass, InjectionInvocation[] invocation)throws BeanException;

  /**
   * register a classes
   */
  public void registerClass(Class beanClass, InjectionProperty[] properties,InjectionInvocation[] invocation)throws BeanException;

  /**
   * register a classes
   */
  public void registerClass(Class beanClass, BeanParameter[] parameters)throws BeanException;

  /**
   * register a classes
   */
  public void registerClass(Class beanClass, BeanParameter[] parameters,InjectionProperty[] properties)throws BeanException;

  /**
   * register a classes
   */
  public void registerClass(Class beanClass, BeanParameter[] parameters,InjectionInvocation[] invocation)throws BeanException;

  /**
   * register a classes
   */
  public void registerClass(Class beanClass, BeanParameter[] parametersr,InjectionProperty[] properties, InjectionInvocation[] invocation)throws BeanException;

  /**
   * register a classes
   */
  public void registerClass(Class beanClass, InstanceCreation constructor)throws BeanException;

  /**
   * register a classes
   */
  public void registerClass(Class beanClass, InstanceCreation constructor,InjectionProperty[] properties)throws BeanException;

  /**
   * register a classes
   */
  public void registerClass(Class beanClass, InstanceCreation constructor,InjectionInvocation[] invocation)throws BeanException;

  /**
   * register a classes
   */
  public void registerClass(Class beanClass, InstanceCreation constructor,InjectionProperty[] properties, InjectionInvocation[] invocation)throws BeanException;

  /**
   * register a classes
   */
  public void registerClass(Class beanClass, BeanElement[] registerElements)throws BeanException;


  /**
   * register a classes
   */
  public void registerClass(Object id, Class beanClass)throws BeanException;

  /**
   * register a classes
   */
  public void registerClass(Object id, Class beanClass,InjectionProperty[] properties)throws BeanException;

  /**
   * register a classes
   */
  public void registerClass(Object id, Class beanClass,InjectionInvocation[] invocation)throws BeanException;

  /**
   * register a classes
   */
  public void registerClass(Object id, Class beanClass,InjectionProperty[] properties,InjectionInvocation[] invocation)throws BeanException;

  /**
   * register a classes
   */
  public void registerClass(Object id, Class beanClass,BeanParameter[] parameters)throws BeanException;

  /**
   * register a classes
   */
  public void registerClass(Object id, Class beanClass,BeanParameter[] parameters, InjectionProperty[] properties)throws BeanException;

  /**
   * register a classes
   */
  public void registerClass(Object id, Class beanClass,BeanParameter[] parameters, InjectionInvocation[] invocation)throws BeanException;

  /**
   * register a classes
   */
  public void registerClass(Object id, Class beanClass,BeanParameter[] parameters, InjectionProperty[] properties,InjectionInvocation[] invocation)throws BeanException;

  /**
   * register a classes
   */
  public void registerClass(Object id, Class beanClass,InstanceCreation constructor)throws BeanException;

  /**
   * register a classes
   */
  public void registerClass(Object id, Class beanClass,InstanceCreation constructor,InjectionProperty[] properties)throws BeanException;

  /**
   * register a classes
   */
  public void registerClass(Object id, Class beanClass,InstanceCreation constructor,InjectionInvocation[] invocation)throws BeanException;

  /**
   * register a classes
   */
  public void registerClass(Object id, Class beanClass,InstanceCreation constructor,InjectionProperty[] properties, InjectionInvocation[] invocation)throws BeanException;

  /**
   * register a classes
   */
  public void registerClass(Object id, Class beanClass,BeanElement[] registerElements)throws BeanException;

  /**
   * deregister a bean by id
   */
  public void deregister(Object id)throws BeanException;
  
  /**
   * Destroy operation in ioc container
   */
  public void destroy();
  
  
  
  
  /**
   * Element factory
   */
  public BeanElementFactory getBeanElementFactory()throws BeanException;
  
  /**
   *Parameter factory
   */
  public BeanParameterFactory getBeanParameterFactory()throws BeanException;
  
  /**
   * set singleton for bean
   */
  public void setInstanceSingleton(Object id, boolean singleton)throws BeanException;

  /**
   * set singleton for bean
   */
  public void setInstanceSingleton(Object id, InstanceSingleton singleton)throws BeanException;
 
  /**
   * set singleton for bean
   */
  public void setParentReferenceId(Object id, Object parentID)throws BeanException;
  
  /**
   * set singleton for bean
   */
  public void setParentReferenceId(Object id, ParentReferenceId parent)throws BeanException;

  /**
   * set init Invocation
   */
  public void setInitMethodName(Object id,String methodName)throws BeanException;
  
  /**
   * set init Invocation
   */
  public void setInitMethodName(Object id,InitializeMethodName methodName)throws BeanException;
  
  /**
   * set destroy Invocation
   */
  public void setDestroyMethodName(Object id, String methodName)throws BeanException;

  /**
   * set destroy Invocation
   */
  public void setDestroyMethodName(Object id, DestroyMethodName methodName)throws BeanException;
  
  

  
 
  
  /**
   * add a field
   */
  public void addInjectionField(Object id,InjectionField field)throws BeanException;

  /**
   * remove a field
   */
  public void removeInjectionField(Object id,InjectionField field)throws BeanException;
  
  /**
   * add a property
   */
  public void addInjectionProperty(Object id,InjectionProperty property)throws BeanException;

  /**
   * remove a property
   */
  public void removeInjectionProperty(Object id,InjectionProperty property)throws BeanException;

  /**
   * add a Invocation invocation
   */
  public void addInjectionInvocation(Object id,InjectionInvocation invocation)throws BeanException;

  /**
   * remove a Invocation invocation
   */
  public void removeInjectionInvocation(Object id,InjectionInvocation invocation)throws BeanException;

  /**
   * add a interception
   */
  public void addInvocationInterception(Object id, InvocationInterception interceptions)throws BeanException;

  /**
   * remove a  interception
   */
  public void removeInvocationInterception(Object id, InvocationInterception interceptions)throws BeanException;

  /**
   * Set proxy interfaces on a object for
   */
  public void setProxyInterfaces(Object id,Class[] proxyInterfaces)throws BeanException;
  
  /**
   * Set proxy interfaces on a object for
   */
  public void setProxyInterfaces(Object id,ProxyInterfaces proxyInterfaces)throws BeanException;
  

  
  
  
	/**
	 * check contains converter by type
	 */
	public boolean containsTypeConverter(Class type)throws BeanException;
	 
	/**
	 * remove type converter
	 */
	public void removeTypeConverter(Class type)throws BeanException;
	
	/**
	 * get type converter
	 */
	public BeanTypeConverter getTypeConverter(Class type)throws BeanException;
	
	/**
	 * add type converter
	 */
	public void addTypeConverter(Class type,BeanTypeConverter converter)throws BeanException;
	
	/**
	 * convert object
	 */
	public Object convertObject(Object obj,Class type)throws BeanException;

}