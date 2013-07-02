/*
*Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.impl.util;

import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
  private static Map ClassMap = new HashMap();
	
	/**
	 * 存放基本类型到包装的映射
	 */
	private static Map primitiveToWrapper = new HashMap();
	
	/**
	 * 存放包装类型到基本的映射
	 */
	private static Map wrapperToPrimitive = new HashMap();

	/**
	 * 映射构造
	 */
  static {
  	
  	ClassMap.put("bool",boolean.class);
  	ClassMap.put("boolean",boolean.class);
  	ClassMap.put("byte",byte.class);
  	ClassMap.put("short",short.class);
  	ClassMap.put("int",int.class);
  	ClassMap.put("long",long.class);
   	ClassMap.put("float",float.class);
  	ClassMap.put("double",double.class);
  	ClassMap.put("string",String.class);
 
  	primitiveToWrapper.put(Boolean.TYPE, Boolean.class);
  	primitiveToWrapper.put(Byte.TYPE, Byte.class);
  	primitiveToWrapper.put(Short.TYPE, Short.class);
  	primitiveToWrapper.put(Character.TYPE, Character.class);
  	primitiveToWrapper.put(Integer.TYPE, Integer.class);
  	primitiveToWrapper.put(Long.TYPE, Long.class);
  	primitiveToWrapper.put(Float.TYPE, Float.class);
  	primitiveToWrapper.put(Double.TYPE, Double.class);
  	
    wrapperToPrimitive.put(Boolean.class, Boolean.TYPE);
    wrapperToPrimitive.put(Byte.class, Byte.TYPE);
    wrapperToPrimitive.put(Short.class, Short.TYPE);
    wrapperToPrimitive.put(Character.class, Character.TYPE);
    wrapperToPrimitive.put(Integer.class, Integer.TYPE);
    wrapperToPrimitive.put(Long.class, Long.TYPE);
    wrapperToPrimitive.put(Float.class, Float.TYPE);
    wrapperToPrimitive.put(Double.class, Double.TYPE);
  }
	
  /**
	 * Is an public class ?
	 */
	public static boolean isPublicClass(Class beanClass) {
		checkBeanClass(beanClass);
		return Modifier.isPublic(beanClass.getModifiers());
	}

	/**
	 * Is an abstract class or interface
	 */
	public static boolean isAbstractClass(Class beanClass) {
		checkBeanClass(beanClass);
		return !beanClass.isPrimitive() && Modifier.isAbstract(beanClass.getModifiers());
	}

	/**
	 * Is an abstract class or interface
	 */
	public static boolean isFinalClass(Class beanClass) {
		checkBeanClass(beanClass);
		return Modifier.isFinal(beanClass.getModifiers());
	}

  /**
	 * Load a class
	 */
  public static Class loadClass(String clsName) throws ClassNotFoundException {
   return loadClass(clsName,ClassUtil.class.getClassLoader());
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
   	checkBeanClassName(name);
  	if(ClassMap.containsKey(name.trim().toLowerCase())){
    	return (Class)ClassMap.get(name.trim().toLowerCase());
    }else{
    	return Class.forName(name,initialize,loader);
    }
  }
 
 
  /**
   * 比较两个类数组是否相等
   */
  public static boolean isMatchedClasses(Class[] baseSourceTypes,Class[] targetTypes){
  	boolean isEquals =true;
  	if(baseSourceTypes.length==targetTypes.length){
   		for(int i=0;i<baseSourceTypes.length;i++){
  			if(!isMatchedClass(baseSourceTypes[i],targetTypes[i])){
  				isEquals=false;
  				break;
  			}
  		}
  	}else{
  		isEquals = false;
  	}
  	
  	return isEquals;
  }
 
  /**
	 * 判断两个类是否等价
	 */
	public static boolean isMatchedClass(Class baseClass,Class clazz2) {
		if(baseClass== null || clazz2==null)
			throw new IllegalArgumentException("Compared classes can't be null");
		
		if(baseClass.isAssignableFrom(clazz2)) {
			return true;
		}else if (baseClass.isPrimitive() && clazz2.equals(primitiveToWrapper.get(baseClass))) {
			return true;
		} else if (clazz2.isPrimitive() && baseClass.equals(primitiveToWrapper.get(clazz2))) {
			return true;
		} else if(Collection.class.isAssignableFrom(baseClass)&& Collection.class.isAssignableFrom(clazz2)) {
				return true;
		} else if(Map.class.isAssignableFrom(baseClass)&& Map.class.isAssignableFrom(clazz2)) {
			return true;
		}else if(ArrayUtil.isArray(baseClass) && ArrayUtil.isArray(clazz2)){
			if(ArrayUtil.getArrayType(baseClass).isAssignableFrom(ArrayUtil.getArrayType(clazz2))){
				return true;
			}else{
				return false;
			}
		}else {
			 return false;
		}
	}
	
  /**
   * return class short name
   */
  public static String getClassShortName(Class cls){
    String classname = cls.getName();
    int pos = classname.lastIndexOf(".");
    if(pos > 0){
      return classname.substring(pos + 1);
    }else {
      return classname;
    }
  }

  /**
   * return package name for class
   */
  public static String getPackageName(Class cls){
    String name = cls.getName();
    int pos = name.lastIndexOf(".");
    if (pos > 0){
      return name.substring(0, pos);
    }else{
      return "";
    }
  }

  /**
   * get Class Names
   */
  public static String[] getClassesNames(Class[] types) {
   String[] names = new String[(types == null) ? 0 : types.length];
    if (types != null) {
      for (int j = 0; j < types.length; j++) {
        names[j] = types[j].getName();
      }
    }
    return names;
  }
  
  /**
   * get Class Names
   */
  public static Class[] getTypes(Object[] parameters) {
  	Class[] types = new Class[(parameters == null) ? 0 : parameters.length];
    for (int i = 0; i < types.length; i++) {
    	if(parameters[i] != null)
    		types[i] = parameters[i].getClass();
    }
    return types;
  }
  
  
  /**
   * 检查不能为空
   */
  private static void checkBeanClass(Class beanClass)throws IllegalArgumentException{
  	if(beanClass==null)
  		throw new IllegalArgumentException("Target bean class can't be null");
  }
  
  /**
   * 检查类不能为空
   */
  private static void checkBeanClassName(String beanclassName)throws IllegalArgumentException{
  	if(beanclassName==null || beanclassName.trim().length()==0)
  		throw new IllegalArgumentException("Target class name can't be null");
  }

  /**
   * 获得包装类型
   */
  public static Class getWrapperClass(Class type) {
  	if(!type.isPrimitive()){
  		return type;
  	}else{
  		return (Class)primitiveToWrapper.get(type);
  	}
  }
  
  /**
   * 获得primitive类型
   */
  public static Class getPrimitiveClass(Class type) {
  	if(wrapperToPrimitive.containsKey(type))
  		return (Class)wrapperToPrimitive.get(type);
  	else
  		return type;
  }
}