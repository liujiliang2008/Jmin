/*
* Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.jda.impl.connection.jndi;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.jmin.jda.impl.connection.ConnectionFactory;

/**
 * Jndi数据库连接工厂
 * 
 * @author Chris
 * @version 1.0
 */

class JndiConnectionFactory implements ConnectionFactory{
	
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
	 * jndi数据源
	 */
	private DataSource datasource=null;
	
	
	/**
	 * constructor
	 */
	public JndiConnectionFactory(String jndiName,String contextFactory,String contextProvideURL,String contextPrincipal,String contextCredentials){
		this(jndiName,contextFactory,contextProvideURL,contextPrincipal,contextCredentials,null,null);
	}

	/**
	 * constructor
	 */
	public JndiConnectionFactory(String jndiName,String contextFactory,String contextProvideURL,String contextPrincipal,String contextCredentials,String dbUser,String dbPassword){
		this.jndiName = jndiName;
		this.contextFactory = contextFactory;
		this.contextProvideURL =contextProvideURL;
		this.contextPrincipal = contextPrincipal;
		this.contextCredentials = contextCredentials;
		this.dbUser = dbUser;
		this.dbPassword =dbPassword;
	}
	
	/**
	 * 创建连接
	 */
	public Connection createConnection()throws SQLException{
		if(datasource == null)
			this.datasource = lookupDatasource();
		
		if(dbUser==null)
			return datasource.getConnection();
		else
			return datasource.getConnection(dbUser.trim(),dbPassword);
	}	
	
	/**
	 * 查找数据源
	 */
	private DataSource lookupDatasource()throws SQLException{
	   try {
	  	 Properties prop = new Properties();
			 prop.put(Context.PROVIDER_URL, this.contextProvideURL);
			 prop.put(Context.INITIAL_CONTEXT_FACTORY, this.contextFactory);
			 prop.put(Context.SECURITY_PRINCIPAL, this.contextPrincipal);
			 prop.put(Context.SECURITY_CREDENTIALS, this.contextCredentials);
			 InitialContext ctx = new InitialContext(prop);
			 return (DataSource)ctx.lookup(this.jndiName);
		} catch (NamingException e) {
			 throw new SQLException(e.getMessage());
		}
	}
}
