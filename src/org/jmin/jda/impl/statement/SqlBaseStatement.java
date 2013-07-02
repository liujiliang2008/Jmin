/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.statement;

import org.jmin.jda.mapping.ResultMap;

/**
 * SQL定义超类
 * 
 * @author Chris
 */
public abstract class SqlBaseStatement {
	
	/**
	 * 定义ID
	 */
	protected String sqlId;
		
	/**
	 * SQL操作类型
	 */
	private SqlOperationType sqlType;
	
	/**
	 * 结果影射
	 */
	protected ResultMap resultMap = null;

	/**
	 * 构造函数
	 */
	public SqlBaseStatement(String id,SqlOperationType sqlType){
		this.sqlId=id;
		this.sqlType=sqlType;
	}
	
	/**
	 * 定义ID
	 */
	public String getSqlId() {
		return sqlId;
	}
	
	/**
	 * SQL操作类型
	 */
	public SqlOperationType getSqlType() {
		return sqlType;
	}
	
	/**
	 * 结果影射
	 */
	public ResultMap getResultMap() {
		return resultMap;
	}
	
	/**
	 * 参数类
	 */
	public abstract Class getParamClass();
	 
	/**
	 * 结果类
	 */
	public abstract Class getResultClass();
	
}
