/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.impl.config.xml.element;

import org.jdom.Element;
import org.jmin.ioc.BeanElement;
import org.jmin.ioc.BeanElementException;
import org.jmin.ioc.BeanParameter;
import org.jmin.ioc.BeanParameterException;
import org.jmin.ioc.element.AutowiredType;
import org.jmin.ioc.element.InjectionField;
import org.jmin.ioc.impl.config.xml.BeanXMLNodeUtil;
import org.jmin.ioc.impl.config.xml.parameter.BeanParameterXMLFactory;
import org.jmin.ioc.impl.util.StringUtil;

/**
 * Bean Element Finder
 *
 * @author Chris Liao
 * @version 1.0
 */

public class InjectionFieldFinder implements BeanElementXMLFinder{
	
	/**
	 * 元素名
	 */
	private static final String Element_Node_Name = "autowiredType"; 

	/**
	 * 查找注入属性
	 */
	public BeanElement find(Element beanNode,String beanid,String spaceName,String file) throws BeanElementException{
		String fieldName = BeanXMLNodeUtil.getValueByName(beanNode, "name");
		try {
			if (!StringUtil.isNull(fieldName)){
				String autowiredType = BeanXMLNodeUtil.getValueByName(beanNode,Element_Node_Name);
				 
				if(!StringUtil.isNull(autowiredType)){
				 if(!autowiredType.equalsIgnoreCase("ByName") && !autowiredType.equalsIgnoreCase("ByType"))
		  		 throw new BeanElementException("Invalid autowired field["+fieldName+"]of bean:"+beanid +" at file:"+file+",it must be one of[ByName,ByType]");
		
					return new InjectionField(fieldName,AutowiredType.toAutowiredType(autowiredType));
				}else{
					BeanParameter beanParameter = BeanParameterXMLFactory.find(spaceName,beanNode);
					if(beanParameter==null){
						return new InjectionField(fieldName,AutowiredType.BY_NAME);
					}else{
						return new InjectionField(fieldName,beanParameter);
					}
				}
			}else
				throw new BeanElementException("Null field name of bean:"+beanid +" at file:"+file);
		} catch (BeanParameterException e) {
			 throw new BeanElementException("Failed to find parameter for field["+fieldName+"]of bean:"+beanid +" at file:"+file,e);
		}
	}
}