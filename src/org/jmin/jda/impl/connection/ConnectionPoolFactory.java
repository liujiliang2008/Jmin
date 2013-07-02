/*
* Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.jda.impl.connection;

import java.sql.SQLException;

import org.jmin.jda.JdaSourceInfo;
import org.jmin.jda.JdbcSourceInfo;
import org.jmin.jda.JndiSourceInfo;
import org.jmin.jda.impl.connection.jdbc.JdbcConnectionPoolInfo;
import org.jmin.jda.impl.connection.jndi.JndiConnectionPoolInfo;
import org.jmin.jda.impl.exception.DataSourceException;

/**
 * 数据库连接池工厂
 * 
 * @author Chris
 * @version 1.0
 */

public class ConnectionPoolFactory {
	
	/**
	 * 创建连接池
	 */
	public static ConnectionPool createConnectionPool(JdaSourceInfo dataSourceInfo)throws SQLException{
		if(dataSourceInfo ==null)
			throw new DataSourceException("Data soruce definition can't be null");
		
		ConnectionPoolInfo poolInfo = null;
		if(dataSourceInfo instanceof JdbcSourceInfo){
			JdbcSourceInfo info = (JdbcSourceInfo)dataSourceInfo;
			JdbcConnectionPoolInfo jdbcPoolInfo = new JdbcConnectionPoolInfo(
				info.getDbDriver(),
				info.getDbURL(),
				info.getDbUser(),
				info.getDbPassword());
			poolInfo = jdbcPoolInfo;
		}else if(dataSourceInfo instanceof JndiSourceInfo){
			JndiSourceInfo info = (JndiSourceInfo)dataSourceInfo;
			JndiConnectionPoolInfo jndiPoolIfo = new JndiConnectionPoolInfo( 
				info.getContextName(),
				info.getContextFactory(),
				info.getContextProvideURL(),
				info.getContextPrincipal(),
				info.getContextCredentials());
			
			jndiPoolIfo.setDbUser(info.getDbUser());
			jndiPoolIfo.setDbPassword(info.getDbPassword());
			poolInfo = jndiPoolIfo;
		}else{
			throw new DataSourceException("Unkown data source type");
		}
	
		poolInfo.setConnectionMaxSize(dataSourceInfo.getConnectionMaxSize());
		poolInfo.setConnectionIdleTimeout(dataSourceInfo.getConnectionIdleTimeout());
		poolInfo.setConnectionMaxWaitTime(dataSourceInfo.getConnectionMaxWaitTime());
		poolInfo.setStatementCacheSize(dataSourceInfo.getStatementCacheSize());
		
		return new ConnectionPool(poolInfo);
	}
}
