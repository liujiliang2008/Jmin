/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.jmin.jda.JdaTypeConverter;
import org.jmin.jda.JdaTypeConverterMap;
import org.jmin.jda.JdaDialect;
import org.jmin.jda.JdaPageList;
import org.jmin.jda.JdaTypePersister;
import org.jmin.jda.JdaRowHandler;
import org.jmin.jda.JdaSession;
import org.jmin.jda.impl.connection.ConnectionPool;
import org.jmin.jda.impl.exception.SqlExecutionException;
import org.jmin.jda.impl.execution.select.ObjectFinder;
import org.jmin.jda.impl.execution.select.ObjectListFinder;
import org.jmin.jda.impl.execution.select.ObjectMapFinder;
import org.jmin.jda.impl.execution.select.ObjectPageFinder;
import org.jmin.jda.impl.execution.select.ObjectRowFinder;
import org.jmin.jda.impl.execution.update.BatchHandler;
import org.jmin.jda.impl.execution.update.UpdateHandler;
import org.jmin.jda.impl.statement.SqlBaseStatement;
import org.jmin.jda.impl.statement.SqlOperationType;
import org.jmin.jda.impl.transaction.Transaction;
import org.jmin.jda.impl.transaction.TransactionException;
import org.jmin.jda.impl.transaction.TransactionManager;
import org.jmin.jda.impl.util.CloseUtil;
import org.jmin.jda.impl.util.StringUtil;

/**
 * 持久化操作.
 * 
 * SQL可以被看作一个输入(参数)输出(结果）
 * 
 * 一条SQL依照参数对照表进行参数设置,查询结果依据结果参照表进行读取
 * 
 * @author Chris
 * @version 1.0
 */

public class JdaSessionImpl implements JdaSession {
	
	/**
	 * 是否处于打开状态，
	 */
	private boolean closed;
	
	/**
	 * 批量更新操作执行器
	 */
	private BatchHandler batch;
	
	/**
	 * 当前事务
	 */
	private Transaction transaction;
	
	/**
	 * 所属于的SQL Container
	 */
	private JdaContainerImpl sqlContainer;
	
	/**
	* 是否支持批量更新
	*/
	private boolean supportBatch,supportBatchChecked;
	
	/**
	* 是否支持批量Fecth,是否检查过可支持Fecth
	*/
	private boolean supportFetch,supportFetchChecked;

	/**
	 * 是否支持滚动,是否支持滚动
	 */
	private boolean supportScrollable,supportScrollableChecked;
	
	/**
	 * Constructor
	 */
	public JdaSessionImpl(JdaContainerImpl mapContainer) {
		this.sqlContainer = mapContainer;
	}

	/**
	 * 检查当前Session是否有效
	 */
	public boolean isClosed() {
		return this.closed;
	}
	
	/**
	 * 关闭当前Session,使其变为不可用
	 */
	public void close()throws SQLException{
		this.closed = true;
	}
	
	/**
	 * 插入操作
	 */
	public int insert(String id) throws SQLException {
		return this.executeUpdate(id,null,SqlOperationType.Insert);
	}

	/**
	 * 插入操作
	 */
	public int insert(String id,Object paramObject) throws SQLException {
		return this.executeUpdate(id,paramObject,SqlOperationType.Insert);
	}

	/**
	 * 更新操作
	 */
	public int update(String id) throws SQLException {
		return this.executeUpdate(id, null,SqlOperationType.Update);
	}

	/**
	 * 更新操作
	 */
	public int update(String id,Object paramObject) throws SQLException {
		return this.executeUpdate(id, paramObject,SqlOperationType.Update);
	}

	/**
	 * 删除操作
	 */
	public int delete(String id) throws SQLException {
		return this.executeUpdate(id, null,SqlOperationType.Delete);
	}

	/**
	 * 删除操作
	 */
	public int delete(String id, Object paramObject) throws SQLException {
		return this.executeUpdate(id, paramObject,SqlOperationType.Delete);
	}

	/**
	 * 执行数据改变操作
	 */
	private int executeUpdate(String id,Object paramObject,SqlOperationType operateType) throws SQLException {
		this.validateIsOpen();
		return UpdateHandler.update(this,getSqlStatement(id),paramObject,operateType);
	}
	
