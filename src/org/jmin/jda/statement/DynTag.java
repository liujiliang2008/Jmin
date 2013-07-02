/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.statement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 动态标签
 * 
 * @author Chris
 */
public abstract class DynTag {
	
	/**
	 * 子标签列表
	 */
	protected List subList = new ArrayList();

	/**
	 * 标签名
	 */
	public abstract String getTagName();
	
	/**
	 * 增加子标签
	 */
	public void addChild(DynTag tag) {
		if(tag==null)
			throw new NullPointerException();
		subList.add(tag);
	}
	
	/**
	 * 获得子属性数量
	 */
	public int getChildrenCount(){
		return subList.size();
	}
	
	/**
	 * 删除子标签
	 */
	public void removeChild(DynTag tag) {
		subList.remove(tag);
	}
	
	/**
	 * 获得子属性
	 */
	public DynTag getChildren(int index){
		return (DynTag)subList.get(index);
	}
	
	/**
	 * 获得子属性
	 */
	public DynTag[] getChildren(){
		return (DynTag[])subList.toArray(new DynTag[0]);
	}
	
	/**
	 * 获得
	 */
	public void apend(StringBuffer buf){
		buf.append("<"+this.getTagName()+">");
		Iterator itor = subList.iterator();
  	while(itor.hasNext()){
  		DynTag sub = (DynTag)itor.next();
  		sub.apend(buf);
  	}
  	buf.append("</" + this.getTagName() +">");
	}
}
