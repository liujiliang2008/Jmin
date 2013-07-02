/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.mapping.result;

import org.jmin.jda.mapping.RelationUnit;
import org.jmin.jda.mapping.ResultMap;
import org.jmin.jda.mapping.ResultUnit;

/**
 * 结果映射列表
 * 
 * @author Chris 
 */

public class ResultMapImpl implements ResultMap{
	
	/**
	 *是否初始化过
	 */
	private boolean inited;
	
	/**
	 * 结果类
	 */
	private Class resultClass;
	
	/**
	 * 结果映射
	 */
	private ResultUnit[] resultUnits;
	
	/**
	 * 关联映射
	 */
	private RelationUnit[] relationUnits;
	
	/**
	 * 延迟加载的属性已经初始化
	 */
	private boolean layLoadInited;
	
	/**
	 * 延迟加载的类
	 */
	private Class layLoadresultClass;
	
	/**
	 * 构造函数
	 */
	public ResultMapImpl(Class resultClass,ResultUnit[] resultUnits) {
		this(resultClass,resultUnits, null);
	}
	
	/**
	 * 构造函数
	 */
	public ResultMapImpl(Class resultClass,ResultUnit[] resultUnits,RelationUnit[] relationUnits) {
		this.resultClass= resultClass;
		this.resultUnits = resultUnits;
		this.relationUnits = relationUnits;
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
	 * 结果类
	 */
	public Class getResultClass() {
		return resultClass;
	}
	
	/**
	 * 结果映射
	 */
	public ResultUnit[] getResultUnits() {
		return resultUnits;
	}
	
	/**
	 * 映射单元
	 */
	public void setResultUnits(ResultUnit[] resultUnits) {
		this.resultUnits = resultUnits;
	}
	
	/**
	 * 关联映射
	 */
	public RelationUnit[] getRelationUnits() {
		return relationUnits;
	}
	
	/**
	 * 关联映射
	 */
	public void setRelationUnits(RelationUnit[] relationUnits) {
		this.relationUnits = relationUnits;
	}
	
	/**
	 * 延迟加载的属性已经初始化
	 */
	public boolean isLayLoadInited() {
		return layLoadInited;
	}
	
	/**
	 * 延迟加载的属性已经初始化
	 */
	public void setLayLoadInited(boolean layLoadInited) {
		this.layLoadInited = layLoadInited;
	}
	
	/**
	 * 延迟加载的类
	 */
	public Class getLayLoadresultClass() {
		if(layLoadInited)
			return layLoadresultClass;
		else
			return resultClass;
	}
	
	/**
	 * 延迟加载的类
	 */
	public void setLayLoadresultClass(Class layLoadresultClass) {
		this.layLoadresultClass = layLoadresultClass;
	}
}
