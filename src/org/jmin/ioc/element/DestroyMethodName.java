/*
*Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.element;

import org.jmin.ioc.BeanElement;
import org.jmin.ioc.BeanElementException;

/**
 * 事例对象释放的时候,将调用破坏方法来做一些结束事情
 * 
 * <destroy-method>xxx.xxx</destroy-method>
 *
 * @author Chris Liao
 * @version 1.0
 */
public final class DestroyMethodName implements BeanElement {

  /**
   * 破坏方法名
   */
  private String methodName;
  
  /**
   * 构造函数
   */
  public DestroyMethodName(String methodName)throws BeanElementException {
  	this.checkMethodName(methodName);
  	this.methodName = methodName;
  }
  
  /**
   * 破坏方法名
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
    if (obj instanceof DestroyMethodName) {
    	DestroyMethodName other = (DestroyMethodName) obj;
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
		buff.append("Destroy method:" + methodName + "()");
		return buff.toString();
  }
  
  /**
   * 检查方法名
   */
  private void checkMethodName(String methodName)throws BeanElementException{
  	if(methodName == null || methodName.trim().length()==0)
   		throw new BeanElementException("Destroy method name can't be null");
  }
}