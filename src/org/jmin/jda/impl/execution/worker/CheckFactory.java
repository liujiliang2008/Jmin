/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.execution.worker;

import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;

import org.jmin.jda.impl.JdaSessionImpl;
import org.jmin.jda.impl.exception.ParamMapException;
import org.jmin.jda.impl.exception.ResultMapException;
import org.jmin.jda.impl.exception.SqlIllegalAccessException;
import org.jmin.jda.impl.statement.SqlBaseStatement;
import org.jmin.jda.impl.statement.SqlDynStatement;
import org.jmin.jda.impl.statement.SqlOperationType;
import org.jmin.jda.impl.statement.SqlStaticStatement;
import org.jmin.jda.impl.util.ClassUtil;
import org.jmin.jda.mapping.ParamUnit;

/**
 * SQL执行过程的检查工厂
 * 
 * @author Chris
 * @version 1.0
 */

public class CheckFactory {
	
	/**
	 * 检查操作类型
	 */
	public static void checkOperationType(String id,SqlOperationType defineType,SqlOperationType requestType)throws SQLException{
		if(SqlOperationType.Insert.equals(requestType) && !SqlOperationType.Insert.equals(defineType)){//请求类型为: Insert
			throw new SqlIllegalAccessException(id,"Target definition is not a insert sql");
		}else if(SqlOperationType.Delete.equals(requestType)&& !SqlOperationType.Delete.equals(defineType)){//请求类型为: delete
			throw new SqlIllegalAccessException(id,"Target definition is not a delete sql");
		}else if(SqlOperationType.Update.equals(requestType)&& !SqlOperationType.Update.equals(defineType) && !SqlOperationType.Procedure.equals(defineType) && !SqlOperationType.Unknown.equals(defineType)){//请求类型为: update
			throw new SqlIllegalAccessException(id,"Target definition is not a update sql");
		}else if(SqlOperationType.Select.equals(requestType) && !SqlOperationType.Select.equals(defineType) && !SqlOperationType.Procedure.equals(defineType)){
			throw new SqlIllegalAccessException(id,"Target definition is not a select sql");
		}
	}

 /**
	* 检查参数对象是否正确
	*/
	public static void checkParamObject(JdaSessionImpl session,String id,SqlBaseStatement statement,Class paramClass,Object paramObject)throws SQLException{
		//针对静态定义的可以允许有数组参数
		if(statement instanceof SqlStaticStatement){
			SqlStaticStatement staticStatement = (SqlStaticStatement)statement;
			ParamUnit[] untis = staticStatement.getParamUnits();
			int unitLen =(untis==null)?0:untis.length;
			if((paramClass !=null || unitLen > 0) && paramObject==null){
				throw new ParamMapException(id,"Parameter object can't be null");
			}else if((paramClass !=null || unitLen > 0) && paramObject!=null){
				if(paramObject.getClass().isArray()){
					if(statement instanceof SqlDynStatement)
						throw new ParamMapException(id,"Dynamic sql don't support array type parameter");
					int arrayLen = Array.getLength(paramObject);
					if(unitLen!=arrayLen)
						throw new ParamMapException(id,"Parameter unit count("+untis.length+") can't match array parameter length["+arrayLen+"]");
				}else if(paramObject instanceof Collection){
					Collection col =(Collection)paramObject;
					int arrayLen = col.size();
					if(unitLen != arrayLen)
						throw new ParamMapException(id,"Parameter unit count("+untis.length+") can't match collection parameter length["+arrayLen+"]");
				}else if(paramObject instanceof Map){
					Map map =(Map)paramObject;
					int arrayLen = map.size();
					if(unitLen != arrayLen)
						throw new ParamMapException(id,"Parameter unit count("+untis.length+") can't match map parameter length["+arrayLen+"]");
				}else if(!(unitLen ==1 && session.supportsPersisterType(paramObject.getClass())) && !ClassUtil.isAcceptableInstance(paramClass,paramObject)){
					throw new ParamMapException(id,"Parameter object can't match paramter class["+paramClass+"]");
				} 
			}
		}else {
			if(paramObject==null)
			  throw new ParamMapException(id,"Parameter object can't be null");
			
			if(!ClassUtil.isAcceptableInstance(paramClass,paramObject)){
				throw new ParamMapException(id,"Parameter object can't match paramter class["+paramClass+"]");
			}
		}
	}
	
	/**
	 * 检查结果对象是否正确
	 */
	public static void checkResultObject(String id,Class resultClass,Object resultObject)throws SQLException{
		if(resultClass==null){
			throw new ResultMapException(id,"Result class can't be null");
		}else if(resultObject!=null && !ClassUtil.isAcceptableInstance(resultClass,resultObject)){
			throw new ResultMapException(id,"Result object can't match result class["+resultClass+"]");
		}
	}
}
