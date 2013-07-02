/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.app.web;

import java.sql.SQLException;

/**
 * Dao访问抽象对象
 * 
 * @author Chris
 */

public abstract class Dao {
	
	/**
	 * 访问template
	 */
	private DaoTemplate daoTemplate;
	
	/**
	 * 构造函数
	 */
	public Dao(){}

	/**
	 * 构造函数
	 */
	public Dao(DaoTemplate daoTemplate){
		this.daoTemplate = daoTemplate;
	}

	/**
	 * 访问template
	 */
	public void setDaoTemplate(DaoTemplate daoTemplate) {
		this.daoTemplate = daoTemplate;
	}
	
	/**
	 * 访问template
	 */
	protected DaoTemplate getDaoTemplate() {
		return this.daoTemplate;
	}
	
	/**
	 * 是否在事物之中
	 */
	public boolean isInTransaction() throws SQLException {
		return this.daoTemplate.isInTransaction();
	}
	/**
	 * 开始一个事务
	 */
	public void beginTransaction() throws SQLException {
		this.daoTemplate.beginTransaction();
	}

	/**
	 * 提交一个事务
	 */
	public void commitTransaction() throws SQLException {
		 this.daoTemplate.commitTransaction();
	}

	/**
	 * 回滚一个事务
	 */
	public void rollbackTransaction() throws SQLException {
		 this.daoTemplate.rollbackTransaction();
	}
}
