/*
* Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.jda.impl.execution;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.jmin.jda.JdaDialect;
import org.jmin.jda.JdaTypePersister;
import org.jmin.jda.impl.JdaSessionImpl;
import org.jmin.jda.impl.exception.SqlExecutionException;
import org.jmin.jda.impl.execution.worker.ParamFactory;
import org.jmin.jda.impl.statement.SqlOperationType;
import org.jmin.jda.impl.util.CloseUtil;
import org.jmin.jda.mapping.ParamValueMode;
import org.jmin.log.Logger;


/**
 * SQL执行请求处理
 * 
 * @author Chris Liao
 */
public class SqlRequestHandler{
	
	/**
	 * message Printer
	 */
	private static final Logger logger = Logger.getLogger(SqlRequestHandler.class);
	
	/**
	 * 执行更新操作
	 */
	public static int handleUpdateRequest(SqlRequest request)throws SQLException{
		Connection connection=null;
		PreparedStatement statement=null;
		
		String sqlId=request.getSqlId();
		String sqlText=request.getSqlText();
		Object paramObject=request.getParamObject();
		String[]paramNames=request.getParamNames();
		Class[] paramTypes=request.getParamValueTypes();
		Object[]paramValues=request.getParamValues();
 	  int[] paramSqlTypeCodes=request.getParamSqlTypeCodes();
 	  JdaTypePersister[] paramTypePersisters=request.getParamTypePersisters();
 	  ParamValueMode[] paramValueModes=request.getParamValueModes();
 	  JdaSessionImpl session=request.getRequestSession();
		
		StringBuffer sqlInfoBuff = getSqlPrintInfo(sqlText,paramNames,paramTypes,paramValues);
				
		try {
			connection=getConneciton(request);
			statement=SqlRequestHandler.createGeneralStatement(connection,sqlText);
			ParamFactory.setParamValues(session,sqlId,statement,paramNames,paramValues,paramSqlTypeCodes,paramTypePersisters,paramValueModes);
			
			int effectRows=statement.executeUpdate();
			if(statement instanceof CallableStatement && paramObject!= null && !session.supportsPersisterType(request.getParamClass()))//当前为存储过程调用，则需要读取那些out类型的参数结果
				ParamFactory.readCallStatement(request.getRequestSession(),sqlId,(CallableStatement)statement,paramObject,paramNames,paramTypePersisters,paramValueModes);
			
			logger.debug(sqlId,"Success to execute sql:\n" + sqlInfoBuff  + "\neffected rows:" + effectRows + "\n");
			return effectRows;
		}catch (Throwable e) {
			SQLException ee = new SqlExecutionException(sqlId,"Failed to execute update sql:\n" + sqlInfoBuff+"\n\n cause:"+e.getMessage(),e);
			throw ee;
		}finally{
			CloseUtil.close(statement);
			statement=null;
		}
	}
	
	/**
	 * 执行查询操作
	 */
	public static ResultSet handleQueryRequest(SqlRequest request)throws SQLException{
		Connection connection=null;
		ResultSet resultSet=null;
		PreparedStatement statement=null;
		
		boolean jdbcScrollable=false;
		String sqlId=request.getSqlId();
		String sqlText=request.getSqlText();
		Object paramObject=request.getParamObject();
		String[]paramNames=request.getParamNames();
		Class[] paramTypes=request.getParamValueTypes();
		Object[]paramValues=request.getParamValues();
		
 	  int[] paramSqlTypeCodes=request.getParamSqlTypeCodes();
 	  JdaTypePersister[] paramTypePersisters=request.getParamTypePersisters();
 	  ParamValueMode[] paramValueModes=request.getParamValueModes();
		
		int startRownum=request.getRecordSkipPos();
		int maxRecordCount=request.getRecordMaxRows();
		JdaDialect dialect=request.getSqlDialect();
		JdaSessionImpl session= request.getRequestSession();
		SqlOperationType definitionType = request.getDefinitionType();
		StringBuffer sqlInfoBuff = getSqlPrintInfo(sqlText,paramNames,paramTypes,paramValues);
	
		try {
				connection=getConneciton(request);
				if(startRownum >0 && dialect!=null && SqlOperationType.Select.equals(definitionType))//当前SQL是取间查询，需要重新改造Request
					rebuildRequestForPageQuery(request,startRownum,maxRecordCount);
				
				if(startRownum >0 && (dialect==null || sqlText.equals(request.getSqlText()) || SqlOperationType.Procedure.equals(definitionType)))
					jdbcScrollable=true;
							
				statement=SqlRequestHandler.createQueryStatement(connection,request.getSqlText(),jdbcScrollable,session);
				ParamFactory.setParamValues(session,sqlId,statement,paramNames,paramValues,paramSqlTypeCodes,paramTypePersisters,paramValueModes);
				
				SqlRequestHandler.setResultSetFetchSize(statement,session);
				if(startRownum > 0 && session.supportScrollable() && (dialect==null || sqlText.equals(request.getSqlText()) || SqlOperationType.Procedure.equals(definitionType))){//JDBC本身支持的最大记录数
					 statement.setMaxRows(request.getRecordSkipPos() + request.getRecordMaxRows()-1);
				}
				
				resultSet=statement.executeQuery();
				if(statement instanceof CallableStatement && paramObject!= null && !session.supportsPersisterType(paramObject.getClass()))//当前为存储过程调用，则需要读取那些out类型的参数结果
					ParamFactory.readCallStatement(request.getRequestSession(),sqlId,(CallableStatement)statement,paramObject,paramNames,paramTypePersisters,paramValueModes);
 
				logger.debug(sqlId,"Success to execute sql:\n" + sqlInfoBuff+ "\n");
				return resultSet;
		}catch (Throwable e) {
			SQLException ee = new SqlExecutionException(sqlId,"Failed to execute select sql:\n" + sqlInfoBuff+"\n\n cause:"+e.getMessage(),e);
			throw ee;
		}finally{
			CloseUtil.close(statement);
			statement=null;
		}
	}

	/**
	 * 获取连接
	 */
	private static Connection getConneciton(SqlRequest request)throws SQLException{
		Connection connection =null;
		if(request.getConnection()==null){
			JdaSessionImpl session= request.getRequestSession();
			connection= session.getConnection();
			request.setConnection(connection);
	   }else{
	  	connection=request.getConnection();
	   }
		
		return connection;
	}

	/**
	 * 构造一个普通PreparedStatement
	 */
	public static PreparedStatement createGeneralStatement(Connection con,String sql) throws SQLException {
		if(sql.startsWith("{"))// 判断SQL文本是否为存储过程调用
			return con.prepareCall(sql);
		else
			return con.prepareStatement(sql);
	}

	/**
	 * 构造一个滚动查询PreparedStatement
	 */
	public static PreparedStatement createScrollStatement(Connection con,String sql) throws SQLException {
		if(sql.startsWith("{"))
			return con.prepareCall(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		else
			return con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
	}
	
	/**
	* 构造一个查询专用的PreparedStatement
	*/
	private static PreparedStatement createQueryStatement(Connection con,String sql,boolean needScroll,JdaSessionImpl session)throws SQLException {
		if(!needScroll){//不需要记录滚动或者
			return SqlRequestHandler.createGeneralStatement(con,sql);
		}else {//需要记录滚动
			if(session.supportScrollableChecked()){// 已经检查过是否支持滚动
				if(!session.supportScrollable())// 不支持滚动，只能普通创建
					return SqlRequestHandler.createGeneralStatement(con,sql);
				else
					return SqlRequestHandler.createScrollStatement(con,sql);		
			}else{//没经检查过是否支持滚动

				try{
					PreparedStatement statement =null;
					statement = SqlRequestHandler.createScrollStatement(con,sql);			
					session.setSupportScrollable(true);
					return statement;
				}catch(SQLException e){
					logger.warn("Current driver not support result scrolled,caused by: "+ e);
					session.setSupportScrollable(false);
					return SqlRequestHandler.createGeneralStatement(con,sql);
				}finally{
					session.setSupportScrollableChecked(true);
				}
			}
		}
	}
	
	/**
	 * 设置结果Fectch size
	 */
	private static void setResultSetFetchSize(PreparedStatement ps,JdaSessionImpl session)throws SQLException {
		if(session.getResultSetFetchSize() > 0 ){
			if(session.supportFetchChecked()){//已经检查过是否支持ResultFetch
				if(session.supportFetch()){
					ps.setFetchSize(session.getResultSetFetchSize());
					ps.setFetchDirection(ResultSet.FETCH_FORWARD);
				}
			}else{////还没检查过是否支持ResultFetch
				try{
					ps.setFetchSize(session.getResultSetFetchSize());
					ps.setFetchDirection(ResultSet.FETCH_FORWARD);
					session.setSupportFetch(true);
				}catch(Throwable e){
					logger.warn("Current driver not support result fetch");
					session.setSupportFetch(false);
				}finally{
					session.setSupportFetchChecked(true);
				}
			}
		}
	}
	
	/**
	 * 改造SQL为区间查询
	 */
	public static void rebuildRequestForPageQuery(SqlRequest request,int startPos,int maxRows)throws SQLException {
		JdaDialect dialect=request.getSqlDialect();
		String pageSql = dialect.getPageQuerySql(request.getSqlText(),startPos,maxRows);//构造出分页查询的SQL
		if(!pageSql.equals(request.getSqlText())){
			request.setSqlText(pageSql);
			int pos1 = dialect.getStartRownum(startPos,maxRows);
			int pos2 = dialect.getEndRownum(startPos,maxRows);
			
			JdaSessionImpl session= request.getRequestSession();
			Object[]paramValues = request.getParamValues();
			int arrayLen = (paramValues==null)?0:paramValues.length;
			Class[]paramTypes = request.getParamValueTypes();
			String[]paramNames = request.getParamNames();
			int[] paramSQLTypes = request.getParamSqlTypeCodes();
			JdaTypePersister[]persisters=request.getParamTypePersisters();
			ParamValueMode[] valueModes = request.getParamValueModes();
		
			Object[]newParamValues = new Object[arrayLen+2];
			Class[]newParamTypes = new Class[arrayLen+2];
			String[]newParamNames = new String[arrayLen+2];
			int[] newParamSQLTypes = new int[arrayLen+2];
			JdaTypePersister[]newPersisters=new JdaTypePersister[arrayLen+2];
			ParamValueMode[] newValueModes=new ParamValueMode[arrayLen+2];
			
			if(paramValues!=null){
				System.arraycopy(paramNames,0,newParamNames,0,arrayLen);
				System.arraycopy(paramValues,0,newParamValues,0,arrayLen);
				System.arraycopy(paramTypes,0,newParamTypes,0,arrayLen);
				System.arraycopy(paramSQLTypes,0,newParamSQLTypes,0,arrayLen);
				System.arraycopy(persisters,0,newPersisters,0,arrayLen);
				System.arraycopy(valueModes,0,newValueModes,0,arrayLen);
			}
			
			newParamNames[arrayLen]="";
			newParamTypes[arrayLen]=Integer.class;
			newParamValues[arrayLen]=new Integer(pos1);
			newParamSQLTypes[arrayLen]=Types.INTEGER;
			newPersisters[arrayLen]=session.getTypePersister(Integer.class);
			valueModes[arrayLen]=ParamValueMode.IN;
			
			newParamNames[arrayLen+1]="";
			newParamTypes[arrayLen+1]=Integer.class;
			newParamValues[arrayLen+1]=new Integer(pos2);
			newParamSQLTypes[arrayLen+1]=Types.INTEGER;
			newPersisters[arrayLen+1]=session.getTypePersister(Integer.class);
			newValueModes[arrayLen+1]=ParamValueMode.IN;
			
			request.setParamNames(newParamNames);
			request.setParamValues(newParamValues);
			request.setParamValueTypes(newParamTypes);
			request.setParamSqlTypeCodes(newParamSQLTypes);
			request.setParamTypePersisters(newPersisters);
			request.setParamValueModes(newValueModes);
	   }
	}
	
	/**
	 * 获得SQL执行参数值
	 */
	private static StringBuffer getSqlPrintInfo(String sql,String[]paramName,Class[]paramTypes,Object[] paramValues)throws SQLException {
		StringBuffer buffer = new StringBuffer();
		buffer.append(sql);
		if(paramValues!=null && paramValues.length >0)
			buffer.append("\n");
		for(int i=0;paramValues!=null && i<paramValues.length;i++){
			buffer.append("["+ (i+1) +"] = "+String.valueOf(paramValues[i]));
			buffer.append("  ("+ paramTypes[i].getName()+ " : " + paramName[i]+ ")");
	 
			if(i<paramValues.length-1)
				buffer.append("\n");
		}
		return buffer;
	}
}
