/*
* Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.test.ioc.bijection;

import org.jmin.ioc.element.annotation.Bean;
import org.jmin.ioc.element.annotation.Parameter;

/**
 * 双向注入类
 * 
 * @author Chris liao
 */
@Bean
public class Wife {
	
	/**
	 *名字
	 */
	@Parameter("val:Summer")
	private String name;
	
	/**
	 * 丈夫
	 */
	@Parameter("ref:Husband")
	private Husband husband;

	/**
	 *名字
	 */
	public String getName() {
		return name;
	}
	
	/**
	 *名字
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 丈夫
	 */
	public Husband getHusband() {
		return husband;
	}
	
	/**
	 * 丈夫
	 */
	public void setHusband(Husband husband) {
		this.husband = husband;
	}
}
