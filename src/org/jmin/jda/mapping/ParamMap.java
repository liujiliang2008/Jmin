/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.mapping;

/**
 * 参数映射列表
 * 
 * @author Chris 
 */

public interface ParamMap {
	
	/**
	 * 获得参数类
	 */
	public Class getParamClass();
	
	/**
	 * 获得参数影射单元
	 */
	public ParamUnit[] getParamUnits();
	
}