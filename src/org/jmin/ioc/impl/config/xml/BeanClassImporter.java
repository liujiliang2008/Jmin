/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.impl.config.xml;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.jmin.ioc.BeanContainer;
import org.jmin.ioc.BeanElement;
import org.jmin.ioc.BeanElementException;
import org.jmin.ioc.BeanException;
import org.jmin.ioc.BeanParameter;
import org.jmin.ioc.BeanParameterException;
import org.jmin.ioc.element.AutowiredType;
import org.jmin.ioc.element.DestroyMethodName;
import org.jmin.ioc.element.InitializeMethodName;
import org.jmin.ioc.element.InjectionField;
import org.jmin.ioc.element.InjectionInvocation;
import org.jmin.ioc.element.InstanceCreation;
import org.jmin.ioc.element.InstanceSingleton;
import org.jmin.ioc.element.InvocationInterception;
import org.jmin.ioc.element.ParentReferenceId;
import org.jmin.ioc.element.ProxyInterfaces;
import org.jmin.ioc.element.annotation.Autowired;
import org.jmin.ioc.element.annotation.Bean;
import org.jmin.ioc.element.annotation.Creation;
import org.jmin.ioc.element.annotation.Interceptor;
import org.jmin.ioc.element.annotation.Parameter;
import org.jmin.ioc.element.annotation.Parameters;
import org.jmin.ioc.element.annotation.ProxyClasses;
import org.jmin.ioc.impl.util.ClassUtil;
import org.jmin.ioc.impl.util.StringUtil;
import org.jmin.ioc.parameter.ClassParameter;
import org.jmin.ioc.parameter.InstanceParameter;
import org.jmin.ioc.parameter.ReferenceParameter;

/**
 * 获取类的注解,并导入容器之中
 * 
 * @author Liao
 */
public class BeanClassImporter {
	
	//注册类
	public static void register(Class beanClass,BeanContainer container)throws BeanException{
		Bean beanInfo =(Bean) beanClass.getAnnotation(Bean.class);
		if(beanInfo!=null){
			String beanId=beanInfo.id();
			if(StringUtil.isNull(beanId))
				beanId=ClassUtil.getClassShortName(beanClass);
			
			List elementList=new ArrayList();
			resolveClass(beanId,beanClass,elementList);
			resolveField(beanId,beanClass,elementList);
			resolveMethod(beanId,beanClass,elementList);
			resolveInterceptor(beanId,beanClass,elementList);
			container.registerClass(beanId,beanClass,(BeanElement[])elementList.toArray(new BeanElement[0]));
		}
	}
	
	//解析类
	private static void resolveClass(String beanid,Class beanClass,List elements)throws BeanException{
		Bean beanInfo =(Bean) beanClass.getAnnotation(Bean.class);
		String parentId = beanInfo.parentId();
		boolean singleton =beanInfo.singleton();
	
		String initMethod     = beanInfo.initMethod();
		String destroyMethod  = beanInfo.destroyMethod();

		elements.add(new InstanceSingleton(singleton));
		if(!StringUtil.isNull(parentId))
		  elements.add(new ParentReferenceId(parentId));
		if(!StringUtil.isNull(initMethod))
			 elements.add(new InitializeMethodName(initMethod));
		if(!StringUtil.isNull(destroyMethod))
			 elements.add(new DestroyMethodName(destroyMethod));
		
		//解析创建方式
		Creation creationInfo =(Creation) beanClass.getAnnotation(Creation.class);
		if(creationInfo!=null){
			String[] paramTxtArry = creationInfo.parameters();
			String factoryBean = creationInfo.factoryBean();
			String factoryMethod = creationInfo.factoryMethod();
	
			BeanParameter[] beanParameters=new BeanParameter[(paramTxtArry==null)?0:paramTxtArry.length];
      for(int i=0;i<beanParameters.length;i++){
        try {
					beanParameters[i]=convertToBeanParameter(paramTxtArry[i]);
				} catch (BeanParameterException e) {
					throw new BeanException("[Annotation] Parameter error of Constructor in bean class("+ beanClass.getName()+")",e);
				} catch (ClassNotFoundException e) {
					throw new BeanException("[Annotation] Parameter error of Constructor in bean class("+ beanClass.getName()+")",e);
				}
      }
      
      if(!StringUtil.isNull(factoryBean) && !StringUtil.isNull(factoryMethod)){
      	elements.add(new InstanceCreation(factoryBean,factoryMethod,beanParameters));
      }else if(!StringUtil.isNull(factoryMethod)){
      	elements.add(new InstanceCreation(factoryMethod,beanParameters));
      }else {
      	elements.add(new InstanceCreation(beanParameters));
      }
		}
		
		//解析代理类
		ProxyClasses proxyInfo =(ProxyClasses)beanClass.getAnnotation(ProxyClasses.class);
		if(proxyInfo!=null){
			String[] proxyClassNames = proxyInfo.interfaces();
			Class[] proxyClasses=new Class[(proxyClassNames==null)?0:proxyClassNames.length];
		   for(int i=0;i<proxyClassNames.length;i++){
		  	 try {
					proxyClasses[i] = ClassUtil.loadClass(proxyClassNames[i]);
				} catch (ClassNotFoundException e) {
					throw new BeanException("[Annotation] Not found proxy interface("+proxyClassNames[i]+")in bean class("+ beanClass.getName()+")");
				}
		   }
		   
		  if(proxyClasses!=null && proxyClasses.length >0){
		  	elements.add(new ProxyInterfaces(proxyClasses));
		  }
		}
	}

	//解析字段
	private static void resolveField(String beanid,Class beanClass,List elements)throws BeanException{
		Field[] fields = beanClass.getDeclaredFields();
		for(int i=0;i<fields.length;i++){
			Field field = fields[i];
			Autowired autowired=(Autowired)field.getAnnotation(Autowired.class);
			Parameter parameterInfo =(Parameter)field.getAnnotation(Parameter.class);
			
			if(autowired!=null){
				String type = autowired.type();
				 if(!type.equalsIgnoreCase("ByName") && !type.equalsIgnoreCase("ByType"))
		  		 throw new BeanElementException("[Annotation] Invalid autowired field("+field.getName()+")in bean class("+ beanClass.getName()+"),it must be one of{\"ByName\",\"ByType\"}");
		
				 elements.add(new InjectionField(field.getName(),AutowiredType.toAutowiredType(type)));
			}else if(parameterInfo!=null){
				try {
					String text = parameterInfo.value();
					BeanParameter iocParam=convertToBeanParameter(text);
					elements.add(new InjectionField(field.getName(),iocParam));
				} catch (BeanParameterException e) {
					throw new BeanException("[Annotation] Parameter error of field("+field.getName()+")in bean class("+ beanClass.getName()+")",e);
				} catch (ClassNotFoundException e) {
					throw new BeanException("[Annotation] Parameter error oof field("+field.getName()+")in bean class("+ beanClass.getName()+")",e);
				}
			}
		}
	}
	
