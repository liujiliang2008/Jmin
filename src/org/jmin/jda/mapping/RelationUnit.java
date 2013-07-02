/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.mapping;

/**
 * 关联属性
 * 
 * @author Chris Liao 
 */
public interface RelationUnit {
	
	/**
	 * 获得关联ID
	 */
	public String getSqlId();
	
	/**
	 * 获得属性名
	 */
	public String getPropertyName();

	/**
	 * 获得属性类别
	 */
	public Class getPropertyType();

	/**
	 * 设置属性类别
	 */
	public void setPropertyType(Class type);

	/**
	 * 获得参数影射单元
	 */
	public String[] getRelationColumnNames();
	
	/**
	 * 获得参数影射单元
	 */
	public void setRelationColumnNames(String[] columnNames);
	
	/**
	 * Map关联的对象的Key propertyName
	 */
	public String getMapKeyPropertyName();//只有当前关联属性为 map类型才有效
	
	/**
	 * Map关联的对象的Key propertyName
	 */
	public void setMapKeyPropertyName(String keyPropertyName);//只有当前关联属性为 map类型才有效
	
	/**
	 * Map关联的对象的Value propertyName
	 */
	public String getMapValuePropertyName();//只有当前关联属性为 map类型才有效
	
	/**
	 * Map关联的对象的Value propertyName
	 */
	public void setMapValuePropertyName(String valuePropertyName);//只有当前关联属性为 map类型才有效
	
	/**
	 * 延迟加载
	 */
	public boolean isLazyLoad();

	/**
	 * 延迟加载
	 */
	public void setLazyLoad(boolean lazy);
	
}