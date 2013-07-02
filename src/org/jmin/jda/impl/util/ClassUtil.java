/*
*Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A class helper
 *
 * @author Chris Liao
 * @version 1.0
 */

public class ClassUtil {
	
	/**
	 * 存放静态基本
	 */
  private static Map classMap = new HashMap();
  
	/**
	 * 存放抽象的子类实现
	 */
	private static Map subClassMap = new HashMap();
  
	/**
	 * 存放基本类型到包装的映射
	 */
	private static Map classToWrapper = new HashMap();

  static{
  	classMap.put("bool",boolean.class);
  	classMap.put("boolean",boolean.class);
  	classMap.put("byte",byte.class);
  	classMap.put("short",short.class);
  	classMap.put("char",char.class);
  	classMap.put("int",int.class);
  	classMap.put("long",long.class);
   	classMap.put("float",float.class);
  	classMap.put("double",double.class);
  	classMap.put("string",String.class);
  	classMap.put("map",HashMap.class);
  	classMap.put("hashmap",HashMap.class);
  	classMap.put("hashtable",Hashtable.class);
  	
  	classMap.put("list",ArrayList.class);
  	classMap.put("arraylist",ArrayList.class);
  	classMap.put("linkedlist",LinkedList.class);
  	
  	classToWrapper.put(Boolean.TYPE, Boolean.class);
  	classToWrapper.put(Byte.TYPE, Byte.class);
  	classToWrapper.put(Short.TYPE, Short.class);
  	classToWrapper.put(Character.TYPE, Character.class);
  	classToWrapper.put(Integer.TYPE, Integer.class);
  	classToWrapper.put(Long.TYPE, Long.class);
  	classToWrapper.put(Float.TYPE, Float.class);
  	classToWrapper.put(Double.TYPE, Double.class);
  	
  	subClassMap.put(Map.class,HashMap.class);
  	subClassMap.put(Set.class,HashSet.class);
  	subClassMap.put(List.class,ArrayList.class);
  	subClassMap.put(Collection.class,ArrayList.class);  	
  }

	/**
	 * 是否为抽象类
	 */
	public static boolean isAbstractClass(Class clazz){
		return !isPrimitiveClass(clazz) && Modifier.isAbstract(clazz.getModifiers());
	}
	
	/**
	 * 是否为公开类
	 */
	public static boolean isPublicClass(Class clazz){
		return Modifier.isPublic(clazz.getModifiers());
	}
	
	/**
	 * 是否为Final类
	 */
	public static boolean isFinalClass(Class clazz){
		return Modifier.isFinal(clazz.getModifiers());
	}

	/**
	 * 是否为公开类
	 */
	public static boolean isPrimitiveClass(Class clazz){
		return clazz.isPrimitive();
	}
	
	/**
	 * 是否为公开类
	 */
	public static boolean isPrimitiveClass(String clazz){
		if(clazz!=null)
			return classMap.containsKey(clazz.trim().toLowerCase());
		else
			return false;
	}

	/**
	 * 是否无参数构造函数
	 */
	public static boolean existDefaultConstructor(Class clazz){
		boolean exist=false;
		if(!isPrimitiveClass(clazz)){
			Constructor[] constructos = clazz.getConstructors();
			for(int i=0;i<constructos.length;i++){
				if(constructos[i].getParameterTypes().length == 0){
					exist=true;
					break;
				}
			}
		}else{
			exist=true;
		}
		return exist;
	}
	
  /**
   * Load a class
   */
  public static Class loadClass(String clsName) throws ClassNotFoundException {
    return loadClass(clsName,true,ClassUtil.class.getClassLoader());
  }
  
  /**
   * Load a class
   */
  public static Class loadClass(String clsName,ClassLoader classLoader)throws ClassNotFoundException {
    return loadClass(clsName,true,classLoader);
  }
  
  /**
   * Load a class by name
   */
  public static Class loadClass(String name, boolean initialize, ClassLoader loader)throws ClassNotFoundException {
    if(classMap.containsKey(name.trim().toLowerCase())){
    	return (Class)classMap.get(name.trim().toLowerCase());
    }else{
    	return Class.forName(name,initialize, loader);
    }
  }
 
	/**
	 * Check object to match class type
	 */
	public static boolean isAcceptableInstance(Class type, Object instance) {
		if (type.isInstance(instance)) {
			return true;
		}else if (type.isPrimitive()) {
			Class wrapClass =(Class)classToWrapper.get(type);
			return wrapClass!= null && wrapClass.isInstance(instance);
		}else {
			return false;
		}
	}	
 
	/**
	 * 创建结果对象
	 */
	public static Object createInstance(String classname)throws ClassNotFoundException,InstantiationException, IllegalAccessException{
		return createInstance(loadClass(classname));
	}
	
	/**
	 * 创建结果对象
	 */
	public static Object createInstance(Class clazz)throws InstantiationException, IllegalAccessException{
		if(ClassUtil.isAbstractClass(clazz)){
			Class subClass =(Class)subClassMap.get(clazz);
			if(subClass!=null)
				return subClass.newInstance();
			else
				throw new InstantiationException("Not found configed sub class for abstract class:"+clazz.getName());
		}else{
			return clazz.newInstance();
		}
	}
}