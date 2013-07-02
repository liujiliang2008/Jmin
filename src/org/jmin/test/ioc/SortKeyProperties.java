package org.jmin.test.ioc;

import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

/**
 * 有顺序的Properties
 * 
 * @author chris
 */
public class SortKeyProperties extends Properties {
	
	/**
	 * 存放keys
	 */
	private Vector keyVector = new Vector();
	
	/**
	 * 获得Key List
	 */
	public synchronized Enumeration keys() {
		return keyVector.elements();
	}
	
	/**
	 * 重写父类方法
	 */
	public synchronized Object put(Object key, Object value) {
		Object oldValue = super.put(key,value);
		if(!keyVector.contains(key))
			keyVector.add(key);
		return oldValue;
	}
	
	/**
	 * 重写父类方法
	 */
	public synchronized Object remove(Object key) {
		Object value = super.remove(key);
		if(keyVector.contains(key))
			keyVector.remove(key);
		return value;
	}
}
