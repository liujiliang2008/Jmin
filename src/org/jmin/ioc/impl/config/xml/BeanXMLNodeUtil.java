/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.impl.config.xml;

import org.jdom.Element;

/**
 * XML节点辅助类
 * 
 * @author Chris Liao
 */
public class BeanXMLNodeUtil {
	
	/**
	 * 是否为顶节点
	 */
	public static boolean isRootNode(Element node) {
		return node.isRootElement();
	}
	/**
	 * 是否为叶子节点
	 */
	public static boolean isLeafNode(Element node) {
		return node.getChildren().isEmpty()? true:false;
	}
	
	/**
	 * 查找子接点
	 */
	public static Element getChild(Element node, String childName) {
		return node.getChild(childName);
	}
	
	/**
	 * 查找子接点
	 */
	public static Element[] getChildren(Element node) {
		return (Element[])node.getChildren().toArray(new Element[0]);
	}
	
	/**
	 * 查找子接点
	 */
	public static Element[] getChildren(Element node, String childName) {
		return (Element[])node.getChildren(childName).toArray(new Element[0]);
	}
	
	/**
	 * 是否存在子节点
	 */
	public static boolean exitChild(Element node,String childName){
		return !node.getChildren(childName).isEmpty();
	}
	
	/**
	 * 是否存在某个属性
	 */
	public static boolean exitAttribute(Element node,String attributeName){
		return node.getAttribute(attributeName)!=null;
	}
	
	/**
	 * 获得节点文本内容
	 */
	public static String getNodeText(Element node) {
		return node.getTextTrim();
	}
	
	/**
	 * 获得子节点文本内容
	 */
	public static String getChildext(Element node,String childName) {
		return node.getChildTextTrim(childName);
	}
	
	/**
	 * 获得节点属性值
	 */
	public static String getAttributeValue(Element node, String attributeName) {
		return node.getAttributeValue(attributeName);
	}
	
	/**
	 * 获得节点属性值
	 */
	public static String getValueByName(Element node,String name){
		String value = null;
		if(exitAttribute(node, name)) {
			value = getAttributeValue(node, name);
		} else if(exitChild(node,name)) {
			Element child = getChild(node,name);
			if(isLeafNode(child)) 
				value= getNodeText(child);
		}else if(isLeafNode(node)){
			value= getNodeText(node);
		}
		
		return value;
	}
}
