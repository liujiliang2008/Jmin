/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.element;

import org.jmin.ioc.BeanElementException;

/**
 * A description element for auto injection
 *
 * @author Chris Liao
 * @version 1.0
 */

public final class AutowiredType {
			
	/**
	 * 根据属性名自动装配
	 */
	public static final AutowiredType BY_NAME = new AutowiredType("ByName");

	/**
	 * 依据属性类型进行自动装配,如果存在多个该类型的实例，将抛出异常，如果不存在任何实例则不装配
	 */
	public static final AutowiredType BY_TYPE = new AutowiredType("ByType");

	/**
	 * 自动装配值
	 */
	private String autowiredValue;

	/**
	 * 构造函数
	 */
	AutowiredType(String autowiredValue) {
		this.autowiredValue = autowiredValue;
	}

	/**
	 * toString
	 */
	public String toString() {
		return autowiredValue;
	}

	/**
	 * 通过名字找到一个匹配的Autowire
	 */
	public static AutowiredType toAutowiredType(String typeName)
			throws BeanElementException {
		if ("ByName".equalsIgnoreCase(typeName)) {
			return BY_NAME;
		} else if ("ByType".equalsIgnoreCase(typeName)) {
			return BY_TYPE;
		} else {
			throw new BeanElementException("Invalid autowired value,must be one of[ByName,ByType]");
		}
	}

	/**
	 * Override method
	 */
	public boolean equals(Object obj) {
		if (obj instanceof AutowiredType) {
			return this.autowiredValue.equalsIgnoreCase(((AutowiredType) obj).autowiredValue);
		} else {
			return false;
		}
	}
}
