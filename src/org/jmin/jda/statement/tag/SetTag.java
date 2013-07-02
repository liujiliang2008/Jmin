/*
* Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.jda.statement.tag;

import org.jmin.jda.statement.DynTag;

/**
 * 动态标签，更新节点
 * 
 * @author Chris Liao
 */

public class SetTag extends DynTag{

	/**
	 * 标签名
	 */
	public String getTagName(){
		return "set";
	}
}