/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.impl.definition;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.jmin.ioc.BeanDefinition;
import org.jmin.ioc.BeanException;
import org.jmin.ioc.element.InjectionField;
import org.jmin.ioc.element.InjectionInvocation;
import org.jmin.ioc.element.InjectionProperty;
import org.jmin.ioc.element.InvocationInterception;
import org.jmin.ioc.impl.intercept.InterceptionHandler;
import org.jmin.ioc.impl.intercept.InterceptorChain;
import org.jmin.ioc.impl.util.ObjectUtil;

/**
 * A bean wrapper store java beans
 *
 * @author Chris Liao
 * @version 1.0
 */

public abstract class BaseDefinition implements BeanDefinition {
	
  /**
   * Bean registeration ID in Ioc container
   */
  private Object beanID;
  
  /**
   * singleton instance on bean.默认为单例
   */
  private boolean singleton=true;
 
  /**
   * bean init method
   */
  private String initMethodName;

  /**
   * bean destory method
   */
  private String destroyMethodName;
  
  /**
   * ref ID of super class
   */
  private Object parentReferenceId;
  
  /**
   * implements interface for bean
   */
  private Class[] ProxyInterfaces;
  
  
  /**
   * field list
   */
  protected List injectFieldList;

  /**
   * proptery list
   */
  protected List injectPropertyList;

  /**
   * invocation list
   */
  protected List injectInvocationList;

  /**
   * inteception list
   */
  protected List interceptionList;

  /**
   * init method
   */
  private Method initMethod = null;
  
  /**
   * destroy Method
   */
  private Method destroyMethod = null;
  
  
  
  /*********************以下为非接口实现需要的变量******************/
	/**
	 * 类是否已经被修改过
	 */
	private boolean classModified;
  
	/**
	 * 是否已经创建过实例
	 */
	private boolean instaceCreated;
	
  /**
   * 只保留单利模式下的一个实例
   */
  private Object singletonInstance;
  
  /**
   * 方法调用拦截链
   */
  private InterceptorChain interceptorChain=null;
  
  /**
   * Proxy下需要InvocationHandler实现
   */
  private InterceptionHandler interceptionHandler;
  
  /**
   * Class constructor with a ioc registeration ID
   * @param beanID
   */
  public BaseDefinition(Object beanID) {
    this.beanID = beanID;
    this.injectFieldList= new ArrayList();
    this.injectPropertyList = new ArrayList();
    this.injectInvocationList = new ArrayList();
    this.interceptionList = new ArrayList();
  }
  
  /**
   * Retrieve the key associated with the bean.
   */
  public Object getBeanID(){
    return this.beanID;
  }

  /**
   * return implimetation class
   */
  public Class getBeanClass(){
    throw new RuntimeException("Need override in sub class");//support java1.3
  }
 
  /**
   * return init method name
   */
  public String getInitMethodName()throws BeanException {
    return this.initMethodName;
  }
  
  /**
   * set destroy method
   */
  public void setInitMethodName(String initMethodName)throws BeanException{
  	try {
  		if(!this.instaceCreated){
				this.initMethod = ObjectUtil.findMethod(this.getBeanClass(),initMethodName,new Class[0]);
		    this.initMethodName = initMethodName;
  		}
		} catch (Exception e) {
			throw new BeanException(e);
		}
  }
  
  /**
   * return destroy Method Name
   */
  public String getDestroyMethodName()throws BeanException {
    return this.destroyMethodName;
  }
  
  /**
   * return destroy Method Name
   */
  public void setDestroyMethodName(String destroyMethodName)throws BeanException {
  	try {
  		if(!this.instaceCreated){
  			this.destroyMethod = ObjectUtil.findMethod(this.getBeanClass(),destroyMethodName,new Class[0]);
			  this.destroyMethodName = destroyMethodName;
  		}
		} catch (Exception e) {
			throw new BeanException(e);
		}
  }
  
  /**
   * Return super ref ID
   */
  public Object getParentReferenceId()throws BeanException {
    return parentReferenceId;
  }

  /**
   * set super ref ID
   */
  public void setParentReferenceId(Object parentID)throws BeanException {
  	if(!this.instaceCreated){
  		this.parentReferenceId = parentID;
  	}
  }
  
  /**
   * is singleton instance
   */
  public boolean isInstanceSingleton() throws BeanException{
    return singleton;
  }

  /**
   * set singleton for bean
   */
  public void setInstanceSingleton(boolean singleton)throws BeanException {
  	if(!this.instaceCreated){
  		this.singleton = singleton;
  	}
  }
 
