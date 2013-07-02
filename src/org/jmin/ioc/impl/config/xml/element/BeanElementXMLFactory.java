/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.impl.config.xml.element;

import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;
import org.jmin.ioc.BeanElement;
import org.jmin.ioc.BeanException;
import org.jmin.ioc.element.InstanceCreation;
import org.jmin.ioc.impl.util.StringUtil;
import org.jmin.log.Logger;

/**
 * Bean Element Finder
 *
 * @author Chris Liao
 * @version 1.0
 */

public class BeanElementXMLFactory {
	
	/**
	* 日记打印
	*/
	private static final Logger log = Logger.getLogger(BeanElementXMLFactory.class);
	
	/**
	 * 单例摸是查找
	 */
	private static InstanceSingletonFinder singletonFinder = new InstanceSingletonFinder();
	
	/**
	 * 父亲ID摸查找
	 */
	private static ParentReferenceIdFinder parentIdFinder = new ParentReferenceIdFinder();
	
	/**
	 * 破坏方法查找
	 */
	private static DestroyMethodNameFinder destroyFinder = new DestroyMethodNameFinder();
	
	/**
	 * 初始化方法法查找
	 */
	private static InitializeMethodNameFinder initFinder = new InitializeMethodNameFinder();

	/**
	 * 构造方式法查找
	 */
	private static InstanceCreationFinder creationFinder = new InstanceCreationFinder();
	
	/**
	 * 代理接口查找
	 */
	private static ProxyInterfaceFinder proxyInterfaceFinder  = new ProxyInterfaceFinder();
	
	
	/**
	 * 注入字段查找
	 */
	private static InjectionFieldFinder fieldFinder  = new InjectionFieldFinder();

	/**
	 * 注入属性查找
	 */
	private static InjectionPropertyFinder propertyFinder = new InjectionPropertyFinder();
	
	/**
	 * 方法注入查找
	 */
	private static InjectionInvocationFinder invocationFinder = new InjectionInvocationFinder();
	
	/**
	 * 拦截查找
	 */
	private static InvocationInterceptionFinder interceptionFinder = new InvocationInterceptionFinder();
	

	/**
	 * 查找Bean XML节点,找出其元素
	 */
	public static BeanElement[] find(Element beanNode,String beanid,String spaceName,String file)throws BeanException{
		List list = new ArrayList();
		BeanElement parentid = parentIdFinder.find(beanNode,beanid,spaceName,file);
		if (parentid != null)
			list.add(parentid);
	
		BeanElement singleton = singletonFinder.find(beanNode,beanid,spaceName,file);
		if (singleton != null)
			list.add(singleton);

		BeanElement InitMethod = initFinder.find(beanNode,beanid,spaceName,file);
		if (InitMethod != null)
			list.add(InitMethod);
			
		BeanElement DestroyMethod = destroyFinder.find(beanNode,beanid,spaceName,file);
		if (DestroyMethod != null)
			list.add(DestroyMethod);
	
		BeanElement Constructor = creationFinder.find(beanNode,beanid,spaceName,file);
		if (Constructor != null)
			list.add(Constructor);
		
		BeanElement ProxyInterfaces = proxyInterfaceFinder.find(beanNode,beanid,spaceName,file);
		if (ProxyInterfaces != null)
			list.add(ProxyInterfaces);
		
		List fieldElementList = beanNode.getChildren(XMLEelementTags.Field);
	  for (int i = 0; i < fieldElementList.size(); i++) {
	  	Element autowireNode = (Element)fieldElementList.get(i);
	  	list.add(fieldFinder.find(autowireNode,beanid,spaceName,file));
	  }
		
		List propElementList = beanNode.getChildren(XMLEelementTags.Property);
	  for (int i = 0; i < propElementList.size(); i++) {
	  	Element propNode = (Element)propElementList.get(i);
	  	list.add(propertyFinder.find(propNode,beanid,spaceName,file));
	  }
	
	  List allInvocations = beanNode.getChildren(XMLEelementTags.Invocation);
	  for (int i = 0; i < allInvocations.size(); i++) {
	    Element invocationNode = (Element) allInvocations.get(i);
	    list.add(invocationFinder.find(invocationNode,beanid,spaceName,file));
	  }
	
	  List inteceptorList = beanNode.getChildren(XMLEelementTags.Interception);
	  for (int i = 0; i < inteceptorList.size(); i++) {
	    Element inteceptorNode = (Element) inteceptorList.get(i);
	    list.add(interceptionFinder.find(inteceptorNode,beanid,spaceName,file));
	  }
	  
	  if(Constructor!=null){
	  	InstanceCreation creation =(InstanceCreation)Constructor;
	  	if(creation.getFactoryBeanRefID()!=null || !StringUtil.isNull(creation.getFactoryMethodName())){
	  		if(!inteceptorList.isEmpty() && ProxyInterfaces ==null){
	  			log.warn(beanid,"Interception will be disabled,not support factory-bean/factory-method with zero proxy inteface at file:" +file);
	  		}
	  	}
	  }
	 
		return (BeanElement[])list.toArray(new BeanElement[0]);
	}
}
