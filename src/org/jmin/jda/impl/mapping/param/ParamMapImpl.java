/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.mapping.param;

import org.jmin.jda.mapping.ParamMap;
import org.jmin.jda.mapping.ParamUnit;

/**
 * 参数映射列表
 * 
 * @author Chris 
 */

public class ParamMapImpl implements ParamMap{
	
	/**
	 *是否初始化过
	 */
	private boolean inited;
	
	/**
	 * 参数类
	 */
	private Class paramClass;
	
	/**
	 * 映射单元
	 */
	private ParamUnit[] paramUnits;

	/**
	 * 构造函数
	 */
	public ParamMapImpl(Class paramClass,ParamUnit[] paramUnits) {
		this.paramClass = paramClass;
		this.paramUnits = paramUnits;
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
	 * 参数类
	 */
	public Class getParamClass() {
		return paramClass;
	}
	
	/**
	 * 映射单元
	 */
	public ParamUnit[] getParamUnits() {
		return paramUnits;
	}
	
	/**
	 * 映射单元
	 */
	public void setParamUnits(ParamUnit[] paramUnits) {
		this.paramUnits = paramUnits;
	}
}
