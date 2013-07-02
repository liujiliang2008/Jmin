/*
* Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.jda.statement.tag;

import org.jmin.jda.statement.DynTag;

/**
 * 动态标签
 * 
 * @author Chris Liao
 */

public class ForeachTag extends DynTag{
	
	/**
	 * 属性名
	 */
	private String propetyName="";
	
	/**
	 * 开始符号
	 */
	private String startSymbol ="(";
	
	/**
	 * 分割符号
	 */
	private String spaceSymbol =",";
	
	/**
	 * 结束符号
	 */
	private String endSymbol =")";
	
	/**
	 * SQL片段
	 */
	private String subSqlText;
	
	/**
	 * 构造函数
	 */
	public ForeachTag(String propetyName,String sqlBlock){
		this.propetyName = propetyName;
		this.subSqlText = sqlBlock;
	}
	
	/**
	 * 属性名
	 */
	public String getPropetyName() {
		return propetyName;
	}
	
	/**
	 * SQL片段
	 */
	public String getSubSqlText() {
		return subSqlText;
	}

	/**
	 * 开始符号
	 */
	public String getStartSymbol() {
		return startSymbol;
	}
	
	/**
	 * 开始符号
	 */
	public void setStartSymbol(String startSymbol) {
		this.startSymbol = startSymbol;
	}
	
	/**
	 * 结束符号
	 */
	public String getEndSymbol() {
		return endSymbol;
	}
	
	/**
	 * 结束符号
	 */
	public void setEndSymbol(String endSymbol) {
		this.endSymbol = endSymbol;
	}
	
	/**
	 * 分割符号
	 */
	public String getSpaceSymbol() {
		return spaceSymbol;
	}
	
	/**
	 * 分割符号
	 */
	public void setSpaceSymbol(String spaceSymbol) {
		this.spaceSymbol = spaceSymbol;
	}

	/**
	 * 标签名
	 */
	public String getTagName(){
		return "foreach";
	}
	
	/**
	 * 获得子属性数量
	 */
	public int getChildrenCount(){
		throw new UnsupportedOperationException("Operation not support");
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
	 * 输出到一个buffer中
	 */
	public void apend(StringBuffer buf){
		buf.append("<"+this.getTagName());
		buf.append("  item="+propetyName);
		buf.append("  open="+startSymbol);
		buf.append("  separator="+spaceSymbol);
		buf.append("  close="+endSymbol);
	 	buf.append("<" + this.getTagName() +">\n");
		buf.append("#{" +propetyName+"}\n");
		buf.append(subSqlText);
		buf.append("</" + this.getTagName() +">");
	}
}