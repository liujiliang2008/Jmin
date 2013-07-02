/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.util;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * 有序Map
 * 
 * @author emily
 */
public class OrderMap extends HashMap{
	
	/**
	 * Key的List列表
	 */
	private LinkedList keyList = new LinkedList();
	
	/**
	 * put element
	 */
	public Object put(Object key, Object element) {
		super.put(key,element);
		if(!keyList.contains(key))
		 keyList.add(key);
		return element;
	}
	
	/**
	 * remove element
	 */
	public Object remove(Object key) {
		Object removed = super.remove(key);
		if(keyList.contains(key))
			keyList.remove(key);
		return removed;
	}
	
	/**
	 * remove element
	 */
	public void clear() {
		 super.clear();
		 keyList.clear();
	}
	
	/**
	 * remove element
	 */
	public Object[] keyValues() {
		return keyList.toArray();
	}
}
