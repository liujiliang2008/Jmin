/*
*Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.ioc.impl.instance;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jmin.ioc.BeanException;
import org.jmin.ioc.BeanParameter;
import org.jmin.ioc.element.InstanceCreation;
import org.jmin.ioc.element.InvocationInterception;
import org.jmin.ioc.impl.converter.BeanTypeConvertFactory;
import org.jmin.ioc.impl.definition.BaseDefinition;
import org.jmin.ioc.impl.definition.ClassDefinition;
import org.jmin.ioc.impl.definition.InstanceDefinition;
import org.jmin.ioc.impl.exception.BeanClassException;
import org.jmin.ioc.impl.exception.BeanClassModifiedException;
import org.jmin.ioc.impl.exception.BeanInstanceCreateException;
import org.jmin.ioc.impl.exception.BeanInstanceInitException;
import org.jmin.ioc.impl.intercept.ClassWrapEditor;
import org.jmin.ioc.impl.intercept.InterceptorChain;
import org.jmin.ioc.impl.util.ClassUtil;
import org.jmin.ioc.impl.util.ObjectUtil;
import org.jmin.ioc.impl.util.StringUtil;
import org.jmin.log.Logger;

/**
 * 对象实例工厂
 * 
 * @author chris liao
 */
public class InstanceCreateFactory {
	
	/**
	 * 日记
	 */
	private static Logger logger = Logger.getLogger(InstanceCreateFactory.class);
	
	/**
	 * 创建对象实例
	 */
	public Object createInstance(InstanceContainer container,BaseDefinition definition,Map tempMap)throws BeanException{
		try {
			Object beanInstance = null;
			if(definition instanceof InstanceDefinition){
				InstanceDefinition instanceDefinition =(InstanceDefinition)definition;
				beanInstance = instanceDefinition.getSingletonInstance();//原始的注册值
			}else{
				ClassDefinition classDefinition =(ClassDefinition)definition;
				if(needModifyClassForInterception(classDefinition)){//是否需要修改类
					modifyBeanClassForInterception(classDefinition);
				}
				
				Class beanClass = classDefinition.getBeanClass();
				if(classDefinition.getBeanWrapClass()!=null)
					beanClass = classDefinition.getBeanWrapClass();
				
				InstanceCreation creation = classDefinition.getInstanceCreation();
				Object factoryBeanRefID = creation.getFactoryBeanRefID();
				String factoryMethodName = creation.getFactoryMethodName();
				BeanParameter[]parameters = creation.getCreateParameters();
				Object[] paramValues = ParamConvertFactory.convertParameters(container,parameters,tempMap);
				
			  if(factoryBeanRefID!=null){
					Object factoryBean = container.getBean(factoryBeanRefID,tempMap);
					beanInstance = createByFactoryBean(factoryBean,factoryMethodName,paramValues,container.getBeanTypeConvertFactory());
				}else if(!StringUtil.isNull(factoryMethodName)){
					beanInstance = createByFactoryMethod(beanClass,factoryMethodName,paramValues,container.getBeanTypeConvertFactory());
				}else{
					beanInstance = createByReflection(beanClass,paramValues,container.getBeanTypeConvertFactory());
				}
				
				if(beanInstance!=null)
					initBeanInstance(definition.getBeanID(),beanInstance,definition.getInitMethod());
				logger.debug(definition.getBeanID(),"Created a instance with class:"+beanClass.getName());
			}
			
			return beanInstance;
		} catch (BeanException e) {
			throw new BeanInstanceCreateException("Failed to create instance for class["+definition.getBeanClass()+"] at id:" + definition.getBeanID(),e.getCauseException() );
		}
	}
	
	/**
	 * 工厂bean创建实例
	 */
	private Object createByFactoryBean(Object factoryBean,String factoryMethod,Object[]paramValues,BeanTypeConvertFactory convertFactory)throws BeanException{
		try {
			return ObjectUtil.invokeMethod(factoryBean,factoryBean.getClass(),factoryMethod,paramValues,convertFactory);
		}catch(InvocationTargetException e){
			throw new BeanException(e.getTargetException());
		} catch (Exception e) {
			throw new BeanException(e);
		} 
	}
	
