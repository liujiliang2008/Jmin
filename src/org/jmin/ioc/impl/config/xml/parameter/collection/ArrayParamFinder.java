/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.impl.config.xml.parameter.collection;

import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;
import org.jmin.ioc.BeanParameter;
import org.jmin.ioc.BeanParameterException;
import org.jmin.ioc.impl.config.xml.BeanXMLNodeUtil;
import org.jmin.ioc.impl.config.xml.parameter.BeanParameterXMLFactory;
import org.jmin.ioc.impl.config.xml.parameter.BeanParameterXMLFinder;
import org.jmin.ioc.impl.util.ClassUtil;
import org.jmin.ioc.impl.util.StringUtil;
import org.jmin.ioc.parameter.collection.ArrayParameter;

/**
 * 容器类型的Value
 *
 * @author Chris Liao
 * @version 1.0
 */
public final class ArrayParamFinder implements BeanParameterXMLFinder{

	/**
	 * 解析获得数组参类
	 */
	public BeanParameter find(String spaceName,Element node)throws BeanParameterException{
		String typename =null;
		try {
			Class arrayType = Object.class;
			typename = BeanXMLNodeUtil.getValueByName(node,"type");
			if(!StringUtil.isNull(typename)){
				arrayType = ClassUtil.loadClass(typename);
			}
			
			List valueList= new ArrayList();
			List itemList = node.getChildren("item");
			for (int i = 0; i < itemList.size(); i++) {
				Element item = (Element) itemList.get(i);
				valueList.add(BeanParameterXMLFactory.find(spaceName,item));
			}
			
			return new ArrayParameter(arrayType,valueList.toArray());
		} catch (ClassNotFoundException e) {
		 throw new BeanParameterException("Array parameter type["+typename+"] not found at node<"+node.getName()+">");
		}
	}
}