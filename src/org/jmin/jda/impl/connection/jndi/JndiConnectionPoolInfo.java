/*
* Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.jda.impl.connection.jndi;

import java.sql.SQLException;

import org.jmin.jda.impl.connection.ConnectionFactory;
import org.jmin.jda.impl.connection.ConnectionPoolInfo;

/**
 * jndi数据库连接池定义信息
 * 
 * @author Chris
 * @version 1.0
 */

public final class JndiConnectionPoolInfo extends ConnectionPoolInfo{
	
	/**
	 * jndi
	 */
	private String jndiName;

	/**
	 * factory
	 */
	private String contextFactory;
	
	/**
	 * provideURL;
	 */
	private String contextProvideURL;

	/**
	 * principal
	 */
	private String contextPrincipal;

	/**
	 * credentials
	 */
	private String contextCredentials;
	
	/**
	 * jdbc user ID
	 */
	private String dbUser;

	/**
	 * jdbc  Passsword
	 */
	private String dbPassword;

	/**
	 * 连接工厂
	 */
	private ConnectionFactory connectionFactory = null;
	
	/**
	 * constructor
	 */
	public JndiConnectionPoolInfo(){}
	
	/**
	 * constructor
	 */
	public JndiConnectionPoolInfo(String jndiName,String contextFactory,String contextProvideURL,String contextPrincipal,String contextCredentials){
		this.jndiName =jndiName;
		this.contextFactory =contextFactory;
		this.contextProvideURL = contextProvideURL;
		this.contextCredentials = contextCredentials;
	}
	
	/**
	 * jndi
	 */
	public String getJndiName() {
		return jndiName;
	}
	
	/**
	 * jndi
	 */
	public void setJndiName(String jndiName) {
		this.jndiName = jndiName;
	}
	
	/**
	 * factory
	 */
	public String getContextFactory() {
		return contextFactory;
	}
	
	/**
	 * factory
	 */
	public void setContextFactory(String contextFactory) {
		this.contextFactory = contextFactory;
	}
	
	/**
	 * provideURL;
	 */
	public String getContextProvideURL() {
		return contextProvideURL;
	}

	/**
	 * provideURL;
	 */
	public void setContextProvideURL(String contextProvideURL) {
		this.contextProvideURL = contextProvideURL;
	}
	
	/**
	 * principal
	 */
	public String getContextPrincipal() {
		return contextPrincipal;
	}
	
	/**
	 * principal
	 */
	public void setContextPrincipal(String contextPrincipal) {
		this.contextPrincipal = contextPrincipal;
	}

	/**
	 * credentials
	 */
	public String getContextCredentials() {
		return contextCredentials;
	}
	
	/**
	 * credentials
	 */
	public void setContextCredentials(String contextCredentials) {
		this.contextCredentials = contextCredentials;
	}
	
	/**
	 * jdbc user ID
	 */
	public String getDbUser() {
		return dbUser;
	}
	
	/**
	 * jdbc user ID
	 */
	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}
	
	/**
	 * jdbc  Passsword
	 */
	public String getDbPassword() {
		return dbPassword;
	}
	
	/**
	 * jdbc  Passsword
	 */
	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}
	
	/**
	 * 检查参数信息
	 */
	public void check()throws SQLException{
		this.checkItem("JndiName",this.jndiName);
		this.checkItem("ContextFactory",this.contextFactory);
		this.checkItem("ContextProvideURL",this.contextProvideURL);
		this.checkItem("ContextPrincipal",this.contextPrincipal);
		super.check();
	}
	
	/**
	* 连接工厂
	*/
	public ConnectionFactory getConnectionFactory(){
		if (connectionFactory == null) {
			if (dbUser == null)
				this.connectionFactory = new JndiConnectionFactory(jndiName, contextFactory,contextProvideURL, contextPrincipal, contextCredentials);
			else
				this.connectionFactory = new JndiConnectionFactory(jndiName, contextFactory,contextProvideURL, contextPrincipal, contextCredentials, dbUser,dbPassword);
		}
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
