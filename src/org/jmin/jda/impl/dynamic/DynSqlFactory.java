/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.dynamic;

import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jmin.jda.impl.JdaSessionImpl;
import org.jmin.jda.impl.exception.SqlExecutionException;
import org.jmin.jda.impl.property.OgnlPropertyUtil;
import org.jmin.jda.impl.property.PropertyUtil;
import org.jmin.jda.impl.util.StringUtil;
import org.jmin.jda.mapping.ParamUnit;
import org.jmin.jda.statement.DynTag;
import org.jmin.jda.statement.tag.ChooseTag;
import org.jmin.jda.statement.tag.ForeachTag;
import org.jmin.jda.statement.tag.IfTag;
import org.jmin.jda.statement.tag.OtherwiseTag;
import org.jmin.jda.statement.tag.SetTag;
import org.jmin.jda.statement.tag.TextTag;
import org.jmin.jda.statement.tag.TrimTag;
import org.jmin.jda.statement.tag.WhenTag;
import org.jmin.jda.statement.tag.WhereTag;
import org.jmin.log.Logger;

/**
 * 动态SQL合成工厂
 * 
 * @author Chris liao
 */

public class DynSqlFactory {
	
	/**
	 * Logger
	 */
	private final static Logger logger = Logger.getLogger(DynSqlFactory.class);
	
	/**
	 * 分析动态SQL
	 */
  public static DynSqlResult crate(String sqlId,DynTag[]tags,Object paramObj,JdaSessionImpl session)throws SQLException{
  	DynSqlResult result = new DynSqlResult(sqlId,paramObj);
  	StringBuffer sqlBuf = new StringBuffer();
  	List paramUnitList = new ArrayList();
  	for(int i=0;i<tags.length;i++){
  		appendDynTag(sqlId,tags[i],paramObj,sqlBuf,paramUnitList);
  	}
  	
    String sql =sqlBuf.toString();
    sql=removeSuffix(sql,"where ");
    sql=removeSuffix(sql,"and ");
    sql=removeSuffix(sql,"or ");
    sql=removeSuffix(sql,",");
    sql=removeSuffix(sql,"(");
  	result.setSqlText(sql);
  	result.setParamUnits((ParamUnit[])paramUnitList.toArray(new ParamUnit[0]));
  	
  	logger.debug(sqlId,"Build a dyn SQL: " + result.getSqlText());
  	return result;
  }
  
  /**
   * 清理末尾无效字符
   */
  private static String removeSuffix(String sql,String suffix){
  	sql=sql.trim();
  	String sqlLower=sql.toLowerCase();
  	suffix = suffix.trim().toLowerCase();
  	if(sqlLower.endsWith(suffix)){
  		sql=sql.substring(0,sqlLower.lastIndexOf(suffix));
  	}
   return sql;
  }
  
  /**
   * 清理前端无效字符
   */
  private static String removePrefix(String sql,String prefix){
  	sql=sql.trim();
   	String sqlLower=sql.toLowerCase();
  	prefix = prefix.trim().toLowerCase();
  	if(sqlLower.startsWith(prefix)){
  		sql = sql.substring(prefix.length());
  	}
   return sql;
  }
  
