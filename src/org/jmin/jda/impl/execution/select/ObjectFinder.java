/*
* Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.jda.impl.execution.select;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.jmin.jda.impl.JdaSessionImpl;
import org.jmin.jda.impl.exception.SqlExecutionException;
import org.jmin.jda.impl.execution.SqlRequest;
import org.jmin.jda.impl.execution.SqlRequestFactory;
import org.jmin.jda.impl.execution.SqlRequestHandler;
import org.jmin.jda.impl.execution.worker.CheckFactory;
import org.jmin.jda.impl.execution.worker.ResultFactory;
import org.jmin.jda.impl.statement.SqlBaseStatement;
import org.jmin.jda.impl.statement.SqlOperationType;
import org.jmin.jda.impl.util.CloseUtil;

/**
 * 单个对象查找
 * 
 * @author Chris Liao
 */
public class ObjectFinder {
	
	/**
	 * 查找单个对象，如果找到多个对象，将抛出异常,如果resultObject不为空，则需要在该对象注射列值
	 */
	public static Object find(JdaSessionImpl session,SqlBaseStatement statement,Object paramObj,Object resultObject)throws SQLException {
		ResultSet resultSet = null;
		SqlRequest request = null;
		
		try {

			String sqlId= statement.getSqlId();
		
			CheckFactory.checkResultObject(sqlId,statement.getResultClass(),resultObject);
			request=SqlRequestFactory.createSqlRequest(session,statement,paramObj,SqlOperationType.Select);
			resultSet=SqlRequestHandler.handleQueryRequest(request);
			
			if(resultSet.next())
				resultObject = ResultFactory.readResultObject(session,request.getConnection(),statement,resultSet,resultObject);

			if(resultSet.next())
				throw new SqlExecutionException(sqlId,"Found multiple records ");

			return resultObject;
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
