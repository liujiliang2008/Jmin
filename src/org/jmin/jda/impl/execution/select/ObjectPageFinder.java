/*
* Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.jda.impl.execution.select;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.jmin.jda.JdaPageList;
import org.jmin.jda.impl.JdaSessionImpl;
import org.jmin.log.Logger;
import org.jmin.jda.impl.exception.SqlExecutionException;
import org.jmin.jda.impl.execution.SqlRequest;
import org.jmin.jda.impl.execution.SqlRequestFactory;
import org.jmin.jda.impl.execution.SqlRequestHandler;
import org.jmin.jda.impl.statement.SqlBaseStatement;
import org.jmin.jda.impl.statement.SqlOperationType;
import org.jmin.jda.impl.util.CloseUtil;

/**
 * 分页执行查询
 * 
 * @author Chris Liao
 */

public class ObjectPageFinder {
	
	/**
	 * message Printer
	 */
	private static final Logger logger = Logger.getLogger(ObjectPageFinder.class);
	
	/**
	 * 执行一个分页查询
	 */
	public static JdaPageList find(JdaSessionImpl session,SqlBaseStatement statement,Object paramObject,int pageSize)throws SQLException{
		if(pageSize <=0)
			throw new SqlExecutionException(statement.getSqlId(),"Page size must be more than 0");
		
		SqlRequest request=SqlRequestFactory.createSqlRequest(session,statement,paramObject,SqlOperationType.Select);
		
		request.setSqlDialect(session.getSqlDialect());
		request.setDefinitionType(statement.getSqlType());
		int recordCount=getRecordCount(request);
		int totalSize = recordCount/pageSize;
		int remainSize = recordCount%pageSize; 
		if(remainSize >0)
			totalSize +=1;
		
		logger.debug("Execute page sql("+statement.getSqlId()+")Found page total size:"+totalSize);
		ObjectPageList pageList = new ObjectPageList(request,totalSize,pageSize,session);
		if(totalSize>0)
			pageList.initFirstPage();
		return pageList;
	}
	
	/**
	 * 获得记录总数
	 */
	public static int getRecordCount(JdaSessionImpl session,SqlBaseStatement statement,Object paramObject)throws SQLException{
		SqlRequest request=SqlRequestFactory.createSqlRequest(session,statement,paramObject,SqlOperationType.Select);
		request.setSqlDialect(session.getSqlDialect());
		request.setDefinitionType(statement.getSqlType());
		return getRecordCount(request);
	}

	/**
	 * 返回某个查询将会返回多少记录
	 */
	private static int getRecordCount(SqlRequest request)throws SQLException{
		ResultSet resultSet=null;
		SqlRequest countRequest = new SqlRequest(request.getRequestSession(),request.getSqlId(),request.getParamClass(),request.getParamObject());
		JdaSessionImpl session=countRequest.getRequestSession();
		
		try {
			countRequest.setSqlText("select count(*) from("+ request.getSqlText()+")as total");
			countRequest.setDefinitionType(request.getDefinitionType());
			countRequest.setParamValues(request.getParamValues());
			countRequest.setSqlDialect(request.getSqlDialect());
			countRequest.setConnection(request.getConnection());
 
			countRequest.setParamNames(request.getParamNames());
			countRequest.setParamValueTypes(request.getParamValueTypes());
			countRequest.setParamSqlTypeCodes(request.getParamSqlTypeCodes());
			countRequest.setParamValueModes(request.getParamValueModes());
			countRequest.setParamTypePersisters(request.getParamTypePersisters());
			
			resultSet=SqlRequestHandler.handleQueryRequest(countRequest);
			resultSet.next();
			return resultSet.getInt(1);
		}finally{
			if(resultSet!= null){
				CloseUtil.close(resultSet);
				resultSet=null;
			}
			
			if(countRequest!=null && countRequest.getConnection()!= null){
				session.releaseConnection(countRequest.getConnection());
				countRequest.setConnection(null);
			}
		}
	}
}
		