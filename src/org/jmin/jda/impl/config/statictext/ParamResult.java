/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.config.statictext;

import org.jmin.jda.mapping.ParamMap;

/**
 * SQL文本分析结果
 * 
 * @author Chris Liao
 */
public class ParamResult {
	
	/**
	 * 可执行的SQL语句
	 */
	private String exeSQL=null;
	
	/**
	 * 可执行的SQL语句
	 */
	private ParamMap paramMap=null;

	public String getExeSQL() {
		return exeSQL;
	}

	public void setExeSQL(String exeSQL) {
		this.exeSQL = exeSQL;
	}

	public ParamMap getParamMap() {
		return paramMap;
	}

	public void setParamMap(ParamMap paramMap) {
		this.paramMap = paramMap;
	}
}
