/*
* Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.jda.impl.connection.jdbc;

import java.sql.SQLException;

import org.jmin.jda.impl.connection.ConnectionFactory;
import org.jmin.jda.impl.connection.ConnectionPoolInfo;

/**
 * jdbc数据库连接池定义信息
 * 
 * @author Chris
 * @version 1.0
 */

public final class JdbcConnectionPoolInfo extends ConnectionPoolInfo{
	
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
	 * 连接工厂
	 */
	private ConnectionFactory connectionFactory = null;
	
	/**
	 * Constructor
	 */
	public JdbcConnectionPoolInfo(){}
	
	/**
	 * Constructor
	 */
	public JdbcConnectionPoolInfo(String driver,String URL,String user,String password){
		this.dbDriver = driver;
		this.dbURL =URL;
		this.dbUser =user;
		this.dbPassword=password;
	}
	
	/**
	 * Driver class
	 */
	public String getDbDriver() {
		return dbDriver;
	}
	
	/**
	 * Driver class
	 */
	public void setDbDriver(String dbDriver) {
		this.dbDriver = dbDriver;
	}
	
	/**
	 * url link
	 */
	public String getDbURL() {
		return dbURL;
	}
	
	/**
	 * url link
	 */
	public void setDbURL(String dbURL) {
		this.dbURL = dbURL;
	}
	
	/**
	 * url link
	 */
	public String getDbUser() {
		return dbUser;
	}
	
	/**
	 * url link
	 */
	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}
	
	/**
	 * url link
	 */
	public String getDbPassword() {
		return dbPassword;
	}
	
	/**
	 * url link
	 */
	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}
	
	/**
	 * 检查参数信息
	 */
	public void check()throws SQLException{
		this.checkItem("Database driver class",dbDriver);
		this.checkItem("Database connection link",dbURL);
		super.check();
	}
	
	/**
	* 连接工厂
	*/
	public ConnectionFactory getConnectionFactory(){
		if(connectionFactory==null)
			connectionFactory = new JdbcConnectionFactory(dbDriver,dbURL,dbUser,dbPassword);
		return this.connectionFactory;
	}
	
	/**
	 * 验证定义信息
	 */
	private void checkItem(String name,String value)throws SQLException{
		if(value == null || value.trim().length()==0)
			throw new SQLException(name+" can't be null");
	}
}
