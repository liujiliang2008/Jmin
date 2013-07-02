/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.statement;

import org.jmin.jda.mapping.ParamMap;
import org.jmin.jda.mapping.ParamUnit;
import org.jmin.jda.mapping.RelationUnit;
import org.jmin.jda.mapping.ResultMap;
import org.jmin.jda.mapping.ResultUnit;

/**
 * 静态SQL 定义
 * 
 * @author Chris
 */
public class SqlStaticStatement extends SqlBaseStatement {

	/**
	 * SQL定义
	 */
	private String sql;

	/**
	 * 参数影射
	 */
	private ParamMap paramMap = null;

	
	/**
	 * 构造函数
	 */
	public SqlStaticStatement(String id, String sql,SqlOperationType sqlType,ParamMap paramMap, ResultMap resultMap) {
		super(id,sqlType);
		this.sql = sql;
		this.paramMap = paramMap;
		this.resultMap = resultMap;
	}

	/**
	 * SQL定义
	 */
	public String getSqlId() {
		return sqlId;
	}

	/**
	 * SQL定义
	 */
	public String getSql() {
		return sql;
	}

	/**
	 * 参数影射
	 */
	public ParamMap getParamMap() {
		return paramMap;
	}

	
	/**
	 * 参数类
	 */
	public Class getParamClass() {
		return (paramMap == null)?null:paramMap.getParamClass();
	}
	
	/**
	 * 参数类
	 */
	public Class getResultClass() {
		return (resultMap == null)?null:resultMap.getResultClass();
	}
	
	/**
	 * 参数单元
	 */
	public ParamUnit[] getParamUnits() {
		return (paramMap == null)?null:paramMap.getParamUnits();
	}

	/**
	 * 结果单元
	 */
	public ResultUnit[] getResultUnits() {
		return (resultMap == null)?null:resultMap.getResultUnits();
	}
	
	/**
	 * 关联单元
	 */
	public RelationUnit[] getRelationUnits() {
		return (resultMap == null)?null:resultMap.getRelationUnits();
	}
}
