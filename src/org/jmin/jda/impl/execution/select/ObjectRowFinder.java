/*
* Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.jda.impl.execution.select;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.jmin.jda.JdaRowHandler;
import org.jmin.jda.impl.JdaSessionImpl;
import org.jmin.jda.impl.execution.SqlRequest;
import org.jmin.jda.impl.execution.SqlRequestFactory;
import org.jmin.jda.impl.execution.SqlRequestHandler;
import org.jmin.jda.impl.execution.worker.ResultFactory;
import org.jmin.jda.impl.statement.SqlBaseStatement;
import org.jmin.jda.impl.statement.SqlOperationType;
import org.jmin.jda.impl.util.CloseUtil;

/**
 * 执行Row Handler操作
 * 
 * @author Chris Liao
 */
public class ObjectRowFinder {

	/**
	 * 执行Row Handler操作
	 */
	public static void find(JdaSessionImpl session,SqlBaseStatement statement,Object paramObject,JdaRowHandler handler)throws SQLException{
		ResultSet resultSet = null;
		SqlRequest request=SqlRequestFactory.createSqlRequest(session,statement,paramObject,SqlOperationType.Select);
		
		try {
			resultSet=SqlRequestHandler.handleQueryRequest(request);
			while(resultSet.next()){
				handler.handleRow(ResultFactory.readResultObject(session,request.getConnection(),statement,resultSet,null));
			}
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
