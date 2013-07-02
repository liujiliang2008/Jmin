/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda;

import java.sql.SQLException;

import org.jmin.jda.mapping.ParamMap;
import org.jmin.jda.mapping.ParamUnit;
import org.jmin.jda.mapping.RelationUnit;
import org.jmin.jda.mapping.ResultMap;
import org.jmin.jda.mapping.ResultUnit;
import org.jmin.jda.statement.DynTag;

/**
 * SQL表达式中心
 * 
 * @author Chris Liao
 */

public interface JdaContainer {
	
	/**
	 * 是否包含表达式
	 */
	public boolean containsSql(String id)throws SQLException;
	
	/**
	 * 注销SQL
	 */
	public void unregisterSql(String id)throws SQLException;
	
	/**
	 * 是否为静态SQL
	 */
	public boolean isStaticSql(String id)throws SQLException;

	/**
	 * 是否为动态SQL
	 */
	public boolean isDynamicSql(String id)throws SQLException;
	
	
	/**
	 * 注册SQL的表达式
	 */
	public void registerStaticSql(String id,String sql)throws SQLException;
	
	/**
	 * 注册SQL的表达式
	 */
	public void registerStaticSql(String id,String sql,ParamMap paramMap)throws SQLException;
	
	/**
	 * 注册SQL的表达式
	 */
	public void registerStaticSql(String id,String sql,ResultMap resultMap)throws SQLException;
	
	/**
	 * 注册SQL的表达式
	 */
	public void registerStaticSql(String id,String sql,ParamMap paramMap,ResultMap resultMap)throws SQLException;
	
	/**
	 * 注册动态SQL的
	 */
	public void registerDynamicSql(String id,DynTag[] tags,Class paramClass)throws SQLException;
	
	/**
	 * 注册动态SQL的
	 */
	public void registerDynamicSql(String id,DynTag[] tags,Class paramClass,ResultMap resultMap)throws SQLException;
	
	
	/**
	 * 创建参数属性
	 */
	public ParamUnit createParamUnit(String propertyName)throws SQLException;
	
	/**
	 * 创建结果属性
	 */
	public ResultUnit createResultUnit(String propertyName)throws SQLException;
	
	/**
	 * 创建参数属性
	 */
	public ParamUnit createParamUnit(String propertyName,Class propertyType)throws SQLException;

	/**
	 * 创建结果属性
	 */
	public ResultUnit createResultUnit(String propertyName,Class propertyType)throws SQLException;
	
	/**
	 * 创建关联属性
	 */
	public RelationUnit createRelationUnit(String propertyName,String sqlId)throws SQLException;
	
	/**
	 * 创建关联属性
	 */
	public RelationUnit createRelationUnit(String propertyName,Class propertyType,String sqlId)throws SQLException;

	/**
	 * 创建参数映射列表
	 */
	public ParamMap createParamMap(Class paramClass,ParamUnit[]paramUnits)throws SQLException;
	
	/**
	 * 创建结果映射列表
	 */
	public ResultMap createResultMap(Class resultClass,ResultUnit[]resultUnits)throws SQLException;
	
	/**
	 * 创建结果映射列表
	 */
	public ResultMap createResultMap(Class resultClass,ResultUnit[]resultUnits,RelationUnit[] relationUnits)throws SQLException;
	

	/**
	 * 获得jdbc type code
	 */
	public int getJdbcTypeCode(String typeName)throws SQLException;
	
	/**
	 * 验证映射列表中是否包含Jdbc type
	 */
	public boolean containsJdbcType(String typeName)throws SQLException;
	
	/**
	 * 注册Jdbc类
	 */
	public void removeJdbcType(String typeName)throws SQLException;
	
	/**
	 * 注册Jdbc type
	 */
	public void addJdbcType(String typeName,int typeCode)throws SQLException;

	
	
	/**
	 * 是否包含类型转换器
	 */
	public boolean containsTypeConverter(Class type)throws SQLException;
	
  /**
   * 将对象转换为目标类型对象
   */
  public Object convertObject(Object obj,Class type)throws SQLException;
 
	/**
	 * 删除类型转换器
	 */
	public void removeTypeConverter(Class type)throws SQLException;
	
	/**
	 * 获得类型转换器
	 */
	public JdaTypeConverter getTypeConverter(Class type)throws SQLException;
	
	/**
	 * 注册类型转换器
	 */
	public void addTypeConverter(Class type,JdaTypeConverter converter)throws SQLException;
	

	

	/**
	 * 是否包含参数持久器
	 */
	public boolean containsTypePersister(Class type)throws SQLException;
	
	/**
	 * 是否包含参数持久器
	 */
	public boolean containsTypePersister(Class type,String jdbcName)throws SQLException;
	
	/**
	 * 获得参数持久器
	 */
	public JdaTypePersister getTypePersister(Class type)throws SQLException;
	
	/**
	 * 获得参数持久器
	 */
	public JdaTypePersister getTypePersister(Class type,String jdbcName)throws SQLException;
	
	
	/**
	 * 注册参数持久器
	 */
	public void addTypePersister(Class type,JdaTypePersister persister)throws SQLException;
	
	/**
	 *  注册参数持久器
	 */
	public void addTypePersister(Class type,String jdbcName,JdaTypePersister persister)throws SQLException;

	/**
	 * 删除参数持久器
	 */
	public void removeTypePersister(Class type)throws SQLException;

	/**
	 *  删除参数持久器
	 */
	public void removeTypePersister(Class type,String jdbcName)throws SQLException;
	
		
  /**
   * 创建Session
   */
  public JdaSession openSession() throws SQLException;
}