	/**
	 *工厂方法创建实例
	 */
	private Object createByFactoryMethod(Class beanClass,String factoryMethod,Object[]paramValues,BeanTypeConvertFactory convertFactory)throws BeanException{
		try {
			return ObjectUtil.invokeMethod(null,beanClass,factoryMethod,paramValues,convertFactory);
		}catch(InvocationTargetException e){
			throw new BeanException(e.getTargetException());
		} catch (Exception e) {
			throw new BeanException(e);
		} 
	}

	/**
	 * 反射方法创建实例
	 */
	private Object createByReflection(Class beanClass,Object[]paramValues,BeanTypeConvertFactory convertFactory)throws BeanException{
		try {
			return ObjectUtil.createInstance(beanClass,paramValues,convertFactory);
		}catch(InvocationTargetException e){
			throw new BeanException(e.getTargetException());
		} catch (Exception e) {
			throw new BeanException(e);
		} 
	}
	
	/*************************************私有方法*************************************************/
	/**
	 * 检查是否需要编辑类以
	 */
	private boolean needModifyClassForInterception(ClassDefinition definition)throws BeanException{
		if(!definition.isInstaceCreated()&&!ClassUtil.isAbstractClass(definition.getBeanClass()) && definition.getBeanWrapClass() ==null){
			Object beanid = definition.getBeanID();
			Class[] ProxyInterfaces = definition.getProxyInterfaces();
			InstanceCreation creation = definition.getInstanceCreation();
			List interceptList = definition.getInvocationInterceptionList();
			InterceptorChain interceptorChain = definition.getInterceptorChain();
			
			if(creation!=null){
		  	if(creation.getFactoryBeanRefID()!=null || !StringUtil.isNull(creation.getFactoryMethodName())){
		  		if(!interceptList.isEmpty() && ProxyInterfaces ==null){
		  			logger.warn(beanid,"Interception will be disabled,not support factory-bean/factory-method without proxy intefaces");
		  			return false;
		  		}
		  	}
		  }
		
			if(!interceptList.isEmpty()&& (ProxyInterfaces == null || ProxyInterfaces.length==0)&& interceptorChain == null){
				if(ClassUtil.isFinalClass(definition.getBeanClass()))
					throw new BeanClassException("Intercepted bean("+beanid+")class["+definition.getBeanClass()+"]can't be final type without proxy intefaces");
				else
				  return true;
			}else
				return false;
		 }else{
			 return false;
		 }
	}
	
	/**
	 * 修改类,为拦截做准备
	 */
	private void modifyBeanClassForInterception(ClassDefinition definition)throws BeanException{
		try {
			List interceptionList = definition.getInvocationInterceptionList();
			Method[] interceptedMethods = new Method[interceptionList.size()];
	 
			int index=0;
			Iterator itor = interceptionList.iterator();
			while(itor.hasNext()){
				InvocationInterception interception =(InvocationInterception)itor.next();
				interceptedMethods[index++]= ObjectUtil.findMethod(
									definition.getBeanClass(),
									interception.getMethodName(),
									interception.getMethodParamTypes());
			}
			
			Class wrapClass = ClassWrapEditor.createWrapClass(definition.getBeanClass(),interceptedMethods);
			definition.setBeanWrapClass(wrapClass);
			definition.setClassModified(true);
			logger.debug(definition.getBeanID(),"Created AOP wrapper class:"+wrapClass.getName());
		} catch (Exception e) { 
			throw new BeanClassModifiedException(e);
		}
	}
	
	/**
	 * 调用初始化方法完成
	 */
	private void initBeanInstance(Object beanId,Object instance,Method initMethod)throws BeanException{
		if(initMethod!=null){
			try {
				initMethod.invoke(instance,new Object[0]);
				logger.debug(beanId,"Inited instance:"+instance);
			} catch (InvocationTargetException e) {
				throw new BeanInstanceInitException("Bean("+ beanId +") - Failed to init instance",e.getTargetException());
			}catch (Exception e) {
				throw new BeanInstanceInitException("Bean("+ beanId +") - Failed to init instance",e);
			}
		}
	}
}
