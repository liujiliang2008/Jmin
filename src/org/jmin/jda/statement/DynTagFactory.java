/*
* Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.jda.statement;

import org.jmin.jda.impl.exception.SqlDynTagException;
import org.jmin.jda.mapping.ParamUnit;
import org.jmin.jda.statement.tag.ChooseTag;
import org.jmin.jda.statement.tag.ForeachTag;
import org.jmin.jda.statement.tag.IfTag;
import org.jmin.jda.statement.tag.OtherwiseTag;
import org.jmin.jda.statement.tag.SetTag;
import org.jmin.jda.statement.tag.TextTag;
import org.jmin.jda.statement.tag.TrimTag;
import org.jmin.jda.statement.tag.WhenTag;
import org.jmin.jda.statement.tag.WhereTag;

/**
 * 标签工厂
 * 
 * @author Chris
 */
public class DynTagFactory {
	
	/**
	 * 创建文本标签
	 */
	public static TextTag createTextTag(String text)throws SqlDynTagException{
		return createTextTag(text,null);
	}
	
	/**
	 * 创建文本标签
	 */
	public static TextTag createTextTag(String text,ParamUnit[] paramUnits)throws SqlDynTagException{
		return new TextTag(text,paramUnits);
	}

	/**
	 * 创建Where标签
	 */
	public static WhereTag createWhereTag()throws SqlDynTagException{
		return new WhereTag();
	}
	
	/**
	 * 创建Set标签
	 */
	public static SetTag createSetTag()throws SqlDynTagException{
		return new SetTag();
	}
	
	/**
	 * 创建trim标签
	 */
	public static TrimTag createTrimTag()throws SqlDynTagException{
		return new TrimTag();
	}
	
	
	/**
	 * 创建IF标签
	 */
	public static IfTag createIfTag(String expression)throws SqlDynTagException{
		return new IfTag(expression);
	}
	
	/**
	* 创建Choose标签
	*/
	public static ChooseTag crateChooseTag()throws SqlDynTagException{
		return new ChooseTag();
	}
	
	/**
	 * 创建When标签
	 */
	public static WhenTag crateWhenTag(String expression)throws SqlDynTagException{
		return new WhenTag(expression);
	}
	
	/**
	 * 创建otherwise标签
	 */
	public static OtherwiseTag crateOtherwiseTag()throws SqlDynTagException{
		return new OtherwiseTag();
	}

	/**
	 * 创建ForeachTag标签
	 */
	public static ForeachTag createForeachTag(String propetyName,String sqlBlockText)throws SqlDynTagException{
		return new ForeachTag(propetyName,sqlBlockText);
	}
}
