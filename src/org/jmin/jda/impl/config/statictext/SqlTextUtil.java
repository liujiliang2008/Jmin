/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.config.statictext;

import org.jmin.jda.impl.exception.SqlDefinitionException;
import org.jmin.jda.impl.exception.SqlDefinitionFileException;

/**
 * SQL文本辅助类
 * 
 * @author Chris Liao
 */
public class SqlTextUtil {
	
	private static String[] dynTags = new String[] {
		"if",
		"where", 
		"choose",
		"when", 
		"otherwise", 
		"set", 
		"trim", 
		"foreach" };
	
	/**
	 * 获得SQL动态或动态类型
	 */
	public static boolean isDynamicText(String SQL){
   boolean existTagInd = (SQL.indexOf("<") > 0 && SQL.indexOf(">") > 0);
   if(existTagInd){
     SQL = SQL.toLowerCase();
     for(int i=0;i<dynTags.length;i++){
    	 if(SQL.indexOf("<"+dynTags[i]) > 0){
    		 return true;
    	 }
     }
   }
	 return false;
	}
	
	/**
	* 获得SQL动态或静态类型
	*/
	public static boolean isStaticText(String SQL){
	 return (SQL.indexOf("<")==-1 && SQL.indexOf(">")==-1);
	}
	
	/**
	 * 获取XML内容
	 */
	public static String removeCdata(String sqlID,String XML)throws SqlDefinitionException {
		int pos = XML.indexOf("CDATA");
		if(pos==-1){
			return XML;
		}else{
			pos = XML.indexOf("[",pos);
			if(pos==-1)
				throw new SqlDefinitionFileException(sqlID,"Error CDATA content");
			XML = XML.substring(pos+1);
			int endPos = XML.lastIndexOf("]");
			if(endPos==-1)
				throw new SqlDefinitionFileException(sqlID,"Error CDATA content");
			XML = XML.substring(0,endPos);
			
			endPos = XML.lastIndexOf("]");
			if(endPos==-1)
				throw new SqlDefinitionFileException(sqlID,"Error CDATA content");
			return XML.substring(0,endPos);
		}
	}
}
