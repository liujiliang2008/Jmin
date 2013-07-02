/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.property;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Collection;
import java.util.Map;

import org.jmin.jda.impl.util.ClassUtil;
import org.jmin.jda.impl.util.StringUtil;

/**
 * 属性信息辅助类
 * 
 * @author Chris
 * @version 1.0
 */

public class PropertyUtil {
	
	/**
	 * 通过解析get方法获得属性名
	 */
	public static String getPropertySetMethodName(String propertyName){
	 return "set" + propertyName.substring(0,1).toUpperCase()+ propertyName.substring(1);
	}
	
	/**
	 * 通过解析get方法获得属性名
	 */
	public static String getPropertyGetMethodName(String propertyName){
	  return "get" + propertyName.substring(0,1).toUpperCase()+ propertyName.substring(1);
	}

	/**
	 * 设置属性值
	 */
	public static Object getPropertyValue(Object bean,String propertyName)throws PropertyException{
		 try {
			 return OgnlPropertyUtil.getPropertyValue(bean,propertyName);
		 }catch(NullPointerException e){
			 return null;
		 } catch (Throwable e) {
			throw new PropertyException("Failed to get value for propety["+propertyName+"]on bean:["+bean+"]",e);
		}
	}
	
	/**
	 * 设置属性值
	 */
	public static void setPropertyValue(Object bean,String propertyName,Object value)throws PropertyException{
		 try {
			 OgnlPropertyUtil.setPropertyValue(bean,propertyName,value);
		} catch (Throwable e) {
			throw new PropertyException("Failed to set value for propety["+propertyName+"]on bean:["+bean+"]",e);
		}
	}
	
	/**
	 * 查找属性信息类
	 */
	public static Class getPropertyType(Class beanClass,String propertyName)throws PropertyException{
		if(beanClass==null)
			throw new PropertyException("Bean class can't be null");
		if(propertyName==null && propertyName.trim().length()==0)
			throw new PropertyException("Property name can't be null");
				
			Class parentClass=beanClass;
		  String[]subNames=StringUtil.split(propertyName,".");
		  for(int i=0;i<subNames.length;i++) {
				try {
					parentClass =getInternelPropertyType(beanClass,subNames[i]);
					if(parentClass == null)
						throw new PropertyException("Class:"+ beanClass.getName()+ ",Property["+PropertyUtil.getUnionName(subNames,0,i)+"]not found");

					if(i<subNames.length-1 && Map.class.isAssignableFrom(parentClass))
						throw new PropertyException("Class:"+ beanClass.getName()+ ",Property["+PropertyUtil.getUnionName(subNames,0,i)+"]is map,search broken");
					if(i<subNames.length-1 && Collection.class.isAssignableFrom(parentClass))
						throw new PropertyException("Class:"+ beanClass.getName()+ ",Property["+PropertyUtil.getUnionName(subNames,0,i)+"]is collection,search broken");
					
					if(i<subNames.length-1 && !Map.class.isAssignableFrom(parentClass)&& ClassUtil.isAbstractClass(parentClass))
						throw new PropertyException("Class:"+ beanClass.getName()+ ",Property["+PropertyUtil.getUnionName(subNames,0,i)+"]can't be abstract");
					if(i<subNames.length-1 && !ClassUtil.existDefaultConstructor(parentClass))
						throw new PropertyException("Class:"+ beanClass.getName()+ ",Property["+PropertyUtil.getUnionName(subNames,0,i)+"]missed default constructor");
				} catch (IntrospectionException e) {
					throw new PropertyException("Class:"+ beanClass.getName()+ " Property["+PropertyUtil.getUnionName(subNames,0,i)+"]not found",e);
				}
			}
		  
		 return parentClass;
	}
	
	/**
	* 拼凑字符串
	*/
	private static String getUnionName(String[]subNames,int startIndex,int endIndex){
		String[] names2 = new String[endIndex+1];
		System.arraycopy(subNames,0,names2,0,names2.length);
		return StringUtil.concat(names2,".");
	}
	
	/**
	* 查询出匹配的反射Field
	*/
	private static Class getInternelPropertyType(Class clazz,String fiedlName)throws IntrospectionException{
		Class propertyType =null;
		BeanInfo beanInfo = Introspector.getBeanInfo(clazz,Object.class);
		PropertyDescriptor[]descriptors = beanInfo.getPropertyDescriptors();
		for(int i=0;i<descriptors.length;i++){
			if(descriptors[i].getName().equals(fiedlName)){
				propertyType = descriptors[i].getPropertyType();
				break;
			}
		}
		return propertyType;
	}
}
