/*
*Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.element;

import org.jmin.ioc.BeanElement;
import org.jmin.ioc.BeanElementException;

/**
 * 对象被构造后，将进行一些初始化动作
 *
 * <init-method>xxx.xxx</init-method>
 * @author Chris Liao
 * @version 1.0
 */

public final class InitializeMethodName implements BeanElement {

	 /**
   * 初始化方法名
   */
  private String methodName;
  
  /**
   * 构造函数
   */
  public InitializeMethodName(String methodName)throws BeanElementException{
  	this.checkMethodName(methodName);
    this.methodName = methodName;
  }

  /**
   * 初始化方法名
   */
  public String getMethodName() {
    return methodName;
  }
  
  /**
   * Return hash code for this interception
   */
  public int hashCode() {
    return methodName.trim().hashCode();
  }
  
  /**
   * Override method
   */
  public boolean equals(Object obj) {
    if (obj instanceof InitializeMethodName) {
    	InitializeMethodName other = (InitializeMethodName) obj;
      return this.methodName.equals(other.methodName);
    } else {
      return false;
    }
  }
  
  /**
   * Override method
   */
  public String toString() {
  	StringBuffer buff = new StringBuffer();
		buff.append("Initialize method:" + methodName+"()");
		return buff.toString();
  }
  
  /**
   * 检查方法名
   */
  private void checkMethodName(String methodName)throws BeanElementException{
  	if(methodName == null ||  methodName.trim().length()==0)
   		throw new BeanElementException("Initialize method name can't be null");
  }
}