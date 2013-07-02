/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda;

/**
 * 数据源定义信息
 * 
 * @author Chris Liao
 */
public abstract class JdaSourceInfo{
	
	/**
	 * 数据源ID
	 */
	private String sourceID ="";

	/**
	* 池最大容许的size
	*/
	private int connectionMaxSize = 10;
	
	/**
	* Connection的Statement缓存容器大小
	*/
	private int statementCacheSize = 20;
	
	/**
	 * 一次性从数据库中获取的记录个数
	 */
	private int resultFetchSize = 10;
	
	/**
	 * 批次最大Size,当statement的执行数据达到该size,statement将执行一次
	 */
	private int batchUpdateSize = 10;
	
	/**
	* 连接的最大闲置时间，超过将被关闭，默认时间为3分钟
	*/
	private long connectionIdleTimeout = 180000;
	
	/**
	* 获取连接的最大同步等待时间:3分钟
	*/
	private long connectionMaxWaitTime = 180000;
	
	/**
	 * SQL方言
	 */
	private JdaDialect sqlMapDialect=null;
	
	/**
	 * 是否让事务处有效
	 */
	private boolean transactionOpen=true;
	
	/**
	 * 事务定义
	 */
	private UserTransactionInfo userTransactionInfo=null;
	
	/**
	* 事务隔离等级,默认等级为：读提交
	*/
	private TransactionLevel transactionIsolation = TransactionLevel.TRANSACTION_READ_COMMITTED;
	

	/**
	 * 数据源ID
	 */
	public String getDataSourceID() {
		return sourceID;
	}

	/**
	 * 数据源ID
	 */
	public void setDataSourceID(String dataSourceID) {
		sourceID = dataSourceID;
	}
	
	/**
	 * SQL方言
	 */
	public JdaDialect getJdbcDialect() {
		return sqlMapDialect;
	}
	
	/**
	 * SQL方言
	 */
	public void setJdbcDialect(JdaDialect dialect) {
		this.sqlMapDialect = dialect;
	}
	
	/**
	* 池最大容许的size
	*/
	public int getConnectionMaxSize() {
		return connectionMaxSize;
	}
	
	/**
	* 池最大容许的size
	*/
	public void setConnectionMaxSize(int connectionMaxSize) {
		this.connectionMaxSize = connectionMaxSize;
	}
	
	/**
	* Connection的Statement缓存容器大小
	*/
	public int getStatementCacheSize() {
		return statementCacheSize;
	}
	
	/**
	* Connection的Statement缓存容器大小
	*/
	public void setStatementCacheSize(int statementCacheSize) {
		this.statementCacheSize = statementCacheSize;
	}

	/**
	 * 批次最大Size,当statement的执行数据达到该size,statement将执行一次
	 */
	public int getBatchUpdateSize() {
		return batchUpdateSize;
	}
	
	/**
	 * 批次最大Size,当statement的执行数据达到该size,statement将执行一次
	 */
	public void setBatchUpdateSize(int batchUpdateSize) {
		this.batchUpdateSize = batchUpdateSize;
	}
	
	/**
	 * 一次性从数据库中获取的记录个数
	 */
	public int getResultFetchSize() {
		return resultFetchSize;
	}
	
	/**
	 * 一次性从数据库中获取的记录个数
	 */
	public void setResultFetchSize(int resultFetchSize) {
		this.resultFetchSize = resultFetchSize;
	}
	
	/**
	* 连接的最大闲置时间，超过将被关闭，默认时间为3分钟
	*/
	public long getConnectionIdleTimeout() {
		return connectionIdleTimeout;
	}
	
	/**
	* 连接的最大闲置时间，超过将被关闭，默认时间为3分钟
	*/
	public void setConnectionIdleTimeout(long connectionIdleTimeout) {
		this.connectionIdleTimeout = connectionIdleTimeout;
	}
	
	/**
	* 获取连接的最大同步等待时间
	*/
	public long getConnectionMaxWaitTime() {
		return connectionMaxWaitTime;
	}
	
	/**
	* 获取连接的最大同步等待时间
	*/
	public void setConnectionMaxWaitTime(long connectionMaxWaitTime) {
		this.connectionMaxWaitTime = connectionMaxWaitTime;
	}
	
	/**
	 * 是否让事务处有效
	 */
	public boolean isTransactionOpen() {
		return transactionOpen;
	}
	
	/**
	 * 是否让事务处有效
	 */
	public void setTransactionOpen(boolean transactionOpen) {
		this.transactionOpen = transactionOpen;
	}

	/**
	 * JTA事务定义
	 */
	public UserTransactionInfo getUserTransactionInfo() {
		return userTransactionInfo;
	}
	
	/**
	 * JTA事务定义
	 */
	public void setUserTransactionInfo(UserTransactionInfo jtaTransactionInfo) {
		if(jtaTransactionInfo!=null)
			this.userTransactionInfo = jtaTransactionInfo;
	}
	
	/**
	* 事务隔离等级
	*/
	public TransactionLevel getTransactionIsolation() {
		return transactionIsolation;
	}
	
	/**
	* 事务隔离等级
	*/
	public void setTransactionIsolation(TransactionLevel transactionIsolation) {
		if(transactionIsolation!=null)
			this.transactionIsolation = transactionIsolation;
	}
}
