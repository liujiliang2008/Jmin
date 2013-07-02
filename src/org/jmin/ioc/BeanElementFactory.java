/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc;

import org.jmin.ioc.element.AutowiredType;
import org.jmin.ioc.element.DestroyMethodName;
import org.jmin.ioc.element.InitializeMethodName;
import org.jmin.ioc.element.InjectionField;
import org.jmin.ioc.element.InjectionInvocation;
import org.jmin.ioc.element.InjectionProperty;
import org.jmin.ioc.element.InstanceCreation;
import org.jmin.ioc.element.InstanceSingleton;
import org.jmin.ioc.element.InvocationInterception;
import org.jmin.ioc.element.ProxyInterfaces;

/**
 * A factory to create some bean elements
 *
 * @author Chris Liao
 * @version 1.0
 */

public class BeanElementFactory {

  /**
   * 创建是否单例属性
   */
  public InstanceSingleton createInstanceSingleton(boolean singleton)throws BeanElementException{
    return new InstanceSingleton(singleton);
  }
	
  /**
   * 创建破坏方法
   */
  public DestroyMethodName createDestroyMethod(String methodname)throws BeanElementException{
    return new DestroyMethodName(methodname);
  }

  /**
   * 创建初始化方法
   */
  public InitializeMethodName createInitializeMethod(String methodname)throws BeanElementException{
    return new InitializeMethodName(methodname);
  }
  
  /**
   *创建构造方法
   */
  public InstanceCreation createInstanceCreation(BeanParameter[] parameters)throws BeanElementException {
    return new InstanceCreation(parameters);
  }

  /**
   * 创建构造方法
   */
  public InstanceCreation createInstanceCreation(String factoryMethod,BeanParameter[] parameters)throws BeanElementException {
    return new InstanceCreation(factoryMethod,parameters);
  }

  /**
   * 创建构造方法
   */
  public InstanceCreation createInstanceCreation(String factoryBeanRefID,String factoryMethod,BeanParameter[] parameters)throws BeanElementException {
    return new InstanceCreation(factoryBeanRefID,factoryMethod,parameters);
  }

  
  /**
   * 创建自动自动注入字段
   */
  public InjectionField createInjectionField(String name)throws BeanElementException {
    return new InjectionField(name);
  }
  
  /**
   * 创建自动自动注入字段
   */
  public InjectionField createInjectionField(String name,AutowiredType type)throws BeanElementException {
    return new InjectionField(name,type);
  }
  
  /**
   * 创建注入字段
   */
  public InjectionField createInjectionField(String name,BeanParameter parameter)throws BeanElementException {
    return new InjectionField(name,parameter);
  }
 
  /**
   * 创建注入属性
   */
  public InjectionProperty createInjectionProperty(String name,BeanParameter parameter)throws BeanElementException {
    return new InjectionProperty(name, parameter);
  }
  
  /**
   * 创建注入方法
   */
  public InjectionInvocation createInjectionInvocation(String methodName,BeanParameter[] methodIocParameters)throws BeanElementException {
    return new InjectionInvocation(methodName, methodIocParameters);
  }
 
  /**
   * 创建实现接口
   */
  public ProxyInterfaces createProxyInterfaces(Class[] implInterfaces)throws BeanElementException{
    return new ProxyInterfaces(implInterfaces);
  }

  /**
   * 创建方法拦截
   */
  public InvocationInterception createInvocationInterception(String methodName,Class[] parameterTypes)throws BeanElementException {
    return new InvocationInterception(methodName,parameterTypes);
  }
}