/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.impl.config.xml.parameter.collection;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.jdom.Element;
import org.jmin.ioc.BeanParameter;
import org.jmin.ioc.BeanParameterException;
import org.jmin.ioc.impl.config.xml.parameter.BeanParameterXMLFactory;
import org.jmin.ioc.impl.config.xml.parameter.BeanParameterXMLFinder;
import org.jmin.ioc.impl.config.xml.parameter.BeanParameterXMLTags;
import org.jmin.ioc.parameter.collection.SetParameter;

/**
 * 容器类型的Value
 *
 * @author Chris Liao
 * @version 1.0
 */
public class SetParamFinder implements BeanParameterXMLFinder{

	/**
	 * 解析获得Set参类
	 */
	public BeanParameter find(String spaceName,Element node)throws BeanParameterException{
		Set set = this.createSet(node.getName());
		List itemList = node.getChildren("item");
		for (int i = 0; i < itemList.size(); i++) {
			Element item = (Element) itemList.get(i);
			set.add(BeanParameterXMLFactory.find(spaceName,item));
		}
		
		return new SetParameter(set);
	}
	
	/**
	 * 创建Set子类
	 */
	private Set createSet(String nodename){
		if(BeanParameterXMLTags.HashSet.equalsIgnoreCase(nodename)){
			return new HashSet();
		}else if(BeanParameterXMLTags.TreeSet.equalsIgnoreCase(nodename)){
			return new TreeSet();
		}else{
			return new HashSet();
		}
	}
}