/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.impl.config.xml.parameter.collection;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import java.util.WeakHashMap;

import org.jdom.Element;
import org.jmin.ioc.BeanParameter;
import org.jmin.ioc.BeanParameterException;
import org.jmin.ioc.impl.config.xml.parameter.BeanParameterXMLFactory;
import org.jmin.ioc.impl.config.xml.parameter.BeanParameterXMLFinder;
import org.jmin.ioc.impl.config.xml.parameter.BeanParameterXMLTags;
import org.jmin.ioc.parameter.InstanceParameter;
import org.jmin.ioc.parameter.ReferenceParameter;
import org.jmin.ioc.parameter.collection.MapParameter;

/**
 * 容器类型的Value
 *
 * @author Chris Liao
 * @version 1.0
 */
public class MapParamFinder implements BeanParameterXMLFinder{

	/**
	 * 解析获得Map参类
	 */
	public BeanParameter find(String spaceName,Element node)throws BeanParameterException{
			Map map = this.createMap(node.getName());
			List entryList = node.getChildren("entry");
			for (int i = 0; i < entryList.size(); i++) {
			  Element mapEntry = (Element) entryList.get(i);
			  String key = mapEntry.getAttributeValue("key");
			  BeanParameter keyMeta= null;
			  if(key== null){
			  	key = mapEntry.getAttributeValue("ref");
			  	if(key!=null)
			  		keyMeta = new ReferenceParameter(key);
			  }else{ 
			  	keyMeta = new InstanceParameter(key);
			  }
			  
			 if(keyMeta == null)
			  throw new BeanParameterException("Can't find valid Key for map node " + node.getName());
			 
			  map.put(keyMeta,BeanParameterXMLFactory.find(spaceName,mapEntry));
			}
			return new MapParameter(map);
  }
	
	/**
	 * 创建Map子类
	 */
	private Map createMap(String nodeName){
		if(BeanParameterXMLTags.HashMap.equalsIgnoreCase(nodeName)){
			return new HashMap();
		}else if(BeanParameterXMLTags.Hashtable.equalsIgnoreCase(nodeName)){
			return new Hashtable();
		}else if(BeanParameterXMLTags.Props.equalsIgnoreCase(nodeName)){
			return new Properties();
		}else if(BeanParameterXMLTags.TreeMap.equalsIgnoreCase(nodeName)){
			return new TreeMap();
		}else if(BeanParameterXMLTags.WeakHashMap.equalsIgnoreCase(nodeName)){
			return new WeakHashMap();
		}else{
			return new HashMap();
		}
	}
}