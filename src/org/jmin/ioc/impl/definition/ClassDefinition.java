/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.impl.definition;

import org.jmin.ioc.BeanElement;
import org.jmin.ioc.BeanException;
import org.jmin.ioc.BeanParameter;
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
 * A bean wrapper store java beans
 *
 * @author Chris Liao
 * @version 1.0
 */

public class ClassDefinition extends BaseDefinition {
	
  /**
   * bean class
   */
  private Class beanClass;
  
  /**
   * 包装类，主要为AOP准备
   */
  private Class beanWrapClass;

  /**
   * creation constructor
   */
  private InstanceCreation creation;

	/**
   * constructor
   */
  public ClassDefinition(Object beanID,Class beanClass,BeanElement[] elements)throws BeanException {
    super(beanID);
    this.beanClass = beanClass;
    this.creation = new InstanceCreation(new BeanParameter[0]);//默认给一个无参的构造
    this.divideElements(elements);
  }

  /**
   * return implimetation class
   */
  public Class getBeanClass() {
    return beanClass;
  }
  
  /**
   * 包装类，主要为AOP准备
   */
	public Class getBeanWrapClass() {
		return beanWrapClass;
	}
	
	/**
   * 包装类，主要为AOP准备
   */
	public void setBeanWrapClass(Class beanWrapClass) {
		this.beanWrapClass = beanWrapClass;
	}

  /**
   * 获得构造方法
   */
  public InstanceCreation getInstanceCreation(){
  	return this.creation;
  }
 
  /**
   * @param constructor
   */
  private void setConstruction(InstanceCreation construction){
    this.creation = construction;
  }
  
  /**
   * Separate registered info.
   */
  private void divideElements(BeanElement[] elements)throws BeanException  {
    if (elements != null && elements.length > 0) {
      for (int i = 0; i < elements.length; i++) {
        if (elements[i] instanceof InstanceCreation) {
          this.setConstruction((InstanceCreation) elements[i]);
        } else if(elements[i] instanceof InjectionField){
          this.addInjectionField(((InjectionField)elements[i]));
        } else if (elements[i] instanceof InjectionProperty) {
          this.addInjectionProperty((InjectionProperty) elements[i]);
        } else if (elements[i] instanceof InjectionInvocation) {
          this.addInjectionInvocation((InjectionInvocation) elements[i]);
        } else if (elements[i] instanceof InvocationInterception) {
          this.addInvocationInterception((InvocationInterception) elements[i]);

        } else if ((elements[i] instanceof InstanceSingleton)) {
          this.setInstanceSingleton(((InstanceSingleton) elements[i]).isSingleton());
        } else if ((elements[i] instanceof ParentReferenceId)) {
          this.setParentReferenceId(((ParentReferenceId) elements[i]).getReferenceID());
        } else if (elements[i] instanceof InitializeMethodName) {
          this.setInitMethodName(((InitializeMethodName)elements[i]).getMethodName());
        } else if (elements[i] instanceof DestroyMethodName) {
          this.setDestroyMethodName(((DestroyMethodName)elements[i]).getMethodName());
        }else if(elements[i] instanceof ProxyInterfaces){
          this.setProxyInterfaces(((ProxyInterfaces)elements[i]).getInterfaces());
        }
      }
    }
  }


}