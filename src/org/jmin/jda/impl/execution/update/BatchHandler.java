/*
* Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.jda.impl.execution.update;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jmin.jda.JdaTypePersister;
import org.jmin.jda.impl.JdaSessionImpl;
import org.jmin.jda.impl.execution.SqlRequest;
import org.jmin.jda.impl.execution.SqlRequestHandler;
import org.jmin.jda.impl.execution.worker.ParamFactory;
import org.jmin.jda.impl.util.CloseUtil;
import org.jmin.jda.mapping.ParamValueMode;
import org.jmin.log.Logger;

/**
 * 批量更新操作执行器
 * 
 * @author Chris 
 */

public class BatchHandler {
	
	/**
	 * 批量更新Size
	 */
	private int batchUpdateSize= 10;
	
	/**
	 * 存放需要批量执行的请求
	 */
	private List batchRequestList = new ArrayList();
	
	/**
	 * message Printer
	 */
	private static final Logger logger = Logger.getLogger(BatchHandler.class);
	
	/**
	 * 构造函数
	 */
	public BatchHandler(int batchUpdateSize){
		this.batchUpdateSize = batchUpdateSize;
	}

	/**
	 * 增加一个Item
	 */
	public void addBatchRequest(SqlRequest request){
	  this.batchRequestList.add(request);
	}
	
	/**
	 * 执行批量操作
	 */
	public int execute(Connection con,JdaSessionImpl session) throws SQLException {
		if(session.supportBatch())
	    return this.executeBatchUpdate(con);
	  else{
	    return this.executeGeneralUpdate(con);
	  }
	}
	
	/**
	 * 执行批量操作
	 */
	public int executeBatchUpdate(Connection con) throws SQLException {
		int totalUpdatedRows=0;
		int currentBatchCount=0;
		String preSql="",curSql="";
		PreparedStatement updateStatement = null;
		int[]batchRows=null;
		
		try {
			Iterator itor = batchRequestList.iterator();
			while(itor.hasNext()) {
				SqlRequest curReqest = (SqlRequest)itor.next();
				curSql = curReqest.getSqlText();
				String sqlId=curReqest.getSqlId();
				String[]paramNames=curReqest.getParamNames();
				Object[]paramValues=curReqest.getParamValues();
		 	  int[] paramSqlTypeCodes=curReqest.getParamSqlTypeCodes();
		 	  JdaTypePersister[] paramTypePersisters=curReqest.getParamTypePersisters();
		 	  ParamValueMode[] paramValueModes=curReqest.getParamValueModes();
		 	  JdaSessionImpl session=curReqest.getRequestSession();
		 	  
				/**
				 * 满足批量更新的条件如下：
				 * 1:上一个Statement更新进去的数据已经达到Size,则需要执行前面Statement,
				 * 2:当前的SQL与前面执行SQL不同时候，则需要执行前面Statement,
				 * 3:到达数据列表的终点，不论1与2，都需要执行当前的Statement
				 */
			
				if(updateStatement==null)
					 updateStatement  = SqlRequestHandler.createGeneralStatement(con,curSql);
				
				if(currentBatchCount == batchUpdateSize){//Size已满,需要执行批量更新了
					batchRows = updateStatement.executeBatch();
					totalUpdatedRows +=getUpdateCount(batchRows);
					updateStatement.clearBatch();
					currentBatchCount = 0;
				}
				
				if(curSql.equalsIgnoreCase(preSql)){
					ParamFactory.setParamValues(session,sqlId,updateStatement,paramNames,paramValues,paramSqlTypeCodes,paramTypePersisters,paramValueModes);
					updateStatement.addBatch();
					currentBatchCount++;
					preSql = curSql;
				}else	if(!curSql.equalsIgnoreCase(preSql)){
					batchRows = updateStatement.executeBatch();
					totalUpdatedRows +=getUpdateCount(batchRows);
					updateStatement.clearBatch();
					currentBatchCount = 0;
					CloseUtil.close(updateStatement);
					updateStatement =null;
					
					updateStatement= SqlRequestHandler.createGeneralStatement(con,curSql);
					ParamFactory.setParamValues(session,sqlId,updateStatement,paramNames,paramValues,paramSqlTypeCodes,paramTypePersisters,paramValueModes);					
					currentBatchCount++;
					preSql = curSql;
				} 
			}
			
			if(currentBatchCount > 0){
				batchRows = updateStatement.executeBatch();
				totalUpdatedRows +=getUpdateCount(batchRows);
				updateStatement.clearBatch();
				currentBatchCount = 0;
				CloseUtil.close(updateStatement);
				updateStatement=null;
			}
		}catch(SQLException e){  
			if(updateStatement!=null){
				try {
					updateStatement.clearBatch();
					CloseUtil.close(updateStatement);
					updateStatement=null;
				} catch (Exception ee) {
				}
			}
			throw e;
		}
	
		return totalUpdatedRows;
	}
	
	/**
	 * 获取影响函数
	 */
	private int getUpdateCount(int[]batchRows){
		int count=0;
		for(int i=0;i<batchRows.length;i++){
			if(batchRows[i]>=0)
				count +=batchRows[i];
		}
		return count;
	}

	/**
	 * 正常情况下，一条一条执行
	 */
	private int executeGeneralUpdate(Connection con)throws SQLException{
		int updatedRows,totalUpdatedRows=0;
		String preSqlText="",curSqlText ="";
		PreparedStatement statement = null;
		
		try {
			Iterator itor = batchRequestList.iterator();
			while (itor.hasNext()) {
				SqlRequest curReqest = (SqlRequest) itor.next();
				String sqlId=curReqest.getSqlId();
				curSqlText = curReqest.getSqlText();
				Object paramObject=curReqest.getParamObject();
				String[]paramNames=curReqest.getParamNames();
				Object[]paramValues=curReqest.getParamValues();
		 	  int[] paramSqlTypeCodes=curReqest.getParamSqlTypeCodes();
		 	  JdaTypePersister[] paramTypePersisters=curReqest.getParamTypePersisters();
		 	  ParamValueMode[] paramValueModes=curReqest.getParamValueModes();
		 	  JdaSessionImpl session=curReqest.getRequestSession();
				
				if(statement==null)
					 statement =SqlRequestHandler.createGeneralStatement(con,curSqlText);
	
				if(!curSqlText.equals(preSqlText)){
					CloseUtil.close(statement);
					statement = null;
					statement = SqlRequestHandler.createGeneralStatement(con,curSqlText);
					
					ParamFactory.setParamValues(session,sqlId,statement,paramNames,paramValues,paramSqlTypeCodes,paramTypePersisters,paramValueModes);
					updatedRows = statement.executeUpdate();
					preSqlText = curSqlText;
					
					totalUpdatedRows = totalUpdatedRows+updatedRows;
				}else{
					ParamFactory.setParamValues(session,sqlId,statement,paramNames,paramValues,paramSqlTypeCodes,paramTypePersisters,paramValueModes);
					updatedRows =statement.executeUpdate();
					
					if(statement instanceof CallableStatement && curReqest.getParamObject()!= null && 
							!curReqest.getRequestSession().supportsPersisterType(curReqest.getParamObject().getClass()))//当前为存储过程调用，则需要读取那些out类型的参数结果
					
						ParamFactory.readCallStatement(session,sqlId,(CallableStatement)statement,paramObject,paramNames,paramTypePersisters,paramValueModes);
					  preSqlText = curSqlText;
					  totalUpdatedRows = totalUpdatedRows+updatedRows;
				}
			}
			return totalUpdatedRows;
		} finally {
			CloseUtil.close(statement);
		}
	}

	
	/**
	* 检查是否支持批量更新
	*/
	public static boolean checkBatchUpdate(Connection con) {
		try{
			return con.getMetaData().supportsBatchUpdates();
		}catch(SQLException e){
			Statement statemnet = null;
			try {
				statemnet = con.createStatement();
				statemnet.clearBatch();
				return true;
			} catch (SQLException ee) {
				logger.warn("Current driver not support batch update,caused by: "+ e);
				return false;
			} finally {
				CloseUtil.close(statemnet);
				statemnet=null;
			}
		}
	}
 
	/**
	 * 清理所有Item
	 */
	public void clear(){
	  this.batchRequestList.clear();
	}
}
