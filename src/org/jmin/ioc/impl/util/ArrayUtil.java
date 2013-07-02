/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.impl.util;

import java.lang.reflect.Array;

/**
 * A Util class for Array
 *
 * @author Chris Liao
 * @version 1.0
 */

public class ArrayUtil {

  /**
   * return dimension of array
   */
  public static boolean isArray(Class clazz) {
    return clazz.isArray();
  }
	
  /**
   * return dimension of array
   */
  public static boolean isArray(Object object) {
    return object.getClass().isArray();
  }
 
  /**
   * return array component type
   */
  public static Class getArrayType(Class array) {
    return array.getComponentType();
  }
  
  /**
   * return array component type
   */
  public static Class getArrayType(Object array) {
    return array.getClass().getComponentType();
  }

  /**
   * return dimension of array
   */
  public static int getArraySize(Object array) {
    return Array.getLength(array);
  }

  /**
   * create a array with type
   */
  public static Object createArray(Class type, int size) {
    return Array.newInstance(type, size);
  }

  /**
   * return dimension of array
   */
  public static Object getObject(Object arry, int index) {
    return Array.get(arry, index);
  }

  /**
   * return dimension of array
   */
  public static void setObject(Object arry,int index,Object value) {
    Array.set(arry, index, value);
  }
}