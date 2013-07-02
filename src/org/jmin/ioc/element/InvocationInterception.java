/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.element;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jmin.ioc.BeanElement;
import org.jmin.ioc.BeanElementException;
import org.jmin.ioc.BeanInterceptor;
import org.jmin.ioc.BeanParameter;
import org.jmin.ioc.BeanParameterException;
import org.jmin.ioc.parameter.ClassParameter;
import org.jmin.ioc.parameter.InstanceParameter;
import org.jmin.ioc.parameter.ReferenceParameter;

/**
 * A element object to describe method interception in a bean. If exist some
 * interception on a bean class,container will build a AOP wrapper class on the
 * bean class,then inject some interceptors to the new warpper instance. When
 * specified method invoked,interceptors act.
 *
 * Container determinate some methods need to be wrapped on method names and
 * their parameter types
 *
 * @author Chris Liao
 * @version 1.0
 */

public final class InvocationInterception implements BeanElement {

  /**
   * Intecepted method name in bean
   */
  private String methodName;

  /**
   * Method parameter types
   */
  private Class[] paramTypes;

  /**
   * interceptor List
   */
  private List interceptorList;

  /**
   * Constructor
   */
  public InvocationInterception(String methodName,Class[] paramTypes)throws BeanElementException {
    this.interceptorList = new ArrayList();
    this.methodName = methodName;
    this.paramTypes = paramTypes;
    this.checkMethodName(methodName);
    this.checkParamTypes(paramTypes);
  }

  /**
   * Return intecepted method name
   */
  public String getMethodName() {
    return methodName;
  }

  /**
   * Return parameter class type
   */
  public Class[] getMethodParamTypes() {
    return paramTypes;
  }

  /**
   * add a interceptor
   */
  public void addInterceptor(BeanInterceptor interceptor)throws BeanParameterException {
    InstanceParameter value = new InstanceParameter(interceptor);
    if (!interceptorList.contains(value)) {
      interceptorList.add(value);
    }
  }

  /**
   * add a interceptor
   */
  public void addInterceptorClass(Class interceptorClass)throws BeanParameterException {
    if (!BeanInterceptor.class.isAssignableFrom(interceptorClass))
      throw new IllegalArgumentException("Interceptor class must be implemented from: org.jmin.ioc.BeanInterceptor");
    ClassParameter value = new ClassParameter(interceptorClass);
    if (!interceptorList.contains(value)) {
      interceptorList.add(value);
    }
  }

  /**
   * add a interceptor
   */
  public void addInterceptorClassName(String interceptorClassName)throws BeanParameterException {
    ClassParameter value = new ClassParameter(interceptorClassName);
    if (!interceptorList.contains(value)) {
      interceptorList.add(value);
    }
  }

  /**
   * add a interceptor
   */
  public void addInterceptorReference(Object referenceID)throws BeanParameterException {
    ReferenceParameter value = new ReferenceParameter(referenceID);
    if (!interceptorList.contains(value)) {
      interceptorList.add(value);
    }
  }

  /**
   * Return all interceptor ioc values
   */
  public BeanParameter[] getInterceptorsInfo() {
    BeanParameter[] values = new BeanParameter[interceptorList.size()];
    for (int i = 0; i < values.length; i++)
      values[i] = (BeanParameter) interceptorList.get(i);
    return values;
  }

  /**
   * Override method
   */
  public String toString() {
  	StringBuffer buff = new StringBuffer();
		buff.append("Intercepted method:" + methodName);
		buff.append("(");
		if (paramTypes != null) {
			for (int i = 0; i < paramTypes.length; i++) {
				buff.append(paramTypes[i]);
				if (i < paramTypes.length - 1)
					buff.append(",");
			}
		}
		buff.append(")");
		return buff.toString();
  }
  
  /**
   * Override method
   */
  public boolean equals(Object obj) {
    if (obj instanceof InvocationInterception) {
    	InvocationInterception other = (InvocationInterception) obj;
      return this.methodName.equals(other.methodName) 
      	&& Arrays.equals(this.paramTypes,other.paramTypes);
    } else {
      return false;
    }
  }

  /**
   * Return hash code for this interception
   */
  public int hashCode() {
    int hashCode = methodName.trim().hashCode();
    if(paramTypes!=null){
	    for (int i = 0; i < paramTypes.length; i++) 
	      if(paramTypes[i]!=null)
	    	hashCode = hashCode ^ paramTypes[i].hashCode();
  	}
    return hashCode;
  }
  
  /**
   * 检查方法名
   */
  private void checkMethodName(String methodName)throws BeanElementException{
  	if(methodName == null || methodName.trim().length()==0)
   		throw new BeanElementException("Invocation method name can't be null");
  }
 
  /**
   * 检查参数
   */
  private void checkParamTypes(Class[] types)throws BeanElementException{
  	if(types==null)
  		throw new BeanElementException("Method parameter types can't be null");
  	for(int i=0;i<types.length;i++){
  		if(types[i]==null)
  			throw new BeanElementException("Found null parameter type at index:"+i);
  	}
  }
}