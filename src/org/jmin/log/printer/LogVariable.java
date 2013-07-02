/*
* Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.log.printer;

import java.util.HashMap;
import java.util.Map;

/**
 * 记录日记驱动打印者的信息
 * 
 * @author Chris Liao
 */
public final class LogVariable {

	/**
	 * 存放打印线程的一些信息
	 */
	private static ThreadLocal local = new ThreadLocal();

	/**
	 * 放置某个属性Vale
	 */
	public static void put(String key,Object value){
		Map valueMap=(Map)local.get();
		if(valueMap==null){
			valueMap = new HashMap();
			local.set(valueMap);
		}
		valueMap.put(key,value);
	}
	
	/**
	 * 获得某个属性Value
	 */
	public static Object get(String key){
		return get(key,"System");
	}
	
	/**
	 * 获得某个属性Value
	 */
	public static Object get(String key,String defaultValue){
		Map valueMap=(Map)local.get();
		if(valueMap==null)
			return defaultValue;
		else 
			return valueMap.get(key);
	}
	
	/**
	 * 删除某个属性Value
	 */
	public static Object remove(String key){
		Map valueMap=(Map)local.get();
		if(valueMap!=null){
			return valueMap.remove(key);
		}else{
			return null;
		}
	}
}
