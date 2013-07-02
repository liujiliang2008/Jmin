/*
* Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.jda.impl.connection;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 数据库连接工厂
 * 
 * @author Chris
 * @version 1.0
 */

public interface ConnectionFactory {
	
	/**
	 * 创建连接
	 */
	public Connection createConnection()throws SQLException;
	
}
