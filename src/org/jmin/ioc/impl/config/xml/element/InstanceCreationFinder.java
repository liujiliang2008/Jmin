package org.jmin.ioc.impl.config.xml.element;

import org.jdom.Element;
import org.jmin.ioc.BeanElement;
import org.jmin.ioc.BeanElementException;
import org.jmin.ioc.BeanParameter;
import org.jmin.ioc.BeanParameterException;
import org.jmin.ioc.element.InstanceCreation;
import org.jmin.ioc.impl.config.xml.BeanXMLNodeUtil;
import org.jmin.ioc.impl.config.xml.parameter.BeanParameterXMLFactory;
import org.jmin.ioc.impl.util.StringUtil;

/**
 * XML Template is below
 * 
 *<constructor-arg value="xxxxx"/>
 *<constructor-arg class="xxxxx"/>
 *<constructor-arg ref="xxxxx"/>
 *
 *<constructor-arg>
 *  <value>xxxx</value>
 *</constructor-arg>
 *<constructor-arg>
 * xxxxxxx
 *</constructor-arg>
 *
 *<constructor-arg>
 *  <ref>xxxx</ref>
 *</constructor-arg>
 *<constructor-arg>
 *  <ref value="xxxx"/>
 *</constructor-arg>
 *
 *<constructor-arg>
 *  <class>xxxx</class>
 *</constructor-arg>
 *<constructor-arg>
 *  <class value="xxxx"/>
 *</constructor-arg>
 *
 *
 * <parameter>
 * <map><entry key ="xxxx" value="xxxx"/><entry key ="xxxx"><value>
 * xxxxx</value></entry>
 *
 * <entry key ="xxxx" ref="xxxx"/><entry key ="xxxx"><ref value="xxxx"/>
 * </entry><entry key ="xxxx"><ref>xxxxx</ref></entry>
 *
 * <entry key ="xxxx" class="xxxx"/><entry key ="xxxx">
 * <class value="xxxx"/></entry><entry key ="xxxx"><class>xxxxx
 * </class></entry>
 *
 * <entry key ="xxxx"  value ="xxx"/><entry ref ="xxxx"  value="xxx"/>
 * <entry class="xxxx" value ="xxxx"/></map></parameter>
 *
 * <parameter>
 * <props>
 * <prop key ="xxxx"  value ="xxx"/>
 * <prop key ="xxxx"/>
 * <value="xxxxx"/></prop></props></parameter>
 *
 * <parameter>
 * <list><item value="xxx"/><item><value>xxxx</value></item>
 *
 * <item ref="xxx"/><item><ref value="xxxx"/></item><item><ref>xxx
 * </ref></item>
 *
 * <item class="xxxx"/><item><class value="xxx"/></item><item><class>
 * xxxx</class></item></list></parameter>
 * </constructor>
 */

public class InstanceCreationFinder implements  BeanElementXMLFinder{

	/**
	 * 查找Bean定义元素
	 */
	public BeanElement find(Element beanNode,String beanid,String spaceName,String file) throws BeanElementException{
		Element constructorNode = beanNode.getChild(XMLEelementTags.Constructor);
		if(constructorNode != null){//<constructor>  </constructor>
	    String factoryRefID = getFactoryRefID(beanNode);
	    String factoryMethod = getFactoryMethod(beanNode);
	    
	    Element[]argElements= BeanXMLNodeUtil.getChildren(constructorNode,XMLEelementTags.Constructor_arg);
			BeanParameter[] parameters = new BeanParameter[argElements.length];
	    try {
	    		for(int i=0;i<argElements.length;i++)
	    			parameters[i] = BeanParameterXMLFactory.find(spaceName,argElements[i]);
			} catch (BeanParameterException e) {
				throw new BeanElementException("Failed to find parameters for constructer of bean:"+beanid +" at file:"+file,e);
			}
	   
	    if(!StringUtil.isNull(factoryRefID) && !StringUtil.isNull(factoryMethod)){
	    	return new InstanceCreation(factoryRefID,factoryMethod,parameters);
	    }else if(!StringUtil.isNull(factoryMethod)){
	    	return new InstanceCreation(factoryMethod,parameters);
	    }else {
	    	return new InstanceCreation(parameters);
	    }
		}else{
			return null;
		}
  }

	/**
	 * find factory bean refID
	 */
	private static String getFactoryRefID(Element node){
	  Element parent = (Element)node.getParent();
	  String factoryBeanId = parent.getAttributeValue(XMLEelementTags.Factory_Bean);
	  
	  if(StringUtil.isNull(factoryBeanId))
	   factoryBeanId = node.getAttributeValue("ref");
	  
	  if(StringUtil.isNull(factoryBeanId))
	    factoryBeanId = parent.getChildText(XMLEelementTags.Factory_Bean);
	
	  return factoryBeanId;
	}
	
	/**
	 * find factory method
	 */
	private static String getFactoryMethod(Element node){
		Element parent = (Element)node.getParent();
	  String factoryMethod = parent.getAttributeValue(XMLEelementTags.Factory_Method);
	  
	  if(StringUtil.isNull(factoryMethod))
	    factoryMethod = parent.getChildText(XMLEelementTags.Factory_Method);
	 
	  return factoryMethod;
	}
}