  /**
   * 追加SQL
   */
  private static void appendDynTag(String sqlId,DynTag tag,Object paramObj,StringBuffer sqlBuf,List paramUnitList)throws SQLException {
  	if(tag instanceof TextTag){
  		addTextTag(sqlId,(TextTag)tag,paramObj,sqlBuf,paramUnitList);
  	}else if(tag instanceof IfTag){
  		addIfTag(sqlId,(IfTag)tag,paramObj,sqlBuf,paramUnitList);
   	}else if(tag instanceof SetTag){
   		addSetTag(sqlId,(SetTag)tag,paramObj,sqlBuf,paramUnitList);
  	}else if(tag instanceof TrimTag){
  		addTrimTag(sqlId,(TrimTag)tag,paramObj,sqlBuf,paramUnitList);
  	}else if(tag instanceof WhereTag){
  		addWhereTag(sqlId,(WhereTag)tag,paramObj,sqlBuf,paramUnitList);
  	}else if(tag instanceof ChooseTag){
  		addChooseTag(sqlId,(ChooseTag)tag,paramObj,sqlBuf,paramUnitList);
  	}else if(tag instanceof WhenTag){
  		addWhenTag(sqlId,(WhenTag)tag,paramObj,sqlBuf,paramUnitList);
  	}else if(tag instanceof OtherwiseTag){
  		addOtherwiseTag(sqlId,(OtherwiseTag)tag,paramObj,sqlBuf,paramUnitList);
  	}else if(tag instanceof ForeachTag){
  		addForeachTag(sqlId,(ForeachTag)tag,paramObj,sqlBuf,paramUnitList);
  	}
  }
  
  /**
   * Text
   */
  private static void addTextTag(String sqlId,TextTag tag,Object paramObj,StringBuffer sqlBuf,List paramUnitList)throws SQLException{
  	String text = tag.getText();
  	if(!StringUtil.isNull(text)) {
  		sqlBuf.append(" ");
  		text = text.trim();
			String sql = sqlBuf.toString().trim().toLowerCase();
			if(sql.endsWith("where")){
				if(text.toLowerCase().startsWith("and "))
				  text=removePrefix(text,"and");
				if(text.toLowerCase().startsWith("and("))
					text=removePrefix(text,"and");
		
				if(text.toLowerCase().startsWith("or "))
				  text=removePrefix(text,"or");
				if(text.toLowerCase().startsWith("or("))
					text=removePrefix(text,"or");	 
				text=removePrefix(text,",");
				text=removePrefix(text,")");
				sqlBuf.append(" ");
			
			}
			
			//删除where
			if(sql.endsWith(" where") && (text.startsWith("order ") 
					 || text.startsWith("group ") 
					 || text.startsWith("having "))){
				
				String origSQL =sqlBuf.toString();
				origSQL=removeSuffix(origSQL,"where");
				sqlBuf = new StringBuffer();
				sqlBuf.append(origSQL);
			}
 
			sqlBuf.append(text);
			ParamUnit[] paramUnits = tag.getParamUnit();
			if (paramUnits != null) {
				for (int i = 0; i < paramUnits.length; i++)
					paramUnitList.add(paramUnits[i]);
			}
		}
  }
  
  /**
   * If
   */
  private static void addIfTag(String sqlId,IfTag tag,Object paramObj,StringBuffer sqlBuf,List paramUnitList)throws SQLException{
  	try {
		  if(OgnlPropertyUtil.assertBool(tag.getExpression(),paramObj)){
			DynTag[]childRen = tag.getChildren();
			for(int i=0;i<childRen.length;i++)
			appendDynTag(sqlId,childRen[i],paramObj,sqlBuf,paramUnitList);
		  }
  	}catch(SQLException e){
  		throw e;
  	} catch (Throwable e) {
			throw new SqlExecutionException(sqlId,"Failed to execute dynamic sql",e);
		}
  }
 
  /**
   * Set
   */
  private static void addSetTag(String sqlId,SetTag tag,Object paramObj,StringBuffer sqlBuf,List paramUnitList)throws SQLException{
  	try {
  		 sqlBuf.append(" set ");
  		 StringBuffer childBuff = new StringBuffer();
  		 DynTag[]childRen = tag.getChildren();
		  for(int i=0;i<childRen.length;i++)
		   appendDynTag(sqlId,childRen[i],paramObj,childBuff,paramUnitList);
		  String childSQL =childBuff.toString();
		  childSQL=removeSuffix(childSQL,",");
		  childSQL=removePrefix(childSQL,",");
		  sqlBuf.append(childSQL);
  	}catch(SQLException e){
  	   throw e;
  	} catch (Throwable e) {
	   throw new SqlExecutionException(sqlId,"Failed to execute dynamic sql",e);
	}
  }
  
