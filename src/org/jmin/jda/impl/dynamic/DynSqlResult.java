/*
* Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.jda.impl.dynamic;

import org.jmin.jda.mapping.ParamUnit;

/**
 * 动态SQL合成工厂
 * 
 * @author Chris liao
 */

public class DynSqlResult {
	
	/**
	 * sql id
	 */
	private String sqlId;
	
	/**
	 * sql本身文本
	 */
	private String sqlText;
	
	/**
	 * 参数对象
	 */
	private Object parmaObject;
	
	/**
	 * 执行的参数映射
	 */
	private ParamUnit[] paramUnits;
	
	/**
	 * 构造函数
	 */
	public DynSqlResult(String sqlId,Object parmaObject){
		this.sqlId =sqlId;
		this.parmaObject=parmaObject;
	}
	
	/**
	 * sql id
	 */
	public String getSqlId() {
		return sqlId;
	}
	
	/**
	 * 参数对象
	 */
	public Object getParmaObject() {
		return parmaObject;
	}
	
	/**
	 * sql本身文本
	 */
	public String getSqlText() {
		return sqlText;
	}
	
	public void setSqlText(String sqlText) {
		this.sqlText = sqlText;
	}
	
	/**
	 * 执行的参数映射
	 */
	public ParamUnit[] getParamUnits() {
		return paramUnits;
	}
	
	/**
	 * 执行的参数映射
	 */
	public void setParamUnits(ParamUnit[] paramUnits) {
		this.paramUnits = paramUnits;
	}
}
