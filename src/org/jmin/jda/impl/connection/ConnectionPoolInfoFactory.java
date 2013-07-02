/*
* Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.jda.impl.connection;

import org.jmin.jda.impl.connection.jdbc.JdbcConnectionPoolInfo;
import org.jmin.jda.impl.connection.jndi.JndiConnectionPoolInfo;

/**
 * 数据库连接池信息工厂
 * 
 * @author Chris
 * @version 1.0
 */

public class ConnectionPoolInfoFactory {
	
	/**
	 * 创建jdbc池连接信息
	 */
	public static ConnectionPoolInfo createJdbcPoolInfo(String driver,String URL,String user,String password){
		return new JdbcConnectionPoolInfo(driver,URL,user,password);
	}
	
	/**
	 * 创建jndi连接池信息
	 */
	public static ConnectionPoolInfo createJndiPoolInfo(String jndiName,String factory,String provideURL,String principal,String credentials){
		return new JndiConnectionPoolInfo(jndiName,factory,provideURL,principal,credentials);
	}
}