	/**
	 * set proxy interface class on
	*/
  public void setProxyInterfaces(Class[] proxyInterfaces)throws BeanException {
  	if(!this.instaceCreated){
		  for (int i = 0; i < proxyInterfaces.length; i++) {
		 		if (!proxyInterfaces[i].isAssignableFrom(this.getBeanClass()))
				  throw new BeanException("Class["+ proxyInterfaces[i]+ "] is not a super interface for bean ID[" + this.getBeanID() + "]");
			}
		  this.ProxyInterfaces = proxyInterfaces;
  	}
  }

  /**
   * return implemented interfaces
   */
  public Class[] getProxyInterfaces()throws BeanException {
    return ProxyInterfaces;
  }
 
  
  /**
   * Add a Field description for the bean class
   */
  public void addInjectionField(InjectionField field)throws BeanException{
   if(!this.instaceCreated && !injectFieldList.contains(field))
  	 injectFieldList.add(field);
  }

  /**
   * Remove a Field description for the bean class
   */
  public void removeInjectionField(InjectionField field)throws BeanException {
  	if(!this.instaceCreated)
  		injectFieldList.remove(field);
  }

  
  /**
   * Add a property description for the bean class
   */
  public void addInjectionProperty(InjectionProperty property)throws BeanException{
   if(!this.instaceCreated && !injectPropertyList.contains(property))
     injectPropertyList.add(property);
  }

  /**
   * Remove a property description for the bean class
   */
  public void removeInjectionProperty(InjectionProperty property)throws BeanException {
  	if(!this.instaceCreated)
     injectPropertyList.remove(property);
  }

  /**
   * Add a method invocation for the bean class
   */
  public void addInjectionInvocation(InjectionInvocation invocation)throws BeanException{
  	if(!this.instaceCreated && !injectInvocationList.contains(invocation))
  		injectInvocationList.add(invocation);
  }

  /**
   * Remove a method invocation for the bean class
   */
  public void removeInjectionInvocation(InjectionInvocation invocation)throws BeanException {
  	if(!this.instaceCreated)
  		injectInvocationList.remove(invocation);
  	
  }
  
  /**
   * Add a Interception for bean class
   */
  public void addInvocationInterception(InvocationInterception interception)throws BeanException {
    if(!this.isInstaceCreated())
      interceptionList.add(interception);
  }

  /**
   * Remove a Interception for bean class
   */
  public void removeInvocationInterception(InvocationInterception interception)throws BeanException {
    if(!this.isInstaceCreated() && interceptorChain!=null&& interceptionList.contains(interception)) 
      interceptionList.remove(interception);
  }

  /****************************************以下方法为非接口实现方法*********************************************/
  
	/**
	 * 类是否已经被修改过
	 */
	public boolean isClassModified() {
		return classModified;
	}

	/**
	 * 类是否已经被修改过
	 */
	public void setClassModified(boolean classModified) {
		this.classModified = classModified;
	}
 
  /**
	 * 是否已经创建过实例
	 */
	public boolean isInstaceCreated() {
		return instaceCreated;
	}
	
	/**
	 * 是否已经创建过实例
	 */
	public void setInstaceCreated(boolean inited) {
		this.instaceCreated = inited;
	}
  
	/**
   * 获得初始化方法
   */
	public Method getInitMethod() {
		return initMethod;
	}
	
	/**
   * 获得初破坏方法
   */
	public Method getDestroyMethod() {
		return this.destroyMethod;
	}

  /**
   * 单实例
   */
	public Object getSingletonInstance() {
		return this.singletonInstance;
	}

  /**
   * 单实例
   */
	public void setSingletonInstance(Object beanInstance) {
		this.singletonInstance = beanInstance;
	}

	
	/**
   * 获得注入属性列表
   */
  public List getInjectionFieldList() {
    return injectFieldList;
  }
	
  /**
   * 获得注入属性列表
   */
  public List getInjectionPropertyList() {
    return injectPropertyList;
  }
	
  /**
   * 获得注入方法列表
   */
  public List getInjectionInvocationList() {
    return injectInvocationList;
  }
	
  /**
   * 获得调用拦截列表
   */
  public List getInvocationInterceptionList() {
    return interceptionList;
  }
  
  /**
   * 方法调用拦截链
   */
	public InterceptorChain getInterceptorChain() {
		return interceptorChain;
	}
	
	 /**
   * 方法调用拦截链
   */
	public void setInterceptorChain(InterceptorChain interceptorChain) {
		this.interceptorChain = interceptorChain;
	}
	
  /**
   * Proxy下需要InvocationHandler实现
   */
	public InterceptionHandler getInterceptionHandler() {
		return interceptionHandler;
	}
	
  /**
   * Proxy下需要InvocationHandler实现
   */
	public void setInterceptionHandler(InterceptionHandler interceptionHandler) {
		this.interceptionHandler = interceptionHandler;
	}
}