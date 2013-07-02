/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.impl.instance;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jmin.ioc.BeanException;
import org.jmin.ioc.BeanParameter;
import org.jmin.ioc.element.AutowiredType;
import org.jmin.ioc.element.InjectionField;
import org.jmin.ioc.element.InjectionInvocation;
import org.jmin.ioc.element.InjectionProperty;
import org.jmin.ioc.impl.converter.BeanTypeConvertFactory;
import org.jmin.ioc.impl.definition.BaseDefinition;
import org.jmin.ioc.impl.exception.BeanInvocationInjectException;
import org.jmin.ioc.impl.exception.BeanParentClassException;
import org.jmin.ioc.impl.exception.BeanPropertyInjectException;
import org.jmin.ioc.impl.util.ArrayUtil;
import org.jmin.ioc.impl.util.ObjectUtil;
import org.jmin.log.Logger;

/**
 * Bean实例注入工厂
 * 1：字段注入（自动注入和参数注入）
 * 2：属性注入
 * 3：方法调用注入工厂
 * 
 * @author Chris Liao
 * @version 1.0
 */

public class InstanceInjectFactory {
	
	/**
	 * 日记
	 */
	private static Logger logger =Logger.getLogger(InstanceInjectFactory.class);
	
	/**
	 * 实例执行注入,自动装载应该放置在最前，属性注射部分值定义部分为最后的值
	 */
	public Object injectInstance(Object instance,BaseDefinition definition,InstanceContainer container,Map bijectionMap)throws BeanException{
		Object beanId = definition.getBeanID();
		List injectFieldList = new ArrayList();
		List injectPropertyList = new ArrayList();
		resolveParentInjectPropertyList(definition,container,injectFieldList,injectPropertyList);
		
	  Collections.reverse(injectFieldList);
	  Collections.reverse(injectPropertyList);
	 
		Iterator itor1 = injectFieldList.iterator();
		Iterator itor2 = injectPropertyList.iterator();
		Iterator itor3 = definition.getInjectionInvocationList().iterator();
		
		while(itor1.hasNext())//字段
			injectField(beanId,instance,(InjectionField)itor1.next(),container,bijectionMap);//字段注入
		while(itor2.hasNext())//属性set
			injectProperty(beanId,instance,(InjectionProperty)itor2.next(),container,bijectionMap);//属性注入
		while(itor3.hasNext())//方法调用
			injectInvocation(beanId,instance,(InjectionInvocation)itor3.next(),container,bijectionMap);//方法注入
		
		return instance;
	}
	
  /**
   * 字段注入
   */
  private void injectField(Object beanId,Object instance,InjectionField injectField,InstanceContainer container,Map bijectionMap)throws BeanException{
		try {
			Field field =ObjectUtil.findField(instance.getClass(),injectField.getFieldName());
			Object paramValue=null;
			if(injectField.isAutowired()){
				AutowiredType autowiredType = injectField.getAutowiredType();
				if(AutowiredType.BY_NAME.equals(autowiredType)){//通过字段名装载
					paramValue = container.getBean(field.getName(),bijectionMap);
				}else{//通过类型名装载
					paramValue = container.getBeanByClass(field.getType(),bijectionMap);
				}
			}else{//通过参数装载
				BeanParameter param = injectField.getParameter();
				paramValue=ParamConvertFactory.convertParameter(container,param,bijectionMap);
			}
			
			if(paramValue!=null){//不为空才注入
				if(field!=null){
					boolean isAccessible=field.isAccessible();
					try{
				    field.setAccessible(true);
					  BeanTypeConvertFactory convertFact=container.getBeanTypeConvertFactory();
					  Object fieldValue=ObjectUtil.convertValue(paramValue,field.getType(),convertFact);
					
					  field.set(instance, fieldValue);
					  logger.debug( beanId,"Auto injected field["+field.getName()+"]with value:"+fieldValue);
					}finally{
						field.setAccessible(isAccessible);
					}
				}else{
					if(Collection.class.isInstance(instance) && Collection.class.isInstance(paramValue)){//collection
						Collection souceBean =(Collection)instance;
						Collection collParam =(Collection)paramValue;
						souceBean.addAll(collParam);
					}else if(Map.class.isInstance(instance) && Map.class.isInstance(paramValue)){//Map
						Map souceBean =(Map)instance;
						Map mapParam =(Map)paramValue;
						souceBean.putAll(mapParam);
					}else if(ArrayUtil.isArray(instance)){//数组
					 if(ArrayUtil.isArray(paramValue) && ArrayUtil.getArrayType(instance).isAssignableFrom(ArrayUtil.getArrayType(paramValue))){
						 int len1 = ArrayUtil.getArraySize(instance); 
						 int len2 = ArrayUtil.getArraySize(paramValue); 
						 int len = Math.min(len1,len2);
						 for(int i=0;i<len;i++){
							 ArrayUtil.setObject(instance,i,ArrayUtil.getObject(paramValue,i));
						 }
					 }else{
						 throw new BeanException("Bean("+ beanId+") - Not found field["+injectField.getFieldName()+"] in bean["+beanId+"]");
					 }
					}else{
						 throw new BeanException("Bean("+ beanId+") - Not found field["+injectField.getFieldName()+"] in bean["+beanId+"]");
					}
				}
			}
	  }catch(BeanException e){
    	throw e;
    } catch(Exception e) {
			throw new BeanPropertyInjectException("Bean("+ beanId+") - Failed to inject field[" +injectField.getFieldName() +"]",e);
		}
  }

  /**
   * 属性注入
   */
  private void injectProperty(Object beanId,Object instance,InjectionProperty property,InstanceContainer container,Map bijectionMap)throws BeanException {
  	try {
			BeanParameter param = property.getParameter();
			String methodName=getPropertySetMethodName(property.getPropertyName());
			Object paramValue=ParamConvertFactory.convertParameter(container,param,bijectionMap);
	
			if(paramValue!=null){//不为空才注入
				try {
					ObjectUtil.invokeMethod(instance,instance.getClass(),methodName,new Object[]{paramValue},container.getBeanTypeConvertFactory());
					logger.debug(beanId,"Injected property["+property.getPropertyName()+"]with value:"+paramValue);
				}catch (NoSuchMethodException e) {
					if(Collection.class.isInstance(instance) && Collection.class.isInstance(paramValue)){//collection
						Collection souceBean =(Collection)instance;
						Collection collParam =(Collection)paramValue;
						souceBean.addAll(collParam);
					}else if(Map.class.isInstance(instance) && Map.class.isInstance(paramValue)){//Map
						Map souceBean =(Map)instance;
						Map mapParam =(Map)paramValue;
						souceBean.putAll(mapParam);
					}else if(ArrayUtil.isArray(instance)){//数组
					 if(ArrayUtil.isArray(paramValue) && ArrayUtil.getArrayType(instance).isAssignableFrom(ArrayUtil.getArrayType(paramValue))){
						 int len1 = ArrayUtil.getArraySize(instance); 
						 int len2 = ArrayUtil.getArraySize(paramValue); 
						 int len = Math.min(len1,len2);
						 for(int i=0;i<len;i++){
							 ArrayUtil.setObject(instance,i,ArrayUtil.getObject(paramValue,i));
						 }
					 }else{
						 throw e;
					 }
					}else{
						throw e;
					}
				}
			}
    }catch(BeanException e){
    	throw e;
    }catch(InvocationTargetException e){
    	throw new BeanPropertyInjectException("Bean("+ beanId+") - Failed to inject property[" + property.getPropertyName() +"]",e.getTargetException());
    } catch(Exception e) {
			throw new BeanPropertyInjectException("Bean("+ beanId+") - Failed to inject property[" + property.getPropertyName() +"]",e);
		}
  }
 
  /**
   * 方法调用注入
   */
  private void injectInvocation(Object beanId,Object instance,InjectionInvocation invocation,InstanceContainer container,Map tempMap)throws BeanException {
    try {
    	String methodName = invocation.getMethodName();
      BeanParameter[] parameters = invocation.getParameters();
      Object[] paramValues = ParamConvertFactory.convertParameters(container,parameters,tempMap);
			ObjectUtil.invokeMethod(instance,instance.getClass(),methodName,paramValues,container.getBeanTypeConvertFactory());
 
      logger.debug(beanId,"Injected invocation["+invocation.getMethodName()+"]with values:"+paramValues);
    }catch(BeanException e){
    	throw e;
    }catch(InvocationTargetException e){
    	throw new BeanInvocationInjectException("Bean("+ beanId+") - Failed to inject method[" + invocation.getMethodName() +"]",e.getTargetException());
    }catch(Exception e){
    	throw new BeanInvocationInjectException("Bean("+ beanId+") - Failed to inject method[" + invocation.getMethodName() +"]",e);
    }
  }
  
  /**
   * return property set method name
   */
  private String getPropertySetMethodName(String name) {
    String firstChar = name.substring(0,1);
    String lastString = name.substring(1,name.length());
    return "set" + firstChar.toUpperCase() + lastString;
  }
  
  /**
   * 义查找父类定义的自动装载属性和注射属性
   */
  private void resolveParentInjectPropertyList(BaseDefinition definition,InstanceContainer container,List fieldList,List propertyList) throws BeanException {
		Object beanID = definition.getBeanID();
		Class beanClass = definition.getBeanClass();
		Iterator autowireItor = definition.getInjectionFieldList().iterator();
		Iterator propertyItor = definition.getInjectionPropertyList().iterator();
		
		while(autowireItor.hasNext()){
			Object element = autowireItor.next();
			if(!fieldList.contains(element))
				fieldList.add(element);
		}
		
		while(propertyItor.hasNext()){
			Object element = propertyItor.next();
			if(!propertyList.contains(element))
				propertyList.add(element);
		}
	
		Object parentID = definition.getParentReferenceId();
		if (parentID != null) {
			BaseDefinition parentDefinition = (BaseDefinition)container.getBeanDefinition(parentID);
			Class parentClass = parentDefinition.getBeanClass();
			if (!parentClass.isAssignableFrom(beanClass)) {
				throw new BeanParentClassException("Bean id("+parentDefinition.getBeanID()+")class["+parentDefinition.getBeanClass() + "]is not a parent for bean class["+beanClass+ "] at id:" +beanID);
			}
			resolveParentInjectPropertyList(parentDefinition, container,fieldList,propertyList);
		}
	}
}
