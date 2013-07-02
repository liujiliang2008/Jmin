/*
* Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.jda;

/**
 * jndi DataSource Definition
 * 
 * @author Chris
 * @version 1.0
 */
public final class JndiSourceInfo extends JdaSourceInfo{
	
	/**
	 * jndi
	 */
	private String contextName;

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
	 * constructor
	 */
	public JndiSourceInfo(){}
	
	/**
	 * constructor
	 */
	public JndiSourceInfo(String jndiName,String contextFactory,String contextProvideURL,String contextPrincipal,String contextCredentials){
		this.contextName =jndiName;
		this.contextFactory =contextFactory;
		this.contextProvideURL = contextProvideURL;
		this.contextCredentials = contextCredentials;
	}
	
	/**
	 * jndi
	 */
	public String getContextName() {
		return contextName;
	}
	
	/**
	 * jndi
	 */
	public void setContextName(String jndiName) {
		this.contextName = jndiName;
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
	
}
