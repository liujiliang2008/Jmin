/*
* Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.jda.impl.connection.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.jmin.jda.impl.connection.ConnectionFactory;

/**
 * Jdbc数据库连接工厂
 * 
 * @author Chris
 * @version 1.0
 */

class JdbcConnectionFactory implements ConnectionFactory{
	
	/**
	 * Driver class
	 */
	private String dbDriver;

	/**
	 * url link
	 */
	private String dbURL;

	/**
	 * user ID
	 */
	private String dbUser;

	/**
	 * Passsword
	 */
	private String dbPassword;
	
	/**
	 * 是否初始化
	 */
	private boolean driverLoaded;
	
	/**
	 * Constructor
	 */
	public JdbcConnectionFactory(String dbDriver,String dbURL,String dbUser,String dbPassword){
		this.dbDriver = dbDriver;
		this.dbURL = dbURL;
		this.dbUser = dbUser;
		this.dbPassword = dbPassword;
	}

	/**
	 * 创建连接
	 */
	public Connection createConnection()throws SQLException{
		if(!this.driverLoaded){
			this.loadJdbcDriver(this.dbDriver);
			this.driverLoaded = true;
		}
		
		return DriverManager.getConnection(dbURL,dbUser,dbPassword);
	}

  /**
   * 装载jdbc驱动
   */
	private void loadJdbcDriver(String driver)throws SQLException {
    try {
			Class.forName(driver,true,this.getClass().getClassLoader());
		} catch (ClassNotFoundException e) {
			throw new SQLException("Jdbc driver class["+driver+"] not found");
		}
  }
}
