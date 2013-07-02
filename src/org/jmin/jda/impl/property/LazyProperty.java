/*
*Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.property;

/**
 * 延迟加载属性
 * 
 * @author Chris Liao
 */
public class LazyProperty {

	/**
	 * 属性名
	 */
	private String propertyName;

	/**
	 * 属性类型
	 */
	private Class propertyType;
	
	/**
	 * 构造函数
	 */
	public LazyProperty(String propertyName,Class propertyType){
		this.propertyName = propertyName;
		this.propertyType = propertyType;
	}
	
	/**
	 * 属性名
	 */
	public String getPropertyName() {
		return propertyName;
	}

	/**
	 * 属性类型
	 */
	public Class getPropertyType() {
		return propertyType;
	}
}