	//解析方法
	private static void resolveMethod(String beanid,Class beanClass,List elements)throws BeanException{
		Method[] methods = beanClass.getDeclaredMethods();
		for(int i=0;i<methods.length;i++){
			Method method = methods[i];
			Parameter param =(Parameter)method.getAnnotation(Parameter.class);
			Parameters params =(Parameters)method.getAnnotation(Parameters.class);
			if(params!=null){//多个参数的方法
				String[] parameters=params.values();
				if(parameters.length!=method.getParameterTypes().length)
					 throw new BeanParameterException("[Annotation] Parameters count can't match method ["+method.getName()+"]in bean class("+ beanClass.getName()+")");
				
				BeanParameter[] beanParameters=new BeanParameter[(parameters==null)?0:parameters.length];
	      for(int j=0;j<beanParameters.length;j++){
	      	try{
	          beanParameters[j]=convertToBeanParameter(parameters[j]);
	      	} catch (BeanParameterException e) {
						throw new BeanException("[Annotation] Parameter error of method("+method.getName()+")in bean class("+ beanClass.getName()+")",e);
					} catch (ClassNotFoundException e) {
						throw new BeanException("[Annotation] Parameter error oof method("+method.getName()+")in bean class("+ beanClass.getName()+")",e);
					}
	      }
	    	elements.add(new InjectionInvocation(method.getName(),beanParameters));
			}else if(param!=null){//单个参数的方法
				if((StringUtil.isNull(param.value()) && method.getParameterTypes().length!=0) || 
				 (!StringUtil.isNull(param.value()) && method.getParameterTypes().length!=1)){
					 throw new BeanParameterException("[Annotation] Parameters count can't match method("+method.getName()+")in bean class("+ beanClass.getName()+")");
				 }
				
				 BeanParameter[] beanParameters=new BeanParameter[(StringUtil.isNull(param.value()))?0:1];
				 if(beanParameters.length==1){
					try{
					   beanParameters[0]=convertToBeanParameter(param.value());
		         elements.add(new InjectionInvocation(method.getName(),beanParameters));
					  } catch (BeanParameterException e) {
							throw new BeanException("[Annotation] Parameter error of method("+method.getName()+")in bean class("+ beanClass.getName()+")",e);
					  } catch (ClassNotFoundException e) {
							throw new BeanException("[Annotation] Parameter error oof method("+method.getName()+")in bean class("+ beanClass.getName()+")",e);
					  }
		      } 
			 }
	   }
	}
	
	
	//解析拦截器
	private static void resolveInterceptor(String beanid,Class beanClass,List elements)throws BeanException{
		Method[] methods = beanClass.getDeclaredMethods();
		for(int i=0;i<methods.length;i++){
			Method method = methods[i];
			Interceptor interceptorInfo = method.getAnnotation(Interceptor.class);
			if(interceptorInfo!=null){
			  String text = interceptorInfo.value();
			  BeanParameter param =null;
				try {
					param = convertToBeanParameter(text);
				} catch (BeanParameterException e) {
					throw new BeanParameterException("[Annotation] Interceptor error,method ("+method.getName()+")in bean class("+ beanClass.getName()+")");
				} catch (ClassNotFoundException e) {
					throw new BeanParameterException("[Annotation] Interceptor error,method ("+method.getName()+")in bean class("+ beanClass.getName()+")");
				}
			 
			  InvocationInterception interception =new InvocationInterception(method.getName(),method.getParameterTypes());
			  if(param instanceof ClassParameter){
			  	ClassParameter classParameter=(ClassParameter)param;
			  	interception.addInterceptorClass((Class)classParameter.getParameterContent());
			  }else if(param instanceof ReferenceParameter){
			  	ReferenceParameter referenceParameter=(ReferenceParameter)param;
			  	interception.addInterceptorReference(referenceParameter.getParameterContent());
			  }else if(param instanceof InstanceParameter){
			  	throw new BeanParameterException("[Annotation] Interceptor value must be start with \"ref:\" or \"class:\",method ("+method.getName()+")in bean class("+ beanClass.getName()+")");
			  }  
			}
		}
	}

	//将文本转换为Ioc参数
	private static BeanParameter convertToBeanParameter(String text) throws ClassNotFoundException,BeanParameterException{
			int pos = text.indexOf(":");
			if(pos>0){
				String prefix = text.substring(0,pos).trim();
				String suffix = text.substring(pos+1).trim();
				
	     if(prefix.equalsIgnoreCase("class")){
				 Class clazz =ClassUtil.loadClass(suffix);
				 return new ClassParameter(clazz);
	     }else if(prefix.equalsIgnoreCase("val") || prefix.equalsIgnoreCase("value")){
					return new InstanceParameter(suffix);
			 }else if(prefix.equalsIgnoreCase("ref") || prefix.equalsIgnoreCase("reference")){
				  return new ReferenceParameter(suffix);
			 }else {
					return new InstanceParameter(text);
			 }
			}else{
				return new InstanceParameter(text);
			}
	 }
}
