/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.test.ioc.autowired;

import org.jmin.ioc.element.annotation.Autowired;
import org.jmin.ioc.element.annotation.Bean;

/**
 * Autowired bean
 * 
 * @author chris
 */
@Bean
public class Girl {
	
	/**
	 * age
	 */
	@Autowired(type = "byType")
	private int age;
	
	/**
	 * age
	 */
	public int getAge() {
		return age;
	}
	
	/**
	 * age
	 */
	public void setAge(int age) {
		this.age = age;
	}
}
