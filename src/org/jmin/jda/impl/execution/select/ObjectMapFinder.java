/*
* Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.jda.impl.execution.select;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.jmin.jda.impl.JdaSessionImpl;
import org.jmin.jda.impl.exception.ResultMapException;
import org.jmin.jda.impl.exception.SqlExecutionException;
import org.jmin.jda.impl.execution.SqlRequest;
import org.jmin.jda.impl.execution.SqlRequestFactory;
import org.jmin.jda.impl.execution.SqlRequestHandler;
import org.jmin.jda.impl.execution.worker.ParamFactory;
import org.jmin.jda.impl.execution.worker.ResultFactory;
import org.jmin.jda.impl.property.PropertyException;
import org.jmin.jda.impl.property.PropertyUtil;
import org.jmin.jda.impl.statement.SqlBaseStatement;
import org.jmin.jda.impl.statement.SqlOperationType;
import org.jmin.jda.impl.util.ClassUtil;
import org.jmin.jda.impl.util.CloseUtil;
import org.jmin.jda.impl.util.StringUtil;
import org.jmin.log.Logger;

/**
 * 查询，结果返回一个Map
 * 
 * @author Chris
 */

public class ObjectMapFinder {
	
	/**
	 * logger
	 */
	private static Logger logger = Logger.getLogger(ObjectMapFinder.class);
	
	/**
	 * Map结果查询
	 */
	public static Map find(JdaSessionImpl session,SqlBaseStatement statement,Object paramObject,String keyPropName,String valuePropName)throws SQLException {
	 return find(session,statement,paramObject,keyPropName,valuePropName,Map.class);
	}
	
	/**
	 * Map结果查询
	 */
	public static Map find(JdaSessionImpl session,SqlBaseStatement statement,Object paramObject,String keyPropName,String valuePropName,Class mapClass)throws SQLException {
		ResultSet resultSet = null;
		String sqlId=statement.getSqlId();
		Class resultClass=statement.getResultClass();
	  
		if(!Map.class.isAssignableFrom(mapClass))
			throw new ResultMapException(statement.getSqlId(),"Result class must be child of Map.class");
		
		SqlRequest request=SqlRequestFactory.createSqlRequest(session,statement,paramObject,SqlOperationType.Select);
	  
		try {
			if(!StringUtil.isNull(keyPropName) && !Map.class.isAssignableFrom(resultClass))
				try {
					PropertyUtil.getPropertyType(resultClass,keyPropName);
				} catch (PropertyException e) {
					throw new SqlExecutionException(sqlId,"Failed find map key property["+keyPropName+"]",e);
				} 
			
			if(!StringUtil.isNull(valuePropName) && !Map.class.isAssignableFrom(resultClass))
				try {
					 PropertyUtil.getPropertyType(resultClass,valuePropName);
				} catch (PropertyException e) {
					throw new SqlExecutionException(sqlId,"Failed find map value property["+valuePropName+"]",e);
				} 
			
			Map resultMap =(Map)ClassUtil.createInstance(mapClass);
			resultSet=SqlRequestHandler.handleQueryRequest(request);
			
			while(resultSet.next()){
				Object resultObject = ResultFactory.readResultObject(session,request.getConnection(),statement,resultSet,null);
				Object keyValue = ParamFactory.getPropertyValue(sqlId,resultObject,keyPropName);
				if(!StringUtil.isNull(valuePropName))
				  resultObject = ParamFactory.getPropertyValue(sqlId,resultObject,valuePropName); 
				resultMap.put(keyValue,resultObject);
			}
			
			logger.debug(sqlId,"Find result map size: " + resultMap.size());
			return resultMap;
		
		}catch(InstantiationException e){
			throw new SqlExecutionException(sqlId,"Failed to create map instace",e);
		}catch(IllegalAccessException e){
			throw new SqlExecutionException(sqlId,"Failed to create map instace",e);
		}finally {
			if(resultSet!= null){
				CloseUtil.close(resultSet);
				resultSet=null;
			}
			
			if(request!=null && request.getConnection()!= null){
				session.releaseConnection(request.getConnection());
				request.setConnection(null);
			}
		}
	}
}
