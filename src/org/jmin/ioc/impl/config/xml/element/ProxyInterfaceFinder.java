/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.impl.config.xml.element;

import org.jdom.Element;
import org.jmin.ioc.BeanElement;
import org.jmin.ioc.BeanElementException;
import org.jmin.ioc.element.ProxyInterfaces;
import org.jmin.ioc.impl.config.xml.BeanXMLNodeUtil;
import org.jmin.ioc.impl.util.ClassUtil;
import org.jmin.ioc.impl.util.StringUtil;

/**
 * Bean Element Finder
 *
 * @author Chris Liao
 * @version 1.0
 */

public class ProxyInterfaceFinder implements BeanElementXMLFinder{

	/**
	 * 元素名
	 */
	private static final String Element_Node_Name = "proxy-interfaces"; 
	
	/**
	 * 查找Bean定义元素
	 */
	public BeanElement find(Element beanNode,String beanid,String spaceName,String file) throws BeanElementException{
		try {
			String proxyString = BeanXMLNodeUtil.getValueByName(beanNode,Element_Node_Name);
		  if(proxyString!=null){
			String[]proxyInterfaceNames = StringUtil.split(proxyString,",");
			Class[] proxyClasses = new Class[proxyInterfaceNames.length];
			 for(int i=0;i<proxyInterfaceNames.length;i++)
				proxyClasses[i] = ClassUtil.loadClass(proxyInterfaceNames[i].trim());
			if(proxyClasses.length>0)
			 return new ProxyInterfaces(proxyClasses);
			else
				return null;
		 }else{
			 return null;
		 }
		} catch (ClassNotFoundException e) {
			throw new BeanElementException(e);
		}
	}
}