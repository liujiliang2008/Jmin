/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.cache;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

/**
 * 最少访问列Map，将最少访问的元素排除在外
 * 
 * @author Chris Liao
 */

public class MapCache {

	/**
	 * 默认Map的大小
	 */
	private static final int DEFAULT_SIZE = 1000;

	/**
	 * The number of items allowed
	 */
	private int maxSize;

	/**
	 * Key的List列表
	 */
	private LinkedList keyList;

	/**
	 * store the keys and values
	 */
	private Map elementValueMap;

	/**
	 * 构造函数
	 */
	public MapCache() {
		this(DEFAULT_SIZE);
	}

	/**
	 * @param maxSize
	 *          the maximum allowed number of objects
	 */
	public MapCache(int maxSize) {
		this.maxSize = maxSize;
		this.keyList = new LinkedList();
		this.elementValueMap = new HashMap();
	}

	/**
	 * put element
	 */
	public synchronized void put(Object key, Object element) {
		this.keyList.remove(key);
		this.keyList.add(0, key);
		this.elementValueMap.put(key, element);

		if (this.keyList.size() > maxSize) {
			Object lastKey = keyList.removeLast();
			Object lastElement = this.elementValueMap.remove(lastKey);
			this.onRemove(lastKey, lastElement);
		}
	}

	/**
	 * get element
	 */
	public synchronized Object get(Object key) {
		Object element = this.elementValueMap.get(key);
		if (element != null) {
			this.keyList.remove(key);
			this.keyList.add(0, key);
		}
		return element;
	}

	/**
	 * remove element
	 */
	public synchronized Object remove(Object key) {
		Object element = this.elementValueMap.remove(key);
		if (element != null) {
			this.keyList.remove(key);
			this.onRemove(key, element);
		}
		return element;
	}

	/**
	 * isEmpty
	 */
	public synchronized boolean isEmpty() {
		return this.elementValueMap.isEmpty();
	}

	/**
	 * contain object
	 */
	public synchronized boolean containsKey(Object key) {
		return elementValueMap.containsKey(key);
	}
	
	/**
	 * contain object
	 */
	public synchronized boolean containsValue(Object value) {
		boolean containsValue = false;
		Iterator itor = this.elementValueMap.values().iterator();
		while(itor.hasNext()){
			Object obj = itor.next();
			if(obj.equals(value)){
				containsValue = true;
				break;
			}
		}
		return containsValue;
	}
	

	/**
	 * size
	 */
	public synchronized int maxSize() {
		return this.maxSize;
	}

	/**
	 * size
	 */
	public synchronized int size() {
		return this.keyList.size();
	}

	/**
	 * clear
	 */
	public synchronized void clear() {
		Iterator itor = keyList.iterator();
		while (itor.hasNext()) {
			Object key = itor.next();
			itor.remove();
			Object element = this.elementValueMap.remove(key);
			if (element != null)
				this.onRemove(key, element);
		}
	}

	/**
	 * 获得值数组
	 */
	protected synchronized Map.Entry[] toEntryArray() {
		return (Map.Entry[])this.elementValueMap.entrySet().toArray(new Map.Entry[0]);
	}

	/**
	 * do some on the removed object from map
	 */
	protected void onRemove(Object key, Object obj) {}
}
