/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.mapping.result;

import org.jmin.jda.mapping.RelationUnit;

/**
 * 关联属性:
 * 
 * 对象关联有好几几种 1： 普通对象关联 2： List关联 3： set关联 4： connection关联 5： map关联
 * 
 * @author Chris Liao
 */
public class RelationUnitImpl implements RelationUnit{
	
	/**
	 *是否初始化过
	 */
	private boolean inited;
	
	/**
	 * 关联的SQL定义ID
	 */
	private String sqlId;
	
	/**
	 * 所属于的映射map
	 */
  private Object mapOwner=null;

	/**
	 * 属性名
	 */
	private String propertyName=null;

	/**
	 * 属性类型
	 */
	private Class propertyType=null;

	/**
	 * 用于关联的列名,将会从该列中获取结果值
	 */
  private String[] relateColumnNames=null;
   
	/**
	 * map key的属性名
	 */
	private String keyPropertyName=null;
	
	/**
	 * map value的属性名
	 */
	private String valuePropertyName=null;
	

	/**
	 * 是否需要延迟加载
	 */
	private boolean lazyLoad;
	
	/**
	 * 构造函数
	 */
	public RelationUnitImpl(String sqlId,String properTyname) {
		this(sqlId,properTyname,null);
	}

	/**
	 * 构造函数
	 */
	public RelationUnitImpl(String sqlId,String propertyName,Class propertyType) {
		this.sqlId = sqlId;
		this.propertyName = propertyName;
		this.propertyType = propertyType;
	}
	
	/**
	 *是否初始化过
	 */
	public boolean isInited() {
		return inited;
	}
	
	/**
	 *是否初始化过
	 */
	public void setInited(boolean inited) {
		this.inited = inited;
	}
	
	/**
	 * 所属于的映射map
	 */
	public Object getMapOwner() {
		return mapOwner;
	}

	/**
	 * 所属于的映射map
	 */
	public void setMapOwner(Object mapOwner) {
		this.mapOwner = mapOwner;
	}
	
	/**
	 * 获得关联ID
	 */
	public String getSqlId() {
		return this.sqlId;
	}
	
	/**
	 * 关联属性类型名
	 */
	public String getPropertyName() {
		return propertyName;
	}
	
	/**
	 * 获得属性类别
	 */
	public Class getPropertyType(){ 
		return this.propertyType;
	}
	
	/**
	 * 设置属性类别
	 */
	public void setPropertyType(Class type){
		if(!this.isInited())
			this.propertyType = type;
	}

	/**
	 * 获得参数影射单元
	 */
	public String[] getRelationColumnNames(){
		return this.relateColumnNames;
	}
	
	/**
	 * 获得参数影射单元
	 */
	public void setRelationColumnNames(String[] relateColumnNames){
		if(!this.isInited())
		 this.relateColumnNames = relateColumnNames;
	}

	/**
	* 延迟加载
	*/
	public boolean isLazyLoad() {
		return lazyLoad;
	}
	
	/**
	* 延迟加载
	*/
	public void setLazyLoad(boolean delayLoad) {
		if(!this.isInited())
		 this.lazyLoad = delayLoad;
	}
	
	/**
	 * map key的属性名
	 */
	public String getMapKeyPropertyName() {
		return keyPropertyName;
	}
	
	/**
	 * map key的属性名
	 */
	public void setMapKeyPropertyName(String keyPropertyName) {
		if(!this.isInited())
		 this.keyPropertyName = keyPropertyName;
	}
	
	/**
	 * map value的属性名
	 */
	public String getMapValuePropertyName() {
		return valuePropertyName;
	}
	
	/**
	 * map value的属性名
	 */
	public void setMapValuePropertyName(String valuePropertyName) {
		if(!this.isInited())
		 this.valuePropertyName = valuePropertyName;
	}
}