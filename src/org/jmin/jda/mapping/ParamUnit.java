/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.mapping;

import org.jmin.jda.JdaTypePersister;

/**
 * SQL表达式中的参数单元定义信息
 * 
 * @author Chris
 */

public interface ParamUnit {
	
	/**
	 * 获得参数名称
	 */
	public String getPropertyName();

	/**
	 * 参数单元类别
	 */
	public Class getPropertyType();

	/**
	 * 参数单元类别
	 */
	public void setPropertyType(Class type);
	
	/**
	 * 返回映射列的类型代码
	 */
	public int getParamColumnTypeCode();
	
	/**
	 * 设置映射列的类型代码
	 */
	public void setParamColumnTypeCode(int code);
	
	/**
	 * 返回映射列的类型名
	 */
	public String getParamColumnTypeName();

	/**
	 * 设置映射列的类型名
	 */
	public void setParamColumnTypeName(String name);
	
	/**
	 * 获得参数模式
	 */
	public ParamValueMode geParamValueMode();

	/**
	 * 设置参数模式
	 */
	public void setParamValueMode(ParamValueMode mode);
	
	/**
	 * 获得参数转换
	 */
	public JdaTypePersister getJdbcTypePersister();
	
	/**
	 * 设置参数的转换
	 */
	public void setJdbcTypePersister(JdaTypePersister persister);
	
	
}
