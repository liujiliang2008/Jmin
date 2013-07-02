/*
* Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.jda.impl.execution.select;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import org.jmin.jda.JdaDialect;
import org.jmin.jda.impl.JdaSessionImpl;
import org.jmin.jda.impl.exception.SqlExecutionException;
import org.jmin.jda.impl.exception.ResultMapException;
import org.jmin.jda.impl.execution.SqlRequest;
import org.jmin.jda.impl.execution.SqlRequestFactory;
import org.jmin.jda.impl.execution.SqlRequestHandler;
import org.jmin.jda.impl.execution.worker.ResultFactory;
import org.jmin.jda.impl.statement.SqlBaseStatement;
import org.jmin.jda.impl.statement.SqlOperationType;
import org.jmin.jda.impl.util.ClassUtil;
import org.jmin.jda.impl.util.CloseUtil;
import org.jmin.log.Logger;

/**
 * 查询，结果返回一个List
 * 
 * @author Chris
 */

public class ObjectListFinder{
	
	/**
	 * logger
	 */
	private static Logger logger = Logger.getLogger(ObjectListFinder.class);
	
	/**
	 * 多结果的查询，返回一个List
	 */
	public static List find(JdaSessionImpl session,SqlBaseStatement statement,Object paramObject,int skip,int rowNum)throws SQLException {
		return (List)find(session,statement,paramObject,skip,rowNum,List.class);
	}
	
	/**
	 * 多结果的查询，返回一个List
	 */
	public static Collection find(JdaSessionImpl session,SqlBaseStatement statement,Object paramObject,int skip,int rowNum,Class listClass)throws SQLException {
		ResultSet resultSet=null;
		String sqlId=statement.getSqlId();
		JdaDialect dialect=session.getSqlDialect();
		SqlOperationType definitionType=statement.getSqlType();
		
		if (!Collection.class.isAssignableFrom(listClass))
			throw new ResultMapException(sqlId,"Result class must be child of Collection.class");
		
		SqlRequest request=SqlRequestFactory.createSqlRequest(session,statement,paramObject,SqlOperationType.Select);
		
		if(skip<=0 && rowNum >0)skip=1;
		request.setRecordSkipPos(skip);
		request.setRecordMaxRows(rowNum);
		request.setSqlDialect(dialect);
		request.setDefinitionType(statement.getSqlType());
		
		try {
			resultSet=SqlRequestHandler.handleQueryRequest(request);		
			Collection collection =(Collection)ClassUtil.createInstance(listClass);
			
			int readCount=0,skipCount=0;
			boolean needCount=((dialect==null || SqlOperationType.Procedure.equals(definitionType)) && rowNum>0)?true:false;
			boolean needSkip=((dialect==null || SqlOperationType.Procedure.equals(definitionType)) && skip > 1 )?true:false;
			if(needSkip){
				if(session.supportScrollable()){
			    resultSet.first();//将游标移动到第一条记录
			    resultSet.relative(skip-2);//游标移动到要输出的第一条记录
				}else{
					while(resultSet.next()){
						if(skipCount++==skip)
							break;
					}
				}
			}

			while(resultSet.next()) {
				collection.add(ResultFactory.readResultObject(session,request.getConnection(),statement,resultSet,null));
				if(needCount){
					if(++readCount == rowNum)
						break;
				}
			}

			
			logger.debug(sqlId,"Find result list size: " + collection.size());
			return collection;
		}catch(InstantiationException e){
			throw new SqlExecutionException(sqlId,"Failed to create list instace",e);
		}catch(IllegalAccessException e){
			throw new SqlExecutionException(sqlId,"Failed to create list instace",e);
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