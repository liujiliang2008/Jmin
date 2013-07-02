 /*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda;

import java.sql.SQLException;

/**
 * JTA事务的信息
 * 
 * @author Chris Liao
 */

public final class UserTransactionInfo {
	
	/**
	 * jndi
	 */
	private String jndi;

	/**
	 * factory
	 */
	private String factory;

	/**
	 * provider
	 */
	private String provider;

	/**
	 * principal
	 */
	private String principal;

	/**
	 * credentials
	 */
	private String credentials;

	/**
	 * constructor
	 */
	public UserTransactionInfo(String jndi,String factory,String provider,String principal,String credentials)throws SQLException{
		this.jndi = jndi;
		this.factory = factory;
		this.provider=provider;
		this.principal = principal;
		this.credentials = credentials;

		this.validateDescItem("Jta.name",jndi);
		this.validateDescItem("Jta.factory",factory);
		this.validateDescItem("Jta.provider",provider);
		this.validateDescItem("Jta.principal",principal);
		//this.validateDescItem("Jta.credentials",credentials);
	}
	
	/**
	 * Return JNDI
	 */
	public String getJndi() {
		return jndi;
	}
	
	/**
	 *Return factry
	 */
	public String getFactory() {
		return factory;
	}
	
	/**
	 * Return Provider
	 */
	public String getProvider() {
		return provider;
	}
	
	/**
	 * Retturn Principal
	 */
	public String getPrincipal() {
		return principal;
	}

	/**
	 * Return password
	 */
	public String getCredentials() {
		return credentials;
	}

	/**
	 * 验证定义信息
	 */
	private void validateDescItem(String name,String value)throws SQLException{
		if(value == null || value.trim().length()==0)
			throw new SQLException(name+" can't be null");
	}
}
