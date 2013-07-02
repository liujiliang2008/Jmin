/*
* Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.jda.impl.execution.worker;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import org.jmin.jda.JdaTypeConverterMap;
import org.jmin.jda.JdaTypePersister;
import org.jmin.jda.impl.JdaSessionImpl;
import org.jmin.jda.impl.exception.SqlExecutionException;
import org.jmin.jda.impl.execution.base.CallableResultSet;
import org.jmin.jda.impl.execution.base.JdbRowCacheImpl;
import org.jmin.jda.impl.property.PropertyException;
import org.jmin.jda.impl.property.PropertyUtil;
import org.jmin.jda.mapping.ParamValueMode;

/**
 * 参数辅助类
 * 
 * @author chris 
 */
public class ParamFactory {
	
	/**
	 * 获得属性值
	 */
	public static Object getPropertyValue(String sqlId,Object bean,String propertyName)throws SQLException{
		try {
			 if(propertyName!=null && propertyName.trim().length()>0){
				return PropertyUtil.getPropertyValue(bean,propertyName);
			}else{
				throw new PropertyException("Property info can't be null");
			}
		} catch (Throwable e) {
			throw new SqlExecutionException(sqlId,e);
		}
	}
	
	/**
	 * 设置参数值到PreparedStatement
	 */
	public static void setParamValues(JdaSessionImpl session,String sqlId,
			PreparedStatement statement,String[]paramNames,Object[]paramValues,int[] sqlTypes,
		  JdaTypePersister[]persisters,ParamValueMode[] paramValueModes)throws SQLException {
	
		JdaTypeConverterMap typeConverterMap = session.getTypeConverterMap();
		if(statement instanceof CallableStatement){//存储过程调用
			CallableStatement callStatement =(CallableStatement)statement;
			for(int i=0;paramValues!=null && i<paramValues.length;i++) {
				try {
					int typeCode = sqlTypes[i];
					ParamValueMode paramMode = paramValueModes[i];
					
					if(!ParamValueMode.IN.equals(paramMode)) 
						callStatement.registerOutParameter(i+1,typeCode);
					if(!ParamValueMode.OUT.equals(paramMode)){
						if(paramValues[i]!=null)
							persisters[i].set(statement,i+1,paramValues[i],typeCode,typeConverterMap);
						else
						 setNullParamValue(sqlId,statement,i+1,typeCode);
					}
						 
				}catch (Throwable e) {
					throw new SqlExecutionException(sqlId,"Failed to set callable statement parameter value on index:" +(i+1),e);
				}
			}
		}else{//普通SQL调用
			for (int i=0;paramValues!=null && i<paramValues.length;i++) {
				try {
					int typeCode = sqlTypes[i];
					if(paramValues[i]!=null)
						persisters[i].set(statement,i+1,paramValues[i],typeCode,typeConverterMap);
					else
						setNullParamValue(sqlId,statement,i+1,typeCode);
				}catch (Throwable e) {
					throw new SqlExecutionException(sqlId,"Failed to set prepared statement parameter value on index:" +(i+1),e);
				}
			}
		}
  }
 
	/**
	 * 从存储过程调用中获取Out,InOut参数的结果值
	 */
	public static void readCallStatement(JdaSessionImpl session,String sqlId,CallableStatement statement,Object paramObject,
			String[]paramNames,JdaTypePersister[]persisters,ParamValueMode[] paramValueModes )throws SQLException{
		
		CallableResultSet resultSert = new CallableResultSet(statement);
		JdaTypeConverterMap typeConverterMap = session.getTypeConverterMap();
		JdbRowCacheImpl recordRow = new JdbRowCacheImpl();
	
		for(int i=0;paramObject!=null && paramNames!=null && i < paramNames.length; i++) {
			try {
				   if(!ParamValueMode.IN.equals(paramValueModes[i])){
					   Object resultValue = persisters[i].get(resultSert,i+1,typeConverterMap,recordRow);
					   if(resultValue!=null)
						  ResultFactory.setPropertyValue(sqlId,paramObject,paramNames[i],resultValue);
				   }
			} catch (Throwable e) {
				throw new SqlExecutionException(sqlId,"Failed to read prameter out value on index:"+i,e);
			}
		}
	}
	
	private static int[]types= new int[]{
		Types.INTEGER,Types.BIGINT,Types.FLOAT,
		Types.REAL,Types.DOUBLE,Types.NUMERIC,
		Types.DECIMAL,Types.CHAR,Types.VARCHAR,
		Types.DATE,Types.TIME,Types.TIMESTAMP,
		Types.BLOB,Types.CLOB,Types.LONGVARCHAR,
		Types.BIT,Types.TINYINT,Types.SMALLINT,
		16,
		Types.JAVA_OBJECT,
		Types.BINARY,Types.VARBINARY,Types.LONGVARBINARY,
		Types.NULL,Types.OTHER,Types.DISTINCT,
		Types.STRUCT,Types.ARRAY,Types.REF};
	
	/**
	 * 设置Null值调用方法
	 */
	private static void setNullParamValue(String sqlId,PreparedStatement statement,int index,int typeCode)throws SQLException{
		boolean successful;
		try{
			statement.setNull(index,typeCode);
			successful=true;
		}catch(SQLException e){
			successful=false;
		}
		
		SQLException ee=null;
		if(!successful){
			for(int i=0;i<types.length;i++){
				try{
					statement.setNull(index,types[i]);
					successful=true;
					break;
				}catch(SQLException e){
					successful=false;
					ee=e;
					continue;
				}
			}
		}
		
		if(!successful&&ee!=null)
			throw ee;
	}
}
