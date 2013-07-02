/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.test.ioc.field;

/**
 * 字段参数注入
 * 
 * @author chris
 */
public class Boy {
	
	/**
	 * 名字
	 */
	private String name;

	/**
	 * 年龄
	 */
	private int age;
	
	/**
	 * 名字
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 名字
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 年龄
	 */
	public int getAge() {
		return age;
	}
	
	/**
	 * 年龄
	 */
	public void setAge(int age) {
		this.age = age;
	}
}
