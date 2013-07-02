/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.statement;

import org.jmin.jda.mapping.RelationUnit;
import org.jmin.jda.mapping.ResultMap;
import org.jmin.jda.mapping.ResultUnit;
import org.jmin.jda.statement.DynTag;

/**
 * 动态SQL定义
 * 
 * @author Chris
 */
public class SqlDynStatement extends SqlBaseStatement{
	
	/**
	 * 合成标记
	 */
	private DynTag[]tags;
	
	/**
	 * 参数类
	 */
	private Class paramClass;
 
	/**
	 * 构造函数
	 */
	public SqlDynStatement(String id,SqlOperationType sqlType,DynTag[]tags,Class paramClass){
		 super(id,sqlType);
		 this.tags=tags;
		 this.paramClass = paramClass;
	}
	
	/**
	 * 构造函数
	 */
	public SqlDynStatement(String id,SqlOperationType sqlType,DynTag[]tags,Class paramClass,ResultMap resultMap){
		 super(id,sqlType);
		 this.tags=tags;
		 this.paramClass = paramClass;
		 this.resultMap = resultMap;
	}

	/**
	 * 合成标记
	 */
	public DynTag[] getDynTags(){
		return this.tags;
	}
	
	/**
	 * 参数类
	 */
	public Class getParamClass() {
		return paramClass;
	}

	/**
	 * 结果影射列表
	 */
	public ResultMap getResultMap() {
		return resultMap;
	}
	
	/**
	 * 参数类
	 */
	public Class getResultClass() {
		return (resultMap == null)?null:resultMap.getResultClass();
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