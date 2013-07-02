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

public class IfTag extends DynTag{
	
	/**
	 * 测试脚本
	 */
	private String expression;
	
	/**
	 * 构造函数
	 */
	public IfTag(String expression){
		this.expression = expression;
	}

	/**
	 * 标签名
	 */
	public String getTagName(){
		return "if";
	}
 
  /**
	 * 测试脚本
	 */
  public String getExpression(){
  	return expression;
  }

	/**
	 * 获得
	 */
	public void apend(StringBuffer buf){
		buf.append("<"+this.getTagName());
		buf.append(" test="+expression);
		buf.append(">");
		
		Iterator itor = subList.iterator();
  	while(itor.hasNext()){
  		DynTag sub = (DynTag)itor.next();
  		sub.apend(buf);
  	}
  	buf.append("</" + this.getTagName() +">");
	}
}