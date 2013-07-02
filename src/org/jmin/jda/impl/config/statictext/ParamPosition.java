/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.config.statictext;

/**
 * SQL参数的位置
 * 
 * @author Chris Liao
 */

public class ParamPosition implements Comparable{

	/**
	 * 开始索引位置
	 */
	private int startIndex;
	
	/**
	 * 结束索引位置
	 */
	private int endIndex;
	
	/**
	 * 字串内容
	 */
	private ParamSymbol parameterSymbol;
	
	/**
	 * 构造函数
	 */
	public ParamPosition(int startIndex,int endIndex,ParamSymbol parameterSymbol){
		this.startIndex =startIndex;
		this.endIndex = endIndex;
		this.parameterSymbol = parameterSymbol;
	}
	
	public int getStartIndex() {
		return startIndex;
	}

  public int getEndIndex() {
		return endIndex;
	}

	public ParamSymbol getParamSymbol() {
		return parameterSymbol;
	}
	
 	/**
 	 * 比较
	 */
  public int compareTo(Object o){
  	ParamPosition other =(ParamPosition)o;
  	if(this.startIndex < other.startIndex){
  		return -1;
  	}else if(this.startIndex == other.startIndex){
  		return 0;
  	}else {
  		return 1;
  	}
  }
}
