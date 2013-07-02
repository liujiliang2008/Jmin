/*
*Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.connection;

import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import org.jmin.log.Logger;

/**
 * Statement缓存
 *
 * @author Chris liao
 * @version 1.0
 */

public class StatementCache {
	
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
	private Map statementMap;
	
	/**
	 * 默认Map的大小
	 */
	private static final int DEFAULT_SIZE = 20;
	
	/**
	 * message Printer
	 */
	private static final Logger logger = Logger.getLogger(StatementCache.class);
	
	/**
	 * 构造函数
	 */
	public StatementCache() {
		this(DEFAULT_SIZE);
	}

	/**
	 * 构造函数
	 */
	public StatementCache(int maxSize) {
		this.maxSize = maxSize;
		this.keyList = new LinkedList();
		this.statementMap = new HashMap();
	}

	/**
	 * 最大Size
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
	 * isEmpty
	 */
	public synchronized boolean isEmpty() {
		return this.statementMap.isEmpty();
	}

	/**
	 * contain object
	 */
	public synchronized boolean containsKey(Object key) {
		return statementMap.containsKey(key);
	}
	
	/**
	 * put element
	 */
	public synchronized void put(Object key, PreparedStatement statement) {
		this.keyList.remove(key);
		this.keyList.add(0, key);
		this.statementMap.put(key, statement);

		if(this.keyList.size() > maxSize) {
			Object lastKey = keyList.removeLast();
			Object lastElement = this.statementMap.remove(lastKey);
			if(lastElement!=null){
			 this.onRemove(lastKey, lastElement);
			 lastElement=null;
			}
		}
	}

	/**
	 * get element
	 */
	public synchronized PreparedStatement get(Object key) {
		Object element = this.statementMap.get(key);
		if (element != null) {
			this.keyList.remove(key);
			this.keyList.add(0, key);
		}
		return (PreparedStatement)element;
	}

	/**
	 * remove element
	 */
	public synchronized Object remove(Object key) {
		Object element = this.statementMap.remove(key);
		if (element != null) {
			this.keyList.remove(key);
			this.onRemove(key, element);
			element=null;
		}
		return element;
	}

	/**
	 * contain object
	 */
	public synchronized boolean containsValue(PreparedStatement statement) {
		boolean containsValue = false;
		Iterator itor = this.statementMap.values().iterator();
		while(itor.hasNext()){
			Object obj = itor.next();
			if(obj.equals(statement)){
				containsValue = true;
				break;
			}
		}
		return containsValue;
	}
	
	/**
	 * clear
	 */
	public synchronized void clear() {
		Iterator itor = keyList.iterator();
		while (itor.hasNext()) {
			Object key = itor.next();
			itor.remove();
			Object element = this.statementMap.remove(key);
			if(element != null){
				this.onRemove(key, element);
				element=null;
			}
		}
	}

	/**
	 * 获得值数组
	 */
	synchronized Map.Entry[] toEntryArray() {
		return (Map.Entry[])this.statementMap.entrySet().toArray(new Map.Entry[0]);
	}

	/**
	 * 从Cache中删除一个statement
	 */
	void onRemove(Object key, Object obj) {
		try {
			((PreparedStatement)obj).close();
			logger.debug("Closed cached preparedStatement: " + obj);
		} catch (Throwable e) {
		}
	}
}
