/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.impl.config.xml.parameter.collection;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.Vector;

import org.jdom.Element;
import org.jmin.ioc.BeanParameter;
import org.jmin.ioc.BeanParameterException;
import org.jmin.ioc.impl.config.xml.parameter.BeanParameterXMLFactory;
import org.jmin.ioc.impl.config.xml.parameter.BeanParameterXMLFinder;
import org.jmin.ioc.impl.config.xml.parameter.BeanParameterXMLTags;
import org.jmin.ioc.parameter.collection.ListParameter;

/**
 * 容器类型的Value
 *
 * @author Chris Liao
 * @version 1.0
 */
public class ListParamFinder implements BeanParameterXMLFinder{

	/**
	 * 解析获得List参类
	 */
	public BeanParameter find(String spaceName,Element node)throws BeanParameterException{
		List list = this.createList(node.getName());
		List itemList = node.getChildren("item");
		for (int i = 0; i < itemList.size(); i++) {
			Element item = (Element) itemList.get(i);
			list.add(BeanParameterXMLFactory.find(spaceName,item));
		}
		return new ListParameter(list);
	}
	
	/**
	 * 创建List子类
	 */
	private List createList(String nodename){
		if(BeanParameterXMLTags.ArrayList.equalsIgnoreCase(nodename)){
			return new ArrayList();
		}else if(BeanParameterXMLTags.LinkedList.equalsIgnoreCase(nodename)){
			return new LinkedList();
		}else if(BeanParameterXMLTags.Stack.equalsIgnoreCase(nodename)){
			return new Stack();
		}else if(BeanParameterXMLTags.Vector.equalsIgnoreCase(nodename)){
			return new Vector();
		}else{
			return new ArrayList();
		}
	}
}