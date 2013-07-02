/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.mapping;

import org.jmin.jda.JdaTypePersister;

/**
 * 对象中的结果映射属性
 * 
 * @author Chris
 */

public interface ResultUnit{
		
	/**
	 * 获得结果名称
	 */
	public String getPropertyName();

	/**
	 * 结果单元类别
	 */
	public Class getPropertyType();

	/**
	 * 结果单元类别
	 */
	public void setPropertyType(Class type);
	
	/**
	 * 获得属性类别
	 */
	public String getResultColumnName();

	/**
	 * 设置属性类别
	 */
	public void setResultColumnName(String name);
	
	/**
	 * 获得结果映射
	 */
	public JdaTypePersister getJdbcTypePersister();
	
	/**
	 * 设置结果映射
	 */
	public void setJdbcTypePersister(JdaTypePersister persister);
}
