/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.element;

import java.util.Arrays;

import org.jmin.ioc.BeanElement;
import org.jmin.ioc.BeanElementException;

/**
 * A description element about implemented Interface on a bean. If import some
 * interfaces,then build auto proxy for bean
 * 
 * @author Chris Liao
 * @version 1.0
 */

public final class ProxyInterfaces implements BeanElement {

	/**
	 * auto proxy itnerfaces
	 */
	private Class[] interfaces;

	/**
	 *构造函数
	 */
	public ProxyInterfaces(Class[] interfaces)throws BeanElementException {
		this.interfaces = interfaces;
		this.checkInterfaces(interfaces);
	}

	/**
	 * return proxy interface
	 */
	public Class[] getInterfaces() {
		return this.interfaces;
	}

	/**
	 * override method
	 */
	public String toString() {
		StringBuffer buff = new StringBuffer();
		buff.append("Proxy interfaces:");
		for (int i = 0; i < this.interfaces.length; i++) 
			buff.append(interfaces[i] + "\n");
		return buff.toString();
	}

	/**
	 * Overide method
	 */
	public int hashCode() {
		int code = 0;
		for (int i = 0; i < this.interfaces.length; i++)
			code ^= interfaces[i].hashCode();
		return code;
	}

	/**
	 * Overide method
	 */
	public boolean equals(Object obj) {
		if(obj instanceof ProxyInterfaces) {
			ProxyInterfaces other = (ProxyInterfaces) obj;
			return Arrays.equals(this.interfaces,other.interfaces);
		} else {
			return false;
		}
	}
	
	 
  /**
   * 检查参数
   */
  private void checkInterfaces(Class[] interfaces)throws BeanElementException{
  	if(this.interfaces==null)
			throw new BeanElementException("At least supply an implement interface");
		for(int i=0;i<interfaces.length;i++){
			if(interfaces[i]==null)
				throw new BeanElementException("There exist an null interface at index:" +(i+1));
			if (!interfaces[i].isInterface())
			  throw new BeanElementException("Class["+ interfaces[i]+ "] is not an interface");
		}
  }
}
