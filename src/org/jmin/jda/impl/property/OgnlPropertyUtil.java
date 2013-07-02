/*
*Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.jda.impl.property;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ognl.NullHandler;
import ognl.Ognl;
import ognl.OgnlRuntime;

import org.jmin.jda.impl.cache.MapCache;

/**
 * OGNL 辅助类，主要用来获取对象属性或验证表达式
 *  
 * @author Chris
 */

public class OgnlPropertyUtil {

	/**
	 * 表达式缓存
	 */
	private static ThreadLocal expThreadLocal = new ThreadLocal();

	/**
	 * 属性操作识别
	 */
	private static ThreadLocal propertyOPLocal = new ThreadLocal();
	
	/**
	 * 注册默认的NUll Property的Handler
	 */
	static{OgnlRuntime.setNullHandler(Object.class,new PropertyNullHandlerProxy(propertyOPLocal));}

	/**
	 * 布尔表达式测定
	 */
	public static boolean assertBool(String expression,Object paramObj)throws Exception {
		try{
			propertyOPLocal.set("assert");
			return ((Boolean)Ognl.getValue(getOgnlExpression(expression),paramObj)).booleanValue();
		}finally{
			propertyOPLocal.set(null);
		}
	}
	
	/**
	 * 获取属性值
	 */
	public static Object getPropertyValue(Object bean, String propertyName)throws Exception {
		try{
			propertyOPLocal.set("getProperty");
			return Ognl.getValue(getOgnlExpression(propertyName),bean);
		}finally{
			propertyOPLocal.set(null);
		}
	}
	
	/**
	 * 设置属性值
	 */
	public static void setPropertyValue(Object bean, String propertyName,Object value) throws Exception {
		try{
			propertyOPLocal.set("setProperty");
		  Ognl.setValue(getOgnlExpression(propertyName),bean,value);
		}finally{
			propertyOPLocal.set(null);
		}
	}
	
	/**
	 * 获取解析表达式
	 */
	private static Object getOgnlExpression(String expression)throws Exception{
		MapCache expressionCache = (MapCache)expThreadLocal.get();
		if(expressionCache==null){
			expressionCache = new MapCache(500);
			expThreadLocal.set(expressionCache);
		}
		
		Object ognlExpression = expressionCache.get(expression);
		if(ognlExpression==null){
			ognlExpression = Ognl.parseExpression(expression);
			expressionCache.put(expression,ognlExpression);
		}
		return ognlExpression;
	}
}


class PropertyNullHandlerProxy implements NullHandler{
	private ThreadLocal propertyOPLocal;
	
	public PropertyNullHandlerProxy(ThreadLocal propertyOPLocal){
		this.propertyOPLocal =propertyOPLocal;
	}
	
	public Object nullPropertyValue(Map context,Object target,Object property){
  	Object propetyValue = null;
  	if("setProperty".equals(propertyOPLocal.get())){
    	try {
  		  String propertyName = property.toString();
  		  Class propertyType = this.getPropertyType(target.getClass(),propertyName);
  		  propetyValue = propertyType.newInstance();
  			Ognl.setValue(property.toString(), target, propetyValue);
  		} catch (Exception e) {
  			e.printStackTrace();
  		}
  	}
		return propetyValue;
  }
  
  public Object nullMethodResult(Map context, Object target, String methodName, Object[] args){
   return null;
  }
  
  /**
	 * 获得属性的类型
	 */
	private Class getPropertyType(Class parentClass, String propertyName)throws NoSuchFieldException {
		Class propertyType = null;
		if (Map.class.isAssignableFrom(parentClass)) {
			propertyType = parentClass;
		} else {
			Method[] methods = parentClass.getDeclaredMethods();
			String setter = "set" + propertyName.substring(0, 1).toUpperCase()+ propertyName.substring(1);
			for(int i = 0; i < methods.length; i++) {
				if(methods[i].getParameterTypes().length == 1 && setter.equals(methods[i].getName())) {
					propertyType = methods[i].getParameterTypes()[0];
					break;
				}
			}
		}
		
		if(propertyType == null){
			throw new NoSuchFieldException("Not found field["+propertyName+"] in class["+parentClass.getName()+"]");
		}else if(isAbstractClass(propertyType)){
	  	if(Map.class.isAssignableFrom(propertyType))
	  		propertyType = HashMap.class;
	  	else if(Set.class.isAssignableFrom(propertyType))
	  		propertyType = HashSet.class;
	  	else if(List.class.isAssignableFrom(propertyType))
	  		propertyType = ArrayList.class;
	  	else if(Collection.class.isAssignableFrom(propertyType))
	  		propertyType = ArrayList.class;
		}
		return propertyType;
	}

	/**
	 * 是否为抽象类
	 */
	private boolean isAbstractClass(Class clazz){
		return Modifier.isAbstract(clazz.getModifiers());
	}
}
