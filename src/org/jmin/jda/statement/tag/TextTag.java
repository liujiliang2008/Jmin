/*
* Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.jda.statement.tag;

import org.jmin.jda.mapping.ParamUnit;
import org.jmin.jda.statement.DynTag;
 
/**
 * SQL 文本片段
 * 
 * @author Chris
 */

public class TextTag extends DynTag{
	
	/**
	 * SQL文本
	 */
	private String content;
	
	/**
	 * 映射参数
	 */
	private ParamUnit[] paramUnits;
	
	/**
	 * 构造函数
	 */
	public TextTag(String text) {
		 this(text,null);
	}
	
	/**
	 * 构造函数
	 */
	public TextTag(String text,ParamUnit[] paramUnits){
		this.content =text;
		this.paramUnits =paramUnits;
	}
	
	/**
	 * 标签名
	 */
	public String getTagName(){
		return "";
	}

	/**
	 * SQL文本
	 */
	public String getText() {
		return this.content;
	}
	
	/**
	 * 映射参数
	 */
	public ParamUnit[] getParamUnit() {
		return this.paramUnits;
	}
	
	/**
	 * 增加子标签
	 */
	public void addChild(DynTag tag) {
		throw new UnsupportedOperationException("Operation not support");
	}
	
	/**
	 * 删除子标签
	 */
	public void removeChild(DynTag tag) {
		throw new UnsupportedOperationException("Operation not support");
	}
	
	/**
	 * 获得子属性
	 */
	public DynTag getChildren(int index){
		 throw new UnsupportedOperationException("Operation not support");
	}
	/**
	 * 获得子属性
	 */
	public DynTag[] getChildren(){
		throw new UnsupportedOperationException("Operation not support");
	}
	
	/**
	 * 获得
	 */
	public void apend(StringBuffer buf){
		buf.append(content);
	}
}