  /**
   * Trim
   */
  private static void addTrimTag(String sqlId,TrimTag tag,Object paramObj,StringBuffer sqlBuf,List paramUnitList)throws SQLException{
  	try {
		DynTag[]childRen = tag.getChildren();
		sqlBuf.append(" "+tag.getPrefixSymbol());
		
		for(int i=0;i<childRen.length;i++)
		appendDynTag(sqlId,childRen[i],paramObj,sqlBuf,paramUnitList);
  	}catch(SQLException e){
  		throw e;
  	} catch (Throwable e) {
  		throw new SqlExecutionException(sqlId,"Failed to execute dynamic sql",e);
  	}
  }
  
  /**
   * Where
   */
  private static void addWhereTag(String sqlId,WhereTag tag,Object paramObj,StringBuffer sqlBuf,List paramUnitList)throws SQLException{
  	try {
  	StringBuffer childbuff =new StringBuffer();
		DynTag[]childRen = tag.getChildren();
		for(int i=0;i<childRen.length;i++)
		 appendDynTag(sqlId,childRen[i],paramObj,childbuff,paramUnitList);
		
		String child =childbuff.toString();
		child = child.trim().toLowerCase();
		if(child.length()>0){
			if(child.toLowerCase().startsWith("and "))
				child = removePrefix(child, "and");
			if (child.toLowerCase().startsWith("and("))
				child = removePrefix(child, "and");
			if (child.toLowerCase().startsWith("or "))
				child = removePrefix(child, "or");
			if (child.toLowerCase().startsWith("or("))
			  child = removePrefix(child, "or");
			child = removePrefix(child, ",");
			child = removePrefix(child, ")");
		}
		
		if(child.length()>0){
			sqlBuf.append(" where ");
			sqlBuf.append(child);
		}
		
  	}catch(SQLException e){
  		throw e;
  	} catch (Throwable e) {
  		throw new SqlExecutionException(sqlId,"Failed to execute dynamic sql",e);
  	}
  }
  
  /**
   * Choose
   */
  private static void addChooseTag(String sqlId,ChooseTag tag,Object paramObj,StringBuffer sqlBuf,List paramUnitList)throws SQLException{
  	try {
  		boolean appendWhenTag=false;
  		WhenTag[] whenTags = tag.getSubWhenTags();
  		OtherwiseTag otherTag = tag.getOtherwiseTag();
  		for(int i=0;i<whenTags.length;i++){
  			if(OgnlPropertyUtil.assertBool(whenTags[i].getExpression(),paramObj)){
  				appendDynTag(sqlId,whenTags[i],paramObj,sqlBuf,paramUnitList);
  				appendWhenTag =true;
  				break;
  			}
  		}
  		
  		if(!appendWhenTag){//没有追加到when Tag
  			appendDynTag(sqlId,otherTag,paramObj,sqlBuf,paramUnitList);
  		}
  	}catch(SQLException e){
  		throw e;
  	} catch(Throwable e) {
  		throw new SqlExecutionException(sqlId,"Failed to execute dynamic sql",e);
  	}
  }
  
  /**
   * When
   */
  private static void addWhenTag(String sqlId,WhenTag tag,Object paramObj,StringBuffer sqlBuf,List paramUnitList)throws SQLException{
  	try{
		if(OgnlPropertyUtil.assertBool(tag.getExpression(),paramObj)){
			DynTag[]childRen = tag.getChildren();
			for(int i=0;i<childRen.length;i++)
			appendDynTag(sqlId,childRen[i],paramObj,sqlBuf,paramUnitList);
		}
  	}catch(SQLException e){
  		throw e;
  	} catch (Throwable e) {
			throw new SqlExecutionException(sqlId,"Failed to execute dynamic sql",e);
		}
  }
  
