/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.mapping;

/**
 * 结果映射列表
 * 
 * @author Chris 
 */
public interface ResultMap {
	
	/**
	 * 获得结果类
	 */
	public Class getResultClass();
	
	/**
	 * 获得结果影射单元
	 */
	public ResultUnit[] getResultUnits();
	
	/**
	 * 获得关联影射单元
	 */
	public RelationUnit[] getRelationUnits();
	
}