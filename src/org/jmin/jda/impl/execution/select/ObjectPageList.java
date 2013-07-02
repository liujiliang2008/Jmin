/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.execution.select;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jmin.jda.JdaDialect;
import org.jmin.jda.JdaPageList;
import org.jmin.jda.impl.JdaSessionImpl;
import org.jmin.log.Logger;
import org.jmin.jda.impl.exception.SqlExecutionException;
import org.jmin.jda.impl.execution.SqlRequest;
import org.jmin.jda.impl.execution.SqlRequestHandler;
import org.jmin.jda.impl.execution.worker.ResultFactory;
import org.jmin.jda.impl.statement.SqlBaseStatement;
import org.jmin.jda.impl.statement.SqlOperationType;
import org.jmin.jda.impl.util.CloseUtil;

/**
 * 分页查询列表实现
 * 
 * @author Chris Liao
 */

public class ObjectPageList implements JdaPageList {
	
	/**
	 * 当前页码
	 */
	private int currentIndex=0;
	
	/**
	 * 分页的记录最大数
	 */
	private int pageDataSize=10;

	/**
	 * 总页数
	 */
	private int pageTotalSize;

	/**
	 * 当前结果对象列表
	 */
	private List currentDataList;
	
	/**
	 * 是否重新创建过
	 */
	private boolean rebuild =false;
	
	/**
	 * 请求
	 */
	private SqlRequest request;
	
	/**
	 * message Printer
	 */
	private static final Logger logger = Logger.getLogger(ObjectPageList.class);
	
	/**
	 * 构造函数
	 */
	public ObjectPageList(SqlRequest request,int totalSize,int pageRowCount,JdaSessionImpl session){
		this.request=request;
		this.currentIndex=0;//第0页
		this.pageTotalSize=totalSize;
		this.pageDataSize=pageRowCount;
		this.currentDataList =new ArrayList();
	}
	
	/**
	 * 总页数
	 */
	public int getTotalSize(){
		return pageTotalSize;
	}
	
	 /**
	  * 获取当前页码
	  */
	public int geCurrentIndex(){
		return currentIndex;
	}
	
	/**
	 * 获取当前页面数据
	 */
	public Object[] getCurrentPage(){
		return currentDataList.toArray();
	}
	
	/**
	 * 是否为第一页
	 */
	public boolean isFirstPage() {
	 return (currentIndex==1)?true:false;
	}

	/**
	 * 是否为中间页
	 */
	public boolean isMiddlePage() {
		return (1<currentIndex && currentIndex<pageTotalSize)?true:false;
	}

	/**
	 * 是否为最后一页
	 */
	public boolean isLastPage() {
		return (currentIndex==pageTotalSize)?true:false;
	}

	/**
	 * 前进是否可行
	 */
	public boolean isNextPageAvailable() {
		return (1<=currentIndex && currentIndex<pageTotalSize)?true:false;
	}

	/**
	 * 后退是可行
	 */
	public boolean isPreviousPageAvailable() {
		return (1<currentIndex && currentIndex<=pageTotalSize)?true:false;
	}

	/**
	 * 翻到下一页
	 */
	public void movePreviousPage()throws SQLException {
		if(isPreviousPageAvailable()){
			this.loadRecord(currentIndex-1);
		}else{
			throw new SqlExecutionException(request.getSqlId(),"Current page index is First");
		}
	}
	
	/**
	 * 翻到下一页
	 */
	public void moveNextPage()throws SQLException {
		if(isNextPageAvailable()){
			this.loadRecord(currentIndex+1);
		}else{
			throw new SqlExecutionException(request.getSqlId(),"Current page("+currentIndex+")has reach end");
		}
	}

	/**
	 * 跳转到指定页
	 */
	public void moveToPage(int pageNumber)throws SQLException {
		if((1<=pageNumber && pageNumber<=pageTotalSize)){
			this.loadRecord(pageNumber);
		}else{
			throw new SqlExecutionException(request.getSqlId(),"Page index can't be out of range:" + pageTotalSize);
		}
	}
	
	/**
	 * 初试化执行
	 */
	void initFirstPage()throws SQLException{
		this.loadRecord(1);
		this.currentIndex=1;
	}

	/**
	 * 跳转到指定页位置，装载数据
	 */
	private void loadRecord(int pageIndex)throws SQLException{
		if(pageTotalSize ==0)
			throw new SqlExecutionException(request.getSqlId(),"Can't move index on empty record set");
		
		ResultSet resultSet = null;
		String sqlId = request.getSqlId();
		JdaSessionImpl session = request.getRequestSession();
		SqlBaseStatement statement = session.getSqlStatement(sqlId);
		JdaDialect dialect=session.getSqlDialect();
		
		try{
		int startPos=this.getStartRecordNo(pageIndex,pageDataSize);//获取页的记录位置
		if(dialect!=null && SqlOperationType.Select.equals(request.getDefinitionType())){
			if(!rebuild)
				SqlRequestHandler.rebuildRequestForPageQuery(request,startPos,pageDataSize);
		 Object[]paramValues= request.getParamValues();
		 paramValues[paramValues.length-1]= new Integer(dialect.getStartRownum(startPos,pageDataSize)); 
		 paramValues[paramValues.length-2]= new Integer(dialect.getEndRownum(startPos,pageDataSize));
		 request.setRecordSkipPos(0);
		 request.setRecordMaxRows(0);
		}else{
		 request.setRecordSkipPos(startPos);
		 request.setRecordMaxRows(pageDataSize);
		}
	 
		resultSet=SqlRequestHandler.handleQueryRequest(request);
		
		int readCount=0,skipCount=0;
		boolean needCount=((dialect==null || SqlOperationType.Procedure.equals(request.getDefinitionType())))?true:false;
		boolean needSkip=((dialect==null || SqlOperationType.Procedure.equals(request.getDefinitionType())))? true:false;
		if(needSkip){
			if(session.supportScrollable()){
		    resultSet.first();//将游标移动到第一条记录
		    resultSet.relative(startPos-2);//游标移动到要输出的第一条记录
			}else{
				while(resultSet.next()){
					if(skipCount++==startPos)
						break;
				}
			}
		}
		
		currentDataList.clear();
		while(resultSet.next()) {
			Object resultObject = ResultFactory.readResultObject(session,request.getConnection(),statement,resultSet,null);
			currentDataList.add(resultObject);
			if(needCount){
				if(++readCount == pageDataSize)
					break;
			}
		}
		
		this.currentIndex = pageIndex;
		logger.info("Moved page sql("+sqlId+")recordset to index:"+pageIndex);
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
	
	/**
	 * 依据页码计算记录记录的位置
	 */
	private int getStartRecordNo(int index,int pageSize){
		return (index-1)*pageSize + 1;
	}
}
