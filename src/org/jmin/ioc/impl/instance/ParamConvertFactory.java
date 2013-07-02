/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.impl.instance;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.jmin.ioc.BeanException;
import org.jmin.ioc.BeanParameter;
import org.jmin.ioc.BeanParameterException;
import org.jmin.ioc.impl.converter.BeanTypeConvertFactory;
import org.jmin.ioc.impl.util.ArrayUtil;
import org.jmin.ioc.impl.util.ClassUtil;
import org.jmin.ioc.impl.util.ObjectUtil;
import org.jmin.ioc.parameter.ClassParameter;
import org.jmin.ioc.parameter.ContainerParameter;
import org.jmin.ioc.parameter.InstanceParameter;
import org.jmin.ioc.parameter.PrimitiveParameter;
import org.jmin.ioc.parameter.ReferenceParameter;

/**
 * 将参数定义转化为真实的对象值
 * 
 * @author Chris Liao
 * @version 1.0
 */

public final class ParamConvertFactory {

	/**
	 * 将参数定义转化为真实的对象值
	 */
	public static Object convertParameter(InstanceContainer container,BeanParameter parameter,Map tempMap) throws BeanException {
		return convertParamToValue(container, parameter, tempMap);
	}

	/**
	 * 将参数定义转化为真实的对象值
	 */
	public static Object[] convertParameters(InstanceContainer container,BeanParameter[] parameters, Map tempMap) throws BeanException {
		Object[] paramValues = new Object[parameters == null ? 0: parameters.length];
		for (int i = 0; i < paramValues.length; i++)
			paramValues[i] = convertParamToValue(container, parameters[i], tempMap);
		
		return paramValues;
	}

	/**
	 * 真实执行转换的内部方法
	 */
	private static Object convertParamToValue(InstanceContainer container,BeanParameter param, Map tempMap) throws BeanException {
		Object paramValue = null;
		if (param instanceof PrimitiveParameter) {
			paramValue = param.getParameterContent();
		} else if (param instanceof InstanceParameter) {
			paramValue = param.getParameterContent();
		} else if (param instanceof ContainerParameter) {
			paramValue = param.getParameterContent();
		} else if (param instanceof ClassParameter) {
			Object desc = param.getParameterContent();
			paramValue = createClassParamValue(desc,container.getBeanTypeConvertFactory());
		} else if (param instanceof ReferenceParameter) {
			Object refKey = param.getParameterContent();
			if(tempMap != null && tempMap.containsKey(refKey)) 
				paramValue = tempMap.get(refKey);
			else
			 paramValue = container.getBean(refKey,tempMap);
		}

		/**
		 * if instance is a collection
		 */
		if (paramValue instanceof Collection) {
			paramValue = digCollectionParameter(container,(Collection)paramValue,tempMap);
		} else if (paramValue instanceof Map) {
			paramValue = digMapParameter(container, (Map)paramValue,tempMap);
		} else if (ArrayUtil.isArray(paramValue)){
			paramValue = digArrayParameter(container,paramValue,tempMap);
		}

		return paramValue;
	}

	/**
	 * search all value in a collection
	 */
	public static Collection digCollectionParameter(InstanceContainer container,Collection col,Map tempMap) throws BeanException {
		Collection valueCollection =(Collection)createContainerInstance(col.getClass());
		Iterator itor = col.iterator();
		while (itor.hasNext()) {
			Object value = itor.next();
			if(value instanceof BeanParameter) 
				value = convertParamToValue(container,(BeanParameter)value,tempMap);
			valueCollection.add(value);
		}
		return valueCollection;
	}

	/**
	 * search all value in a map
	 */
	public static Map digMapParameter(InstanceContainer container,Map map,Map tempMap) throws BeanException {
		Map valueMap =(Map)createContainerInstance(map.getClass());
		Iterator itor = map.entrySet().iterator();
		while (itor.hasNext()) {
			Map.Entry entry = (Map.Entry) itor.next();
			Object key = entry.getKey();
			Object value = entry.getValue();
			if (key instanceof BeanParameter) 
				key = convertParamToValue(container,(BeanParameter)key,tempMap);
			if (value instanceof BeanParameter) 
				value = convertParamToValue(container,(BeanParameter) value,tempMap);

			valueMap.put(key, value);
		}
		return valueMap;
	}

	/**
	 * search all value in a array
	 */
	public static Object digArrayParameter(InstanceContainer container,Object arry,Map tempMap) throws BeanException {
		int size = ArrayUtil.getArraySize(arry);
		Class type = ArrayUtil.getArrayType(arry);
		Object valueArray =ArrayUtil.createArray(type,size);
		BeanTypeConvertFactory convertFact=container.getBeanTypeConvertFactory();
	
		for (int i = 0; i < size; i++) {
			Object value = ArrayUtil.getObject(arry,i);
			if(value instanceof BeanParameter) {
				value = convertParamToValue(container, (BeanParameter) value,tempMap);
				ArrayUtil.setObject(valueArray,i,value);
			}else{
				ArrayUtil.setObject(valueArray,i,ObjectUtil.convertValue(value,type,convertFact));
			}
		}
		
		return valueArray;
	}

	/**
	 * 创建容器类型的参数
	 */
	private static Object createContainerInstance(Class containerClass) throws BeanException {
		try {
			return containerClass.newInstance();
		} catch (Exception e) {
			throw new BeanParameterException(e);
		}
	}
	
	/**
	 * 创建类参数值
	 */
	private static Object createClassParamValue(Object description,BeanTypeConvertFactory convertFactory)throws BeanException {
		try {
			if (description instanceof String) {
				return ObjectUtil.createInstance(ClassUtil.loadClass((String) description),convertFactory);
			} else{
				return ObjectUtil.createInstance((Class)description,convertFactory);
			} 
		} catch (Exception e) {
			throw new BeanParameterException(e);
		}
	}
}