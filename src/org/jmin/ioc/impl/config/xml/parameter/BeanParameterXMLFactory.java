/*
* Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.ioc.impl.config.xml.parameter;

import java.util.HashMap;
import java.util.Map;

import org.jdom.Element;
import org.jmin.ioc.BeanParameter;
import org.jmin.ioc.BeanParameterException;
import org.jmin.ioc.impl.config.xml.parameter.collection.ListParamFinder;
import org.jmin.ioc.impl.config.xml.parameter.collection.MapParamFinder;
import org.jmin.ioc.impl.config.xml.parameter.collection.SetParamFinder;
import org.jmin.ioc.impl.util.StringUtil;

/**
 * 参数查找工厂
 * 
 * @author Chris Liao
 */

public class BeanParameterXMLFactory {
	
	/**
	 * 查找器列表
	 */
  private static Map finderMap = new HashMap();

  static {
     MapParamFinder mapFinder = new MapParamFinder();
     finderMap.put(BeanParameterXMLTags.Map,mapFinder);
     finderMap.put(BeanParameterXMLTags.HashMap,mapFinder);
     finderMap.put(BeanParameterXMLTags.Hashtable,mapFinder);
     finderMap.put(BeanParameterXMLTags.TreeMap,mapFinder);
     finderMap.put(BeanParameterXMLTags.WeakHashMap,mapFinder);
     finderMap.put(BeanParameterXMLTags.Props,mapFinder);
     
     ListParamFinder listFinder = new ListParamFinder();
     finderMap.put(BeanParameterXMLTags.List,listFinder);
     finderMap.put(BeanParameterXMLTags.ArrayList,listFinder);
     finderMap.put(BeanParameterXMLTags.LinkedList,listFinder);
     finderMap.put(BeanParameterXMLTags.Stack,listFinder);
     finderMap.put(BeanParameterXMLTags.Vector,listFinder);
     
     SetParamFinder setFinder = new SetParamFinder();
     finderMap.put(BeanParameterXMLTags.Set,setFinder);
     finderMap.put(BeanParameterXMLTags.HashSet,setFinder);
     finderMap.put(BeanParameterXMLTags.TreeSet,setFinder);
     finderMap.put(BeanParameterXMLTags.Array,setFinder);
     
 	   finderMap.put(BeanParameterXMLTags.Value,new TextValueParamFinder());
     finderMap.put(BeanParameterXMLTags.Class,new ClassNameParamFinder());
     finderMap.put(BeanParameterXMLTags.Ref,new ReferenceParamFinder());
    }
  

	/**
	 * 从一个节点中查找出其XML参数
	 */
	public static BeanParameter find(String spaceName,Element element)throws BeanParameterException {
  	Element targetElement = element;
  	BeanParameterXMLFinder finder =(BeanParameterXMLFinder)finderMap.get(element.getName());
  	 if(finder == null){
  		 	Element subElement = getChildConainerNode(element);
  		 	if(subElement != null){
  		 		finder = (BeanParameterXMLFinder)finderMap.get(subElement.getName());
  		 		 targetElement = subElement;
  		 	}
  	 }
  	
  	if(finder == null){
    	 if (isValueNode(element)) {
    		 finder = (BeanParameterXMLFinder)finderMap.get(BeanParameterXMLTags.Value);
       } else if (isClassNode(element)) {
      	 finder = (BeanParameterXMLFinder)finderMap.get(BeanParameterXMLTags.Class);
       } else if (isRefNode(element)) {
      	 finder = (BeanParameterXMLFinder)finderMap.get(BeanParameterXMLTags.Ref);
       }
    }
    
    if (finder != null)
      return finder.find(spaceName,targetElement);
    else
      throw new BeanParameterException("Not found a machted parmeter finder for unknown parameter node:<"+ element.getName()+">");
	}

	
	/**
	 * 是否为文本参数类型节点
	 */
	private static boolean isValueNode(Element paraElement) {
		return (isNodeType(paraElement,BeanParameterXMLTags.Value) ||!StringUtil.isNull(paraElement.getTextTrim()));
	}
	
	/**
	 * 是否为类参数节点
	 */
	private static boolean isClassNode(Element paraElement) {
		return isNodeType(paraElement,BeanParameterXMLTags.Class);
	}

	/**
	 * 是否为引用节点
	 */
	private static boolean isRefNode(Element paraElement) {
		return isNodeType(paraElement,BeanParameterXMLTags.Ref);
	}

	/**
	 * 将节点是否为某种类型
	 */
	private static boolean isNodeType(Element paraElement,String type) {
		return (paraElement.getName().equals(type)|| paraElement.getAttribute(type) != null || paraElement.getChild(type) != null)?true: false;
	}

	/**
	 * 查找子节点，且为容器型节点
	 */
	private static Element getChildConainerNode(Element paraElement) {
		Element element = paraElement.getChild(BeanParameterXMLTags.Array);
		if (element == null)
			element = paraElement.getChild(BeanParameterXMLTags.List);
		if (element == null)
			element = paraElement.getChild(BeanParameterXMLTags.ArrayList);
		if (element == null)
			element = paraElement.getChild(BeanParameterXMLTags.LinkedList);
		if (element == null)
			element = paraElement.getChild(BeanParameterXMLTags.Stack);
		if (element == null)
			element = paraElement.getChild(BeanParameterXMLTags.Vector);

		if (element == null)
			element = paraElement.getChild(BeanParameterXMLTags.Set);
		if (element == null)
			element = paraElement.getChild(BeanParameterXMLTags.HashSet);
		if (element == null)
			element = paraElement.getChild(BeanParameterXMLTags.TreeSet);

		if (element == null)
			element = paraElement.getChild(BeanParameterXMLTags.Map);
		if (element == null)
			element = paraElement.getChild(BeanParameterXMLTags.HashMap);
		if (element == null)
			element = paraElement.getChild(BeanParameterXMLTags.Hashtable);
		if (element == null)
			element = paraElement.getChild(BeanParameterXMLTags.TreeMap);
		if (element == null)
			element = paraElement.getChild(BeanParameterXMLTags.Props);
		if (element == null)
			element = paraElement.getChild(BeanParameterXMLTags.WeakHashMap);
		return element;
	}
}
