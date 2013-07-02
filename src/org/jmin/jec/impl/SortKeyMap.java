/*
 * Copyright (c) jmin Organization. All rights reserved.
 */
package org.jmin.jec.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

/**
 * 有顺序的Map
 * 
 * @author chris
 */
public class SortKeyMap extends HashMap {
	
	/**
	 * 存放keys
	 */
	private LinkedList keyset = new LinkedList();
	
	/**
	 * 获得Key List
	 */
	public synchronized Set keySet() {
		throw new RuntimeException("Not support");
	}
	
	/**
	 * 获得Key数组
	 */
	public synchronized Object[] getKeys() {
		return keyset.toArray();
	}
	
	/**
	 * 获得Key数组
	 */
	public synchronized Object[] getKeys(Object[]types){
		return keyset.toArray(types);
	}

	/**
	 * 重写父类方法
	 */
	public synchronized Object put(Object key, Object value) {
		Object oldValue = super.put(key,value);
		if(!keyset.contains(key))
			keyset.add(key);
		return oldValue;
	}
	
	/**
	 * 重写父类方法
	 */
	public synchronized Object remove(Object key) {
		Object value = super.remove(key);
		if(keyset.contains(key))
			keyset.remove(key);
		return value;
	}
	
	/**
	 * 重写父类方法
	 */
	public synchronized void clear() {
		super.clear();
		keyset.clear();
	}
}