  /**
   * Otherwise
   */
  private static void addOtherwiseTag(String sqlId,OtherwiseTag tag,Object paramObj,StringBuffer sqlBuf,List paramUnitList)throws SQLException{
  	try {
		 DynTag[]childRen = tag.getChildren();
		 for(int i=0;i<childRen.length;i++)
		 appendDynTag(sqlId,childRen[i],paramObj,sqlBuf,paramUnitList);
  	}catch(SQLException e){
  		throw e;
  	} catch (Throwable e) {
			throw new SqlExecutionException(sqlId,"Failed to execute dynamic sql",e);
		}
  }
  
  /**
   * Foreach
   */
  private static void addForeachTag(String sqlId,ForeachTag tag,Object paramObj,StringBuffer sqlBuf,List paramUnitList)throws SQLException{
  	try {
	  	
	  	String seperator = tag.getSpaceSymbol();
	  	String propertyName =tag.getPropetyName();
	  	Object propertyValue = PropertyUtil.getPropertyValue(paramObj,propertyName);
	  	StringBuffer tempBuffer = new StringBuffer();
	  	tempBuffer.append(tag.getStartSymbol());
	  	addEachItems(propertyValue,seperator,tempBuffer);
	  	tempBuffer.append(tag.getEndSymbol());
	  	String oginSQL = tag.getSubSqlText();
	  	String targetSQLBlock="";
	  	
	  	String itemDesc1 ="#{"+propertyName+"}";
		  String itemDesc2 ="${"+propertyName+"}";
		  String itemDesc3 ="#"+propertyName+"#";
	  	
	  	if(oginSQL.indexOf(itemDesc1)!=-1){
	  		targetSQLBlock = StringUtil.replace(oginSQL,itemDesc1,tempBuffer.toString());
	  	}else if (oginSQL.indexOf(itemDesc2)!=-1){
	  		targetSQLBlock = StringUtil.replace(oginSQL,itemDesc2,tempBuffer.toString());
	  	
	  	}else if (oginSQL.indexOf(itemDesc3)!=-1){
	  		targetSQLBlock = StringUtil.replace(oginSQL,itemDesc3,tempBuffer.toString());
	  	}
	  	
	  	sqlBuf.append(targetSQLBlock);
  	} catch (Throwable e) {
			throw new SqlExecutionException(sqlId,"Failed to execute dynamic sql",e);
		}
  }
  
  private static void addEachItems(Object propertyValue,String seperator,StringBuffer tempBuffer){
  	if(Collection.class.isInstance(propertyValue)){
  		Collection col =(Collection)propertyValue;
  		int size = col.size();
  		int counter=1;
  		Iterator itor = col.iterator();
  		while(itor.hasNext()){
  			addEachItem(itor.next(),tempBuffer);
  			if(counter < size)
  				tempBuffer.append(seperator);
  				counter++;
  		}
  		
  	}if(Map.class.isInstance(propertyValue)){
  		Map map =(Map)propertyValue;
  		Iterator itor = map.values().iterator();
  		int counter=1;
  		int size = map.size();

  		while(itor.hasNext()){
  			addEachItem(itor.next(),tempBuffer);
  			if(counter < size)
  				tempBuffer.append(seperator);
  				counter++;
  		}
  	}if(propertyValue.getClass().isArray()){
  		int size = Array.getLength(propertyValue);
  		int counter=1;
  		for(int i=0;i<=size;i++){
  			addEachItem(Array.get(propertyValue,i-1),tempBuffer);
  			if(counter < size)
  				tempBuffer.append(seperator);
  				counter++;
  		}
  	}else{
  		addEachItem(propertyValue,tempBuffer);
  	}
  }
  
  private static void addEachItem(Object item,StringBuffer tempBuffer){
  	if(item instanceof String){
  		tempBuffer.append("'"+(String)item + "'");
  	}else{
  		tempBuffer.append(item);
  	}
  }
}
