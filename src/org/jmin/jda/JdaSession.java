/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 持久化的Session
 *
 * @author Chris Liao
 * @version 1.0
 */

public interface JdaSession{
	
	/**
	 *检查当前Session是否有效
	 */
	public boolean isClosed();
	
	/**
	 * 关闭当前的Session,使其变为不可用
	 */
	public void close()throws SQLException;
	
	/**
	 * 插入操作
	 */
	public int insert(String id)throws SQLException;
	
	/**
	 * 插入操作
	 */
	public int insert(String id,Object paramObject)throws SQLException;
	
	/**
	 * 更新操作
	 */
	public int update(String id)throws SQLException;
	
	/**
	 * 更新操作
	 */
	public int update(String id,Object paramObject)throws SQLException;

	/**
	 * 删除操作
	 */
	public int delete(String id)throws SQLException;
	
	/**
	 * 删除操作
	 */
	public int delete(String id,Object paramObject)throws SQLException;
	
	
  
	/**
	 * 查找一个对象，如果出现多个或出错，将抛出异常
	 */
  public Object findOne(String id) throws SQLException;
  
  /**
	 * 查找一个对象，如果出现多个或出错，将抛出异常
	 */
  public Object findOne(String id,Object paramObject) throws SQLException;
  
  /**
	 * 查找一个对象，如果出现多个或出错，将抛出异常
	 */
  public Object findOne(String id,Object paramObject,Object resultObject) throws SQLException;
  
  
  
 
  /**
	 * 查找对象列表
	 */
  public List findList(String id) throws SQLException;
  
  /**
	 * 查找对象列表
	 */
  public List findList(String id,Object paramObject)throws SQLException;
  
  /**
	 * 获取记录的结果记录数
	 */
  public int getResultSize(String id) throws SQLException;
  
  /**
	 *  获取记录的结果记录数
	 */
  public int getResultSize(String id,Object paramObject) throws SQLException;
  
  /**
	 * 查找对象列表，skip跳动位置，收集Rownumber记录
	 */
  public List findList(String id, int skip, int rowNum)throws SQLException;
  
  /**
	 * 查找对象列表,skip跳动位置，收集Rownumber记录
	 */
  public List findList(String id, Object paramObject, int skip, int rowNum) throws SQLException;
  

  
  /**
	 * 查找对象Map,keyPropertyName作Key属性
	 */
  public Map findMap(String id,String keyPropertyName) throws SQLException;
 
  /**
	 * 查找对象Map,keyPropertyName作Key属性
	 */
  public Map findMap(String id,Object paramObject,String keyPropertyName) throws SQLException;
  
  /**
	 * 查找对象Map,keyPropertyName作Key属性,keyPropValue属性为见键值
	 */
  public Map findMap(String id,Object paramObject,String keyPropName,String keyPropValue) throws SQLException;
  
  
  
  
  
  /**
   * 翻页查询
   */
  public JdaPageList findPageList(String id, int pageSize) throws SQLException;
  	
  /**
   * 翻页查询
   */
  public JdaPageList findPageList(String id, Object paramObject, int pageSize) throws SQLException;
  
  /**
   * 通过rowhandlder进行查询
   */
  public void findWithRowHandler(String id,JdaRowHandler rowHandler) throws SQLException;
  
  /**
   * 通过rowhandlder进行查询
   */
  public void findWithRowHandler(String id, Object paramObject, JdaRowHandler rowHandler) throws SQLException;
  
  
  
  
  
  /**
   * 开始执行批量操作
   */
  public void startBatch() throws SQLException;
  
  /**
   * 执行批量操作
   */
  public int executeBatch() throws SQLException;
  
 
	/**
	 * Session中是否存在事物中
	 */
	public boolean isInTransaction() throws SQLException;
	
	/**
	 * 开始一个事务
	 */
	public void beginTransaction()throws SQLException;
	
	/**
	 * 开始一个事务
	 */
	public void beginTransaction(int isolation)throws SQLException;
	
	/**
	 * 提交一个事务
	 */
	public void commitTransaction()throws SQLException;
	
	/**
	 * 回滚一个事务
	 */
	public void rollbackTransaction()throws SQLException;
	
	/**
	 * 获取一个可用的连接
	 */
	public Connection getConnection() throws SQLException ;
	
}