	/**
	 * 查找一个对象，如果出现多个或出错，将抛出异常
	 */
	public Object findOne(String id) throws SQLException {
		return this.findOne(id, null);
	}

	/**
	 * 查找一个对象，如果出现多个或出错，将抛出异常
	 */
	public Object findOne(String id, Object paramObject) throws SQLException {
		return this.findOne(id, paramObject, null);
	}

	/**
	 * 查找一个对象，如果出现多个或出错，将抛出异常
	 */
	public Object findOne(String id,Object paramObject,Object resultObject)throws SQLException {
		this.validateIsOpen();
		return ObjectFinder.find(this,getSqlStatement(id),paramObject,resultObject);
	}

	/**
	 * 查找对象列表
	 */
	public List findList(String id) throws SQLException {
		return this.findList(id,0,0);
	}



	/**
	 * 查找对象列表
	 */
	public List findList(String id, Object paramObject) throws SQLException {
		return this.findList(id, paramObject,0,0);
	}

	
  /**
	 * 获取记录的结果记录数
	 */
  public int getResultSize(String id) throws SQLException{
  	return this.getResultSize(id, null);
  }
  
  /**
	 *  获取记录的结果记录数
	 */
  public int getResultSize(String id,Object paramObject) throws SQLException{
  	this.validateIsOpen();
  	return ObjectPageFinder.getRecordCount(this,getSqlStatement(id),paramObject);
  }
	
	/**
	 * 查找对象列表，skip跳动位置，收集Rownumber记录
	 */
	public List findList(String id, int skip, int rowNum)throws SQLException {
		return this.findList(id, null, skip, rowNum);
	}
	
	/**
	 * 查找对象列表,skip跳动位置，收集Rownumber记录
	 */
	public List findList(String id,Object paramObject,int skip,int rowNum)throws SQLException {
		this.validateIsOpen();
		return ObjectListFinder.find(this,getSqlStatement(id),paramObject,skip,rowNum);
	}
	 

	/**
	 * 查找对象Map,keyPropertyName作Key属性
	 */
	public Map findMap(String id,String keyPropertyName)throws SQLException {
		return this.findMap(id,null,keyPropertyName,null);
	}

//	/**
//	 * 查找对象Map,keyPropertyName作Key属性
//	 */
//	public Map findMap(String id,String keyPropertyName,String keyPropValue) throws SQLException {
//		return this.findMap(id,null,keyPropertyName,keyPropValue);
//	}

	/**
	 * 查找对象Map,keyPropertyName作Key属性
	 */
	public Map findMap(String id,Object paramObject,String keyPropertyName)throws SQLException {
		return this.findMap(id,paramObject,keyPropertyName,null);
	}

	/**
	 * 查找对象Map,keyPropertyName作Key属性,keyPropValue属性为见键值
	 */
	public Map findMap(String id,Object paramObject,String keyPropName,String valuePropName) throws SQLException {
		this.validateIsOpen();
		if(StringUtil.isNull(keyPropName))
			throw new SqlExecutionException(id,"Result map key property name can't be null");
		if(valuePropName!=null && StringUtil.isNull(keyPropName))
			throw new SqlExecutionException(id,"Result map value property name can't be null");
		return ObjectMapFinder.find(this,getSqlStatement(id),paramObject,keyPropName,valuePropName);
	}

	/**
	 * 翻页查询
	 */
	public JdaPageList findPageList(String id, int pageSize)throws SQLException {
		return this.findPageList(id, null, pageSize);
	}

	/**
	 * 翻页查询
	 */
	public JdaPageList findPageList(String id, Object paramObject,int pageSize) throws SQLException {
		this.validateIsOpen();
		return ObjectPageFinder.find(this,getSqlStatement(id),paramObject,pageSize);
	}

	/**
	 * 通过rowhandlder进行查询
	 */
	public void findWithRowHandler(String id, JdaRowHandler rowHandler)throws SQLException {
		this.findWithRowHandler(id, rowHandler);
	}

	/**
	 * 通过rowhandlder进行查询
	 */
	public void findWithRowHandler(String id, Object paramObject,JdaRowHandler rowHandler) throws SQLException {
		this.validateIsOpen();
		ObjectRowFinder.find(this,getSqlStatement(id),paramObject,rowHandler);
	}

	/**
	 * 开始执行批量操作
	 */
	public void startBatch() throws SQLException {
		this.validateIsOpen();
		if(!supportBatchChecked){
			Connection con = this.getConnection();
			this.supportBatch = BatchHandler.checkBatchUpdate(con);
			this.supportBatchChecked=true;
			this.releaseConnection(con);
		}
		
		if(supportBatchChecked && !supportBatch)
			throw new SQLException("Batch is not supported");
		else if (batch != null) 
			throw new SQLException("A batch is in processing");
		else 
		  this.batch = new BatchHandler(this.sqlContainer.getDataSourceInfo().getBatchUpdateSize());
	}

	/**
	 * 执行批量操作
	 */
	public int executeBatch() throws SQLException {
		this.validateIsOpen();
		if (batch == null) {
			throw new SQLException("Batch update not begin");
		} else {
			Connection connection = null;
			try {
				connection = this.getConnection();
				return this.batch.execute(connection,this);
			} finally {
				this.batch.clear();
				this.batch = null;
				this.releaseConnection(connection);
			}
		}
	}
	
	/**
	 * Session中是否存在事物中
	 */
	public boolean isInTransaction() throws SQLException {
		this.validateIsOpen();
		if(this.isTransactionOpen())
		 return(this.transaction != null);
		else
		 return false;
	}

	/**
	 * 开始一个事务
	 */
	public void beginTransaction() throws SQLException {
		this.validateIsOpen();
		if(this.isTransactionOpen()){
		 this.beginTransaction(sqlContainer.getDataSourceInfo().getTransactionIsolation().getIsolationCode());
	  }
	}
	
	/**
	 * 开始一个事务
	 */
	public void beginTransaction(int isolation)throws SQLException{
		this.validateIsOpen();
		if(this.isTransactionOpen()){
			if(this.transaction != null) {
				throw new TransactionException("Session has been in a transtion");
			}else{
				this.transaction = this.getTransactionManager().begin(this.getConnection(),isolation);
			}
		}
	}
	
	/**
	 * 提交一个事务
	 */
	public void commitTransaction() throws SQLException {
		try {
			this.validateIsOpen();
			if(this.isTransactionOpen()){
				if (this.transaction == null) {
					throw new TransactionException("No transaction with the session");
				} else {
					this.transaction.commit();
				}
			}
		} finally {
			if(this.isTransactionOpen())
			  this.clearCurrentTransaction();
		}
	}

	/**
	 * 回滚一个事务
	 */
	public void rollbackTransaction() throws SQLException {
		try {
			this.validateIsOpen();
			if(this.isTransactionOpen()){
				if(this.transaction == null) {
					throw new TransactionException("No transaction with the session");
				} else {
					this.transaction.rollback();
				}
			}
		} finally {
			if(this.isTransactionOpen())
			  this.clearCurrentTransaction();
		}
	}
	
	/**
	 * 获取一个可用的连接
	 */
	public Connection getConnection() throws SQLException {
		try {
			this.validateIsOpen();
			if (this.isTransactionOpen() && this.transaction != null)
				return this.transaction.getConnection();
			else {
				return this.getConnectionPool().borrowConnection();
			}
		} catch (TransactionException e) {
			throw new SQLException("Internal transaction error");
		}
	}
	
	
	/**
	 * 释放一个可用的连接
	 */
	public void releaseConnection(Connection con) throws SQLException {
		this.validateIsOpen();
		if(!this.isInTransaction())
			CloseUtil.close(con);
	}

	/**
	 * 是否为直接映射类型
	 */
	public BatchHandler getBatchUpdateList()throws SQLException{
		this.validateIsOpen();
		return this.batch;
	}
	

	//********************************** 容器扩展方法*************************************
	/**
	 * 是否为静态SQL
	 */
	public boolean isStaticSql(String id)throws SQLException{
	 return this.sqlContainer.isStaticSql(id);
	}

	/**
	 * 是否为动态SQL
	 */
	public boolean isDynamicSql(String id)throws SQLException{
		return this.sqlContainer.isDynamicSql(id);
	}
	
	/**
	* 获取一个SQL定义
	*/
	public SqlBaseStatement getSqlStatement(String id)throws SQLException{
		return this.sqlContainer.getSqlStatement(id);
	}
	
	/**
	* 获取一个SQL定义
	*/
	public JdaDialect getSqlDialect()throws SQLException{
		return this.sqlContainer.getDataSourceInfo().getJdbcDialect();
	}
	
	/**
	 * 记录Fectch size
	 */
	public int getResultSetFetchSize() {
		return this.sqlContainer.getDataSourceInfo().getResultFetchSize();
	}
	
	/**
	 * 获得参数持久器
	 */
	public JdaTypePersister getTypePersister(Class type)throws SQLException{
		this.validateIsOpen();
		return this.sqlContainer.getTypePersister(type);
	}
	
	/**
	 * 获得参数持久器
	 */
	public JdaTypePersister getTypePersister(Class type,String jdbcName)throws SQLException{
		this.validateIsOpen();
		return this.sqlContainer.getTypePersister(type,jdbcName);
	}
	
	/**
	 * 是否包含参数持久器
	 */
	public boolean supportsPersisterType(Class type)throws SQLException{
		this.validateIsOpen();
		return this.sqlContainer.containsTypePersister(type);
	}
	
	/**
	 * 是否包含参数持久器
	 */
	public boolean supportsPersisterType(Class type,String jdbcName)throws SQLException{
		this.validateIsOpen();
		return this.sqlContainer.containsTypePersister(type,jdbcName);
	}
	
	/**
	 * 是否包含类型转换器
	 */
	public boolean supportsConversionType(Class type)throws SQLException{
		this.validateIsOpen();
		return this.sqlContainer.containsTypeConverter(type);
	}

	/**
	 * 获得类型转换器
	 */
	public JdaTypeConverter getTypeConverter(Class type)throws SQLException{
		this.validateIsOpen();
		return this.sqlContainer.getTypeConverter(type);
	}
	
	/**
	 * 获得类型转换器
	 */
	public JdaTypeConverterMap getTypeConverterMap()throws SQLException{
		this.validateIsOpen();
		return this.sqlContainer.getTypeConverterMap();
	}
	
	/**************检查驱动是否支持************************************/
	public boolean supportBatch() {
		return supportBatch;
	}
	
	public void setSupportBatch(boolean support) {
		this.supportBatch = support;
	}
 
	public boolean supportFetch() {
		return supportFetch;
	}
	
	public void setSupportFetch(boolean support) {
		this.supportFetch = support;
	}
	
	public boolean supportFetchChecked() {
		return supportFetchChecked;
	}

	public void setSupportFetchChecked(boolean checked) {
		this.supportFetchChecked = checked;
	}
	
	public boolean supportScrollable() {
		return supportScrollable;
	}

	public void setSupportScrollable(boolean supportResultScroll) {
		this.supportScrollable = supportResultScroll;
	}

	public boolean supportScrollableChecked() {
		return supportScrollableChecked;
	}

	public void setSupportScrollableChecked(boolean supportResultScrollChecked) {
		this.supportScrollableChecked = supportResultScrollChecked;
	}
	

	/** *********************************************************私有方法********************************************************* */
	/**
	 * 获取数据库的连接池
	 */
	private ConnectionPool getConnectionPool(){
  	return this.sqlContainer.getConnectionPool();
  }
  
  /**
   * 获得数据源信息
   */
	private TransactionManager getTransactionManager() {
		return this.sqlContainer.getTransactionManager();
	}
	/**
	 * 检查当前Session是否处于打开状态
	 */
	private void validateIsOpen() throws SQLException {
		if(this.closed)
			throw new SQLException("Invalid opertion,the session has been closed!");
	}

	/**
	 * 查看事务是否开放
	 */
	private boolean isTransactionOpen(){
		return this.sqlContainer.getDataSourceInfo().isTransactionOpen();
 }
	
	/**
	 * 清理当前事务
	 */
	private void clearCurrentTransaction() {
		try {
			if(this.transaction != null) {
				Connection con = transaction.getConnection();
				this.transaction.clear();
				con.close();
			}
		} catch (SQLException e) {
	
		} finally {
			this.transaction = null;
		}
	}
}
