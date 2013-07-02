 /*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.config.datasource;

/**
 * 节点类型名
 * 
 * @author Chris
 */
public class JdbcSourceNodes {
	
	/**
	 * 根节点名
	 */
	public static final String Root ="sql-mapping";
	
	/**
	 * 数据源节点名
	 */
	public static final String DataSource ="datasource";
	
	/**
	 * 池的size
	 */
	public static final String connectionSize ="connection.poolSize";
	
	/**
	 * 连接最大闲置时间
	 */
	public static final String connectionTimeout ="connection.maxIdleTime";
	
	/**
	 * 请求连接最大同步等待时间
	 */
	public static final String connectionRequestTime ="connection.maxWaitTime";
	
	
	/**
	 * sql缓存Size
	 */
	public static final String statementCacheSize ="connection.statement.cacheSize";
	
	/**
	 * sql批量更新的Size
	 */
	public static final String statementBatchSize ="connection.statement.batchSize";
	
	/**
	 * 结果获取的size
	 */
	public static final String resultsetFetchSize ="query.resultset.fetchSize";
	
	/**
	 * 节点名
	 */
	public static final String jdbcSqlDialect ="jdbc.sql.dialect";
	
	
	/**
	 * 事务是否有效
	 */
	public static final String transactionOpen ="transaction.open";

	/**
	 * 事务节点名
	 */
	public static final String transactionJtaName ="transaction.jta.name";
	
	/**
	 * 事务节点名
	 */
	public static final String transactionJtaFactory ="transaction.jta.factory";
	
	/**
	 * 事务节点名
	 */
	public static final String transactionJtaProvider ="transaction.jta.provider";
	
	/**
	 * 事务节点名
	 */
	public static final String transactionJtaPrincipal ="transaction.jta.principal";
	
	/**
	 * 事务节点名
	 */
	public static final String transactionJtaCredentials ="transaction.jta.credentials";
	
	/**
	 * 事务节点名
	 */
	public static final String transactionIsolation ="transaction.isolation";
	

	
	/**
	 * Jdbc类型节点
	 */
	public static final String JdbcTypes ="jdbc-types";
	
	/**
	 * Handler列表
	 */
	public static final String ParamPersisters ="persisters";
	
	/**
	 * Handler列表
	 */
	public static final String ParamPersister ="persister";
	
	/**
	 * Converters
	 */
	public static final String ResultConverters ="converters";
	
	/**
	 * Converters
	 */
	public static final String ResultConverter ="converter";
	
	

	/**
	 * 映射文件节点名
	 */
	public static final String Mapping ="mapping";
	
	
	
	
}
