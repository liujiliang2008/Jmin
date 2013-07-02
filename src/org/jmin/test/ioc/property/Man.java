/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.test.ioc.property;

/**
 * A bean class for IOC test.
 *
 * @author Chris Liao
 */
public class Man {

	/**
	 * name
	 */
	private String name;

	/**
	 * age
	 */
	private int age;
	

	/**
	 * Return man age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * set Age
	 */
	public void setAge(int age) {
		this.age = age;
	}
	
	/**
	 * return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set name
	 */
	public void setName(String name) {
		this.name = name;
	}
}