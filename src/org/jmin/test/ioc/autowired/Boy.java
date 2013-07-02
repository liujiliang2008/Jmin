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
public class Boy {
	@Autowired
	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
