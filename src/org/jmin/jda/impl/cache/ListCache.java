/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.cache;

import java.util.LinkedList;

/**
 * 最少访问列表，将最少访问的元素排除在外
 * 
 * @author Chris Liao
 */

public class ListCache {

	/**
	 * 默认List的大小
	 */
	private static final int DEFAULT_SIZE = 1000;
	
	/**
	 * The number of items allowed in the list.
	 */
	private int maxSize;

	/**
	 * This is used for quicker access of objects.
	 */
	private LinkedList list;

	/**
	 * 构造函数
	 */
	public ListCache() {
		this(DEFAULT_SIZE);
	}

	/**
	 * 构造函数
	 */
	public ListCache(int maxSize) {
		this.maxSize = maxSize;
		this.list = new LinkedList();
	}

	/**
	 * add element
	 */
	public synchronized void add(Object element) {
		if (this.list.contains(element))
			this.list.remove(element);

		this.list.add(0, element);
		if (this.list.size() > maxSize) {
			this.onRemove(list.removeLast());
		}
	}

	/**
	 * get element
	 */
	public synchronized Object get(int index) {
		Object element = this.list.remove(index);
		this.list.add(0, element);
		return element;
	}

	/**
	 * add element
	 */
	public synchronized void remove(Object element) {
		if (this.list.remove(element))
			this.onRemove(element);
	}

	/**
	 * contain object
	 */
	public synchronized boolean contains(Object element) {
		return list.contains(element);
	}

	/**
	 * isEmpty
	 */
	public synchronized boolean isEmpty() {
		return this.list.isEmpty();
	}

	/**
	 * clear
	 */
	public synchronized void clear() {
		Object[] arry = list.toArray();
		for (int i = 0; i < arry.length; i++) {
			if (this.list.remove(arry[i]))
				this.onRemove(arry[i]);
		}
	}

	/**
	 * size
	 */
	public synchronized int size() {
		return list.size();
	}

	/**
	 * 获得
	 */
	protected synchronized Object[] toArray() {
		return this.list.toArray();
	}

	/**
	 * do some on the removed object from list
	 */
	protected void onRemove(Object obj) {
	}
}
