/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.impl.config.xml.element;

import org.jdom.Element;
import org.jmin.ioc.BeanElement;
import org.jmin.ioc.BeanElementException;
import org.jmin.ioc.BeanParameterException;
import org.jmin.ioc.element.InjectionProperty;
import org.jmin.ioc.impl.config.xml.BeanXMLNodeUtil;
import org.jmin.ioc.impl.config.xml.parameter.BeanParameterXMLFactory;
import org.jmin.ioc.impl.util.StringUtil;

/**
 * 查找注入属性
 * 
 * @author chris liao
 */

public class InjectionPropertyFinder implements  BeanElementXMLFinder{

	/**
	 * 查找注入属性
	 */
	public BeanElement find(Element beanNode,String beanid,String spaceName,String file) throws BeanElementException{
		String propertyName = BeanXMLNodeUtil.getValueByName(beanNode, "name");
		try {
			if (!StringUtil.isNull(propertyName))
				return new InjectionProperty(propertyName,BeanParameterXMLFactory.find(spaceName,beanNode));
			else
				throw new BeanElementException("Null property name of bean:"+beanid +" at file:"+file);
		} catch (BeanParameterException e) {
			 throw new BeanElementException("Failed to find parameter for property["+propertyName+"]of bean:"+beanid +" at file:"+file,e);
		}
	}
}