/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.app.web;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.jmin.jda.JdaContainer;
import org.jmin.jda.JdaPageList;
import org.jmin.jda.JdaRowHandler;
import org.jmin.jda.JdaSession;
import org.jmin.jda.impl.config.SqlFileLoader;
 
/**
 * Dao访问Template
 * 
 * @author Chris
 */

public class DaoTemplate {
	
	/**
	 * source file
	 */
	private String sourceFile=null;
	
	/**
	 * needload
	 */
	private boolean needReload=true;
	
	/**
	 * Session工厂
	 */
	private JdaContainer container=null;
	
	/**
	 * SQL file 
	 */
	private SqlFileLoader sqlFileLoader =new SqlFileLoader();
	
	/**
	 * 构造函数
	 */
	public DaoTemplate(){}
	
	/**
	 * 构造函数
	 */
	public DaoTemplate(String sourceFile){
		this.setSourceFile(sourceFile);
	}
	
	/**
	 * 设置映射文件
	 */
	public void setSourceFile(String sourceFile){
		this.sourceFile = sourceFile;
		this.needReload=true;
	}
	
	/**
	 * 插入操作
	 */
	public int insert(String id) throws SQLException {
		return this.getSession().insert(id);
	}

	/**
	 * 插入操作
	 */
	public int insert(String id, Object paramObject) throws SQLException {
		return this.getSession().insert(id,paramObject);
	}

	/**
	 * 更新操作
	 */
  public int update(String id)throws SQLException{
	  return this.getSession().update(id);
	}
	/**
	 * 更新操作
	 */
	public int update(String id, Object paramObject) throws SQLException {
		return this.getSession().update(id,paramObject);
	}

	/**
	 * 删除操作
	 */
	public int delete(String id) throws SQLException {
		return this.getSession().delete(id);
	}

	/**
	 * 删除操作
	 */
	public int delete(String id, Object paramObject) throws SQLException {
		return this.getSession().delete(id,paramObject);
	}
	
	/**
	 * 查找一个对象，如果出现多个或出错，将抛出异常
	 */
	public Object findOne(String id) throws SQLException {
		return this.getSession().findOne(id);
	}

	/**
	 * 查找一个对象，如果出现多个或出错，将抛出异常
	 */
	public Object findOne(String id, Object paramObject) throws SQLException {
		return this.getSession().findOne(id,paramObject);
	}

	/**
	 * 查找一个对象，如果出现多个或出错，将抛出异常
	 */
	public Object findOne(String id, Object paramObject, Object resultObject)throws SQLException {
		return this.getSession().findOne(id,paramObject,resultObject);
	}

	/**
	 * 查找对象列表
	 */
	public List findList(String id) throws SQLException {
		return this.getSession().findList(id);
	}

	/**
	 * 查找对象列表
	 */
	public List findList(String id, Object paramObject) throws SQLException {
		return this.getSession().findList(id,paramObject);
	}
	
  /**
	 * 获取记录的结果记录数
	 */
  public int getResultSize(String id) throws SQLException{
  	return this.getSession().getResultSize(id);
  }	
  
  /**
	 *  获取记录的结果记录数
	 */
  public int getResultSize(String id,Object paramObject) throws SQLException{
  	return this.getSession().getResultSize(id,paramObject);
  }

	/**
	 * 查找对象列表，skip跳动位置，收集Rownumber记录
	 */
	public List findList(String id, int skip, int rowNum)throws SQLException {
		return this.getSession().findList(id,skip,rowNum);
	}
	
	/**
	 * 查找对象列表,skip跳动位置，收集Rownumber记录
	 */
	public List findList(String id, Object paramObject, int skip, int rowNum)throws SQLException {
		return this.getSession().findList(id,paramObject,skip,rowNum);
	}

	/**
	 * 查找对象Map,keyPropertyName作Key属性
	 */
	public Map findMap(String id,String keyPropertyName)throws SQLException {
		return this.getSession().findMap(id,keyPropertyName);
	}

	/**
	 * 查找对象Map,keyPropertyName作Key属性
	 */
	public Map findMap(String id,Object paramObject,String keyPropertyName)throws SQLException {
		return this.getSession().findMap(id,paramObject,keyPropertyName);
	}

	/**
	 * 查找对象Map,keyPropertyName作Key属性,keyPropValue属性为见键值
	 */
	public Map findMap(String id, Object paramObject,String keyPropName,String valuePropName) throws SQLException {
		return this.getSession().findMap(id,paramObject,keyPropName,valuePropName);
	}

	/**
	 * 通过rowhandlder进行查询
	 */
	public void findWithRowHandler(String id, JdaRowHandler rowHandler)throws SQLException {
		 this.getSession().findWithRowHandler(id,rowHandler);
	}

	/**
	 * 通过rowhandlder进行查询
	 */
	public void findWithRowHandler(String id, Object paramObject,JdaRowHandler rowHandler) throws SQLException {
		this.getSession().findWithRowHandler(id,paramObject,rowHandler);
	}

	/**
	 * 翻页查询
	 */
	public JdaPageList findPageList(String id, int pageSize)throws SQLException {
		return this.getSession().findPageList(id,pageSize);
	}

	/**
	 * 翻页查询
	 */
	public JdaPageList findPageList(String id, Object paramObject,int pageSize) throws SQLException {
		return this.getSession().findPageList(id,paramObject,pageSize);
	}

	/**
	 * 开始执行批量操作
	 */
	public void startBatch() throws SQLException {
		this.getSession().startBatch();
	}

	/**
	 * 执行批量操作
	 */
	public int executeBatch() throws SQLException {
		return this.getSession().executeBatch();
	}

	/**
	 * 是否在事物之中
	 */
	public boolean isInTransaction() throws SQLException {
		return this.getSession().isInTransaction();
	}
	
	/**
	 * 开始一个事务
	 */
	public void beginTransaction() throws SQLException {
		this.getSession().beginTransaction();
	}

	/**
	 * 提交一个事务
	 */
	public void commitTransaction() throws SQLException {
		 this.getSession().commitTransaction();
	}

	/**
	 * 回滚一个事务
	 */
	public void rollbackTransaction() throws SQLException {
		 this.getSession().rollbackTransaction();
	}
	
	 /**
	 * 获取一个可用的连接
	 */
	public Connection getConnection() throws SQLException{
		return this.getSession().getConnection();
	}

	/**
	 * 获得Session
	 */
	protected JdaSession getSession()throws SQLException{
		this.loadSource();
		return DaoTemplateUtil.getSession(this.container,this);
	}
	
	/**
	 * 装载配置
	 */
	protected synchronized void loadSource()throws SQLException{
		if(this.needReload) {
			if (this.sourceFile == null || sourceFile.trim().length() == 0)
				this.container = sqlFileLoader.load();
			else
				this.container = sqlFileLoader.load(sourceFile);
			this.needReload = false;
		}
	}
}
