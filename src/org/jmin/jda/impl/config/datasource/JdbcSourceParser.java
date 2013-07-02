 /*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.config.datasource;
										
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.jdom.Element;
import org.jmin.jda.JdaSourceInfo;
import org.jmin.jda.JdaDialect;
import org.jmin.jda.JdbcSourceInfo;
import org.jmin.jda.JndiSourceInfo;
import org.jmin.jda.TransactionLevel;
import org.jmin.jda.impl.exception.DataSourceException;
import org.jmin.jda.impl.exception.TransactionConfigException;
import org.jmin.jda.impl.util.ClassUtil;
import org.jmin.log.Logger;

/**
 * 数据源解析
 * 
 * @author Chris Liao
 */
public class JdbcSourceParser {
	
	/**
	 * logger
	 */
	private static Logger logger = Logger.getLogger(JdbcSourceParser.class);
	
	
	/**
	 * 数据源解析
	 */
	public JdaSourceInfo parse(Element element)throws SQLException{
		if(element == null){
			 throw new DataSourceException("Not found data source info");
		}else{
			List infoList = element.getChildren("property");
			if(infoList.isEmpty())
				throw new DataSourceException("Data source info missed");
			
			Element firstNode =(Element)infoList.get(0);
			String firstNodeName = (String)firstNode.getAttributeValue("name");
			if(firstNodeName.startsWith("jdbc")){
				return parseJdbcDataSourceInfo(element);
			}else	if(firstNodeName.startsWith("jndi")){
				return parseJndiDataSourceInfo(element);
			}else{
				throw new DataSourceException("Datasource configed error!");
			}
		}
	}
	
	/**
	 * 解析JDBC数据源
	 */
	private JdbcSourceInfo parseJdbcDataSourceInfo(Element element)throws SQLException{
		String driver=null,URL=null,user=null,password="";
		int poolSize=0,cacheSize=0,batchSize=0,fetchSize=0;
		long poolTimeout =0,requestTime=0;
		boolean transactionOpen=true;
		TransactionLevel isolation =null;
		JdaDialect jdbcDialect=null;
	
		List infoList = element.getChildren("property");
		Iterator itor = infoList.iterator();
		while (itor.hasNext()) {
			Element subElement = (Element) itor.next();
			String attrName = (String) subElement.getAttributeValue("name");
			 if(!attrName.startsWith("transaction.jta")){
				if("jdbc.driver".equalsIgnoreCase(attrName)) {
					driver = subElement.getTextTrim();
					logger.info("jdbc.driver: " + driver);
				} else if ("jdbc.url".equalsIgnoreCase(attrName)) {
					URL = subElement.getTextTrim();
					logger.info("jdbc.url: " + URL);
				} else if ("jdbc.user".equalsIgnoreCase(attrName)) {
					user = subElement.getTextTrim();
					logger.info("jdbc.user: " + user);
				} else if ("jdbc.password".equalsIgnoreCase(attrName)) {
					password = subElement.getTextTrim();
				} else if (JdbcSourceNodes.connectionSize.equalsIgnoreCase(attrName)) {
					poolSize = Integer.parseInt(subElement.getTextTrim());
					logger.info(JdbcSourceNodes.connectionSize +":"+ poolSize);
				} else if (JdbcSourceNodes.connectionTimeout.equalsIgnoreCase(attrName)) {
					poolTimeout = Long.parseLong(subElement.getTextTrim());
					logger.info(JdbcSourceNodes.connectionTimeout +":"+ poolTimeout + " ms");
				} else if (JdbcSourceNodes.connectionRequestTime.equalsIgnoreCase(attrName)) {
					requestTime = Long.parseLong(subElement.getTextTrim());
					logger.info(JdbcSourceNodes.connectionRequestTime +":"+ requestTime + " ms");
					
				
				} else if (JdbcSourceNodes.statementCacheSize.equalsIgnoreCase(attrName)) {
					cacheSize = Integer.parseInt(subElement.getTextTrim());
					logger.info(JdbcSourceNodes.statementCacheSize +":"+ cacheSize);
				} else if (JdbcSourceNodes.jdbcSqlDialect.equalsIgnoreCase(attrName)) {
					 try {
						 String className = subElement.getTextTrim();
						 jdbcDialect = (JdaDialect)ClassUtil.createInstance(className);
						 logger.info(JdbcSourceNodes.jdbcSqlDialect +":"+ className);
					} catch(Exception e) {
						throw new DataSourceException("SQL dialect class config error",e);
					}
				} else if (JdbcSourceNodes.statementBatchSize.equalsIgnoreCase(attrName)) {
					batchSize = Integer.parseInt(subElement.getTextTrim());
					logger.info(JdbcSourceNodes.statementBatchSize +":"+ batchSize);
				} else if (JdbcSourceNodes.resultsetFetchSize.equalsIgnoreCase(attrName)) {
					fetchSize = Integer.parseInt(subElement.getTextTrim());
					logger.info(JdbcSourceNodes.resultsetFetchSize +":"+ fetchSize);
				}else if (JdbcSourceNodes.transactionOpen.equalsIgnoreCase(attrName)) {
					transactionOpen = Boolean.getBoolean(subElement.getTextTrim());
					logger.info(JdbcSourceNodes.transactionOpen +":"+ transactionOpen);
				}else if (JdbcSourceNodes.transactionIsolation.equalsIgnoreCase(attrName)) {
					isolation = TransactionLevel.getTransactionIsolation(subElement.getTextTrim());
					logger.info(JdbcSourceNodes.transactionIsolation +":"+ subElement.getTextTrim());
					if(isolation == null){
					 throw new TransactionConfigException("Transaction isolation level config error,right value is[TRANSACTION_READ_UNCOMMITTED,TRANSACTION_READ_COMMITTED,TRANSACTION_REPEATABLE_READ,TRANSACTION_SERIALIZABLE]");
				 }
				}
			}
		}
			
		JdbcSourceInfo sourceInfo = new JdbcSourceInfo(driver,URL,user,password);
		if(poolSize > 0)
			sourceInfo.setConnectionMaxSize(poolSize);// int
		if(requestTime > 0)
			sourceInfo.setConnectionMaxWaitTime(requestTime);
		if(poolTimeout > 0)
			sourceInfo.setConnectionIdleTimeout(poolTimeout);// long
		if(cacheSize > 0)
			sourceInfo.setStatementCacheSize(cacheSize);// int
		if(batchSize > 0)
			sourceInfo.setBatchUpdateSize(batchSize);// int
		if(fetchSize > 0)
			sourceInfo.setResultFetchSize(fetchSize);// int
		
		sourceInfo.setTransactionOpen(transactionOpen);
		if(isolation!=null)
			sourceInfo.setTransactionIsolation(isolation);
	  if(jdbcDialect!=null)
	  	sourceInfo.setJdbcDialect(jdbcDialect);
	  
		return sourceInfo;
	}
	
	/**
	 * 解析Jndi数据源
	 */
	private JndiSourceInfo parseJndiDataSourceInfo(Element element)throws SQLException{
		String name=null,factory=null,provideURL=null;
		String principal=null,credentials = "";
		
		String jdbcUser="",jdbcPassword="";
		int poolSize=0,cacheSize=0,batchSize=0,fetchSize=0;
		long poolTimeout =0,requestTime=0;
		boolean transactionOpen=true;
		TransactionLevel isolation =null;
		JdaDialect jdbcDialect=null;
		
		List infoList = element.getChildren("property");
		Iterator itor = infoList.iterator();
		while(itor.hasNext()){
			Element subElement =(Element)itor.next();
			String attrName = (String)subElement.getAttributeValue("name");
		  if(!attrName.startsWith("transaction.jta")){
				if ("jndi.name".equalsIgnoreCase(attrName)) {
					name = subElement.getTextTrim();
					logger.info("jndi.name: " + name);
				} else if ("jndi.factory".equalsIgnoreCase(attrName)) {
					factory = subElement.getTextTrim();
					logger.info("jndi.factory: " + factory);
				} else if ("jndi.provideURL".equalsIgnoreCase(attrName)) {
					provideURL = subElement.getTextTrim();
					logger.info("jndi.priverurl: " + provideURL);
				} else if ("jndi.principal".equalsIgnoreCase(attrName)) {
					principal = subElement.getTextTrim();
					logger.info("jndi.principal: " + principal);
				} else if ("jndi.credentials".equalsIgnoreCase(attrName)) {
					credentials = subElement.getTextTrim();
				} else if ("jndi.jdbc.user".equalsIgnoreCase(attrName)) {
					jdbcUser = subElement.getTextTrim();
					logger.info("jndi.user: " + jdbcUser);
				} else if ("jndi.jdbc.password".equalsIgnoreCase(attrName)) {
					jdbcPassword = subElement.getTextTrim();
				} else if (JdbcSourceNodes.connectionSize.equalsIgnoreCase(attrName)) {
					poolSize = Integer.parseInt(subElement.getTextTrim());
					logger.info(JdbcSourceNodes.connectionSize +":"+ poolSize);
				} else if (JdbcSourceNodes.connectionTimeout.equalsIgnoreCase(attrName)) {
					poolTimeout = Long.parseLong(subElement.getTextTrim());
					logger.info(JdbcSourceNodes.connectionTimeout +":"+ poolTimeout + " ms");
				} else if (JdbcSourceNodes.connectionRequestTime.equalsIgnoreCase(attrName)) {
					requestTime = Long.parseLong(subElement.getTextTrim());
					logger.info(JdbcSourceNodes.connectionRequestTime +":"+ requestTime+ " ms");
				
				} else if (JdbcSourceNodes.statementCacheSize.equalsIgnoreCase(attrName)) {
					cacheSize = Integer.parseInt(subElement.getTextTrim());
					logger.info(JdbcSourceNodes.statementCacheSize +":"+ cacheSize);
				}else if (JdbcSourceNodes.jdbcSqlDialect.equalsIgnoreCase(attrName)) {
						 try {
							 String className = subElement.getTextTrim();
							 jdbcDialect = (JdaDialect)ClassUtil.createInstance(className);
							 logger.info(JdbcSourceNodes.jdbcSqlDialect +":"+ className);
						}catch (Exception e) {
							throw new DataSourceException("SQL dialect class config error",e);
						}
				} else if (JdbcSourceNodes.statementBatchSize.equalsIgnoreCase(attrName)) {
					batchSize = Integer.parseInt(subElement.getTextTrim());
					logger.info(JdbcSourceNodes.statementBatchSize +":"+ batchSize);
				} else if (JdbcSourceNodes.resultsetFetchSize.equalsIgnoreCase(attrName)) {
					fetchSize = Integer.parseInt(subElement.getTextTrim());
					logger.info(JdbcSourceNodes.resultsetFetchSize +":"+ fetchSize);
				}else if (JdbcSourceNodes.transactionOpen.equalsIgnoreCase(attrName)) {
					transactionOpen = Boolean.getBoolean(subElement.getTextTrim());
					logger.info(JdbcSourceNodes.transactionOpen +":"+ transactionOpen);
				}else if (JdbcSourceNodes.transactionIsolation.equalsIgnoreCase(attrName)) {
					isolation = TransactionLevel.getTransactionIsolation(subElement.getTextTrim());
					logger.info(JdbcSourceNodes.transactionIsolation +":"+ subElement.getTextTrim());
					if(isolation == null){
					 throw new TransactionConfigException("Transaction isolation level config error,right value is[TRANSACTION_READ_UNCOMMITTED,TRANSACTION_READ_COMMITTED,TRANSACTION_REPEATABLE_READ,TRANSACTION_SERIALIZABLE]");
				 }
				}
		  }
		}
			
		JndiSourceInfo sourceInfo = new JndiSourceInfo(name,factory,provideURL,principal,credentials);
		if(jdbcUser!=null)
			sourceInfo.setDbUser(jdbcUser);
		if(jdbcPassword!=null)
			sourceInfo.setDbPassword(jdbcPassword);
		if (poolSize > 0)
			sourceInfo.setConnectionMaxSize(poolSize);// int
		if (requestTime > 0)
			sourceInfo.setConnectionMaxWaitTime(requestTime);
		if (poolTimeout > 0)
			sourceInfo.setConnectionIdleTimeout(poolTimeout);// long
		if (cacheSize > 0)
			sourceInfo.setStatementCacheSize(cacheSize);// int
		if (batchSize > 0)
			sourceInfo.setBatchUpdateSize(batchSize);// int
		if (fetchSize > 0)
			sourceInfo.setResultFetchSize(fetchSize);// int
		
		sourceInfo.setTransactionOpen(transactionOpen);
	  if(isolation!=null)
			sourceInfo.setTransactionIsolation(isolation);
	  if(jdbcDialect!=null)
	  	sourceInfo.setJdbcDialect(jdbcDialect);
		return sourceInfo;
	}
}
