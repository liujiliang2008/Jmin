/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.impl.config.xml.element;

import org.jdom.Element;
import org.jmin.ioc.BeanElement;
import org.jmin.ioc.BeanElementException;
import org.jmin.ioc.element.InstanceSingleton;
import org.jmin.ioc.impl.config.xml.BeanXMLNodeUtil;
import org.jmin.ioc.impl.util.StringUtil;

/**
 * Bean Element Finder
 *
 * @author Chris Liao
 * @version 1.0
 */

public class InstanceSingletonFinder implements BeanElementXMLFinder{

	/**
	 * 元素名
	 */
	private static final String Element_Node_Name = "singleton"; 
	
	/**
	 * 查找Bean定义元素
	 */
	public BeanElement find(Element beanNode,String beanid,String spaceName,String file) throws BeanElementException{
		String nodeValue = BeanXMLNodeUtil.getValueByName(beanNode,Element_Node_Name);
		if(!StringUtil.isNull(nodeValue)){
		  boolean value = new Boolean(nodeValue.trim()).booleanValue();
		  return new InstanceSingleton(value);
		} else{
			return null;
		}
	}
}