/*
* Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.jda.impl.execution;
import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;

import org.jmin.jda.JdaTypePersister;
import org.jmin.jda.impl.JdaSessionImpl;
import org.jmin.jda.impl.dynamic.DynSqlFactory;
import org.jmin.jda.impl.dynamic.DynSqlResult;
import org.jmin.jda.impl.execution.worker.CheckFactory;
import org.jmin.jda.impl.execution.worker.ParamFactory;
import org.jmin.jda.impl.statement.SqlBaseStatement;
import org.jmin.jda.impl.statement.SqlDynStatement;
import org.jmin.jda.impl.statement.SqlOperationType;
import org.jmin.jda.impl.statement.SqlStaticStatement;
import org.jmin.jda.mapping.ParamMap;
import org.jmin.jda.mapping.ParamUnit;
import org.jmin.jda.mapping.ParamValueMode;

/**
 * SQL请求构造工厂
 * 
 * @author Chris Liao
 */

public class SqlRequestFactory {
	
	/**
	 * 构造一个SQL请求
	 */
	public static SqlRequest createSqlRequest(JdaSessionImpl session,SqlBaseStatement statement,Object paramObj,SqlOperationType requestType)throws SQLException{
		String sqlId=statement.getSqlId();
		CheckFactory.checkOperationType(sqlId,statement.getSqlType(),requestType);
		CheckFactory.checkParamObject(session,sqlId,statement,statement.getParamClass(),paramObj);
		
		SqlRequest request = new SqlRequest(session,sqlId,statement.getParamClass(),paramObj);
		String targetSQL=null;
		ParamUnit[] paramUnits=null;
		
		if(statement instanceof SqlStaticStatement){
			SqlStaticStatement st = (SqlStaticStatement)statement;
			targetSQL = st.getSql();
			ParamMap paramMap = st.getParamMap();
			if(paramMap!=null)
				paramUnits = paramMap.getParamUnits();
			request.setDynSQL(false);
		}else{
			SqlDynStatement st =(SqlDynStatement)statement;
			DynSqlResult result = DynSqlFactory.crate(sqlId,st.getDynTags(),request.getParamObject(),request.getRequestSession());
			targetSQL = result.getSqlText();
			paramUnits=result.getParamUnits();
			request.setDynSQL(true);
		}

		request.setSqlText(targetSQL);
		initRequest(request,paramUnits);
		return request;
	}
	
	/**
	 * 初始Request,主要设置一些Request中的成员
	 */
  private static void initRequest(SqlRequest request,ParamUnit[] paramUnits)throws SQLException{
  	String sqlID = request.getSqlId();
  	Object paramObject = request.getParamObject();
   	JdaSessionImpl session = request.getRequestSession();
   	
		//设置参数属性名
		String[] paramNames = new String[paramUnits==null?0:paramUnits.length];
		request.setParamNames(paramNames);
		for(int i=0;i<paramNames.length;i++)
			paramNames[i]=paramUnits[i].getPropertyName();
		
		//设置参数属值
		Object[] paramValues = new Object[paramUnits==null?0:paramUnits.length];
		request.setParamValues(paramValues);
		if(paramObject!=null){
			if (paramObject.getClass().isArray()) {
				for (int i = 0; i < paramValues.length; i++)
					paramValues[i] = Array.get(paramObject, i);
			} else if (paramObject instanceof Collection) {
				Collection col = (Collection) paramObject;
				Object[] values = col.toArray();
				for (int i = 0; i < paramValues.length; i++)
					paramValues[i] = values[i];
			} else if (paramObject instanceof Map) {
				Map map = (Map) paramObject;
				for (int i = 0; i < paramValues.length; i++)
					paramValues[i] = map.get(paramNames[i]);
			}else if(paramValues.length ==1 && session.supportsPersisterType(paramObject.getClass())){
				paramValues[0] = paramObject;
	  	}else{
				for(int i=0;i<paramValues.length;i++)
				 paramValues[i]= ParamFactory.getPropertyValue(sqlID,paramObject,paramUnits[i].getPropertyName());
		  }
		}
	
		//设置参数属性类型
		Class[] paramTypes = new Class[paramUnits==null?0:paramUnits.length];
		request.setParamValueTypes(paramTypes);
		for(int i=0;i<paramTypes.length;i++){
			if(paramUnits[i].getPropertyType()!=null)
			 paramTypes[i]= paramUnits[i].getPropertyType();
			else
			 paramTypes[i]= (paramValues[i]==null)?Object.class:paramValues[i].getClass();
		}

		//设置参数属性数据库的SQL类型代码
		int[] sqlTypes = new int[paramUnits==null?0:paramUnits.length];
		request.setParamSqlTypeCodes(sqlTypes);
		for(int i=0;i<sqlTypes.length;i++)
			sqlTypes[i] = paramUnits[i].getParamColumnTypeCode();
		
		
		//设置参数属性参数值模式
  	ParamValueMode[] modes = new ParamValueMode[paramUnits==null?0:paramUnits.length];
  	request.setParamValueModes(modes);
  	for(int i=0;i<modes.length;i++)
  		modes[i]=paramUnits[i].geParamValueMode();
  
		//设置参数持久器
  	JdaTypePersister[] persisters = new JdaTypePersister[paramUnits==null?0:paramUnits.length];
  	request.setParamTypePersisters(persisters);
  	for(int i=0;i<persisters.length;i++){
  		if(paramUnits[i].getJdbcTypePersister()!=null){
  			persisters[i]=paramUnits[i].getJdbcTypePersister();
  		}else{
  			Class paramType = request.getParamValueTypes()[i];
  			
  		  persisters[i]= session.getTypePersister(paramType,paramUnits[i].getParamColumnTypeName());
  		}
		}
  }
}
