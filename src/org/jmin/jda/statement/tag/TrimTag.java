/*
* Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.jda.statement.tag;

import java.util.Iterator;

import org.jmin.jda.statement.DynTag;

/**
 * 动态标签
 * 
 * @author Chris Liao
 */

public class TrimTag extends DynTag{
	
	/**
	 * 前缀填加部分
	 */
	private String prefixSymbol="WHERE"; 
	
	/**
	 * 排除重复多余的字符
	 */
	private String prefixOverrides="AND|OR"; 
	
	/**
	 * 标签名
	 */
	public String getTagName(){
		return "trim";
	}
	
	/**
	 * 前缀填加部分
	 */
	public String getPrefixSymbol() {
		return prefixSymbol;
	}
	
	/**
	 * 前缀填加部分
	 */
	public void setPrefixSymbol(String prefixSymbol) {
		this.prefixSymbol = prefixSymbol;
	}
	
	/**
	 * 排除重复多余的字符
	 */
	public String getPrefixOverride() {
		return prefixOverrides;
	}
	
	/**
	 * 排除重复多余的字符
	 */
	public void setPrefixOvreride(String prefixOverride) {
		this.prefixOverrides = prefixOverride;
	}

	/**
	 * 输出到一个buffer中
	 */
	public void apend(StringBuffer buf){
		buf.append("<"+this.getTagName());
		buf.append("  prefix="+prefixSymbol);
		buf.append("  prefixOverrides="+prefixSymbol);
		
		buf.append(">");
		Iterator itor = subList.iterator();
  	while(itor.hasNext()){
  		DynTag sub = (DynTag)itor.next();
  		sub.apend(buf);
  	}
  	buf.append("</" + this.getTagName() +">");
	}
}