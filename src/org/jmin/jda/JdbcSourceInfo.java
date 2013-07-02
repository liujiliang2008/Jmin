/*
* Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.jda;

/**
 * Jdbc DataSource Definition
 * 
 * @author Chris
 * @version 1.0
 */

public final class JdbcSourceInfo extends JdaSourceInfo {
	
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
	 * Constructor
	 */
	public JdbcSourceInfo(){}
	
	/**
	 * Constructor
	 */
	public JdbcSourceInfo(String driver,String URL,String user,String password){
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
}
