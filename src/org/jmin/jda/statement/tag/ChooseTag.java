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

public class ChooseTag extends DynTag{
	
	/**
	 * 子节点
	 */
	private OtherwiseTag otherwiseTag;
	
	/**
	 * 标签名
	 */
	public String getTagName(){
		return "choose";
	}
	
	/**
	 * 增加子标签
	 */
	public void addWhenTag(WhenTag tag) {
		if(tag==null)
			throw new NullPointerException();
		 super.addChild(tag);
	}
	
	/**
	 * 删除子标签
	 */
	public void removeWhenTag(WhenTag tag) {
		 super.removeChild(tag);
	}
	
	/**
	 * 获得子属性数量
	 */
	public int getSubWhenTagCount(){
		return subList.size();
	}
	
	/**
	 * 获得子属性
	 */
	public WhenTag getSubWhenTag(int index){
		 return (WhenTag)super.getChildren(index);
	}
	/**
	 * 获得子属性
	 */
	public WhenTag[] getSubWhenTags(){
		return (WhenTag[])subList.toArray(new WhenTag[0]);
	}

	/**
	 * 子节点
	 */
	public OtherwiseTag getOtherwiseTag() {
		return otherwiseTag;
	}

	/**
	 * 子节点
	 */
	public void setOtherwiseTag(OtherwiseTag otherwiseTag) {
		this.otherwiseTag = otherwiseTag;
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
	 * 获得
	 */
	public void apend(StringBuffer buf){
		buf.append("<"+this.getTagName() +" >");
		Iterator itor = subList.iterator();
  	while(itor.hasNext()){
  		DynTag sub = (DynTag)itor.next();
  		sub.apend(buf);
  	}
  	
		if(otherwiseTag!=null)
			otherwiseTag.apend(buf);
  	buf.append("</" + this.getTagName() +">");
	}
}