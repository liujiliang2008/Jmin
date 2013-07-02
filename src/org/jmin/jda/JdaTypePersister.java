/*
* Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.jda;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Jdbc type Persister
 * 
 * @author Chris
 * @version 1.0
 */
public interface JdaTypePersister {
	
	/**
	 * 获得持久化类型
	 */
	public Class getPersisterType();
	
  /**
   * 读取结果对象
   */
  public Object get(ResultSet rs,int index,JdaTypeConverterMap converterMap,JdaRowCache rowCache)throws SQLException;
  
  /**
   * 读取结果对象
   */
  public Object get(ResultSet rs,String column,JdaTypeConverterMap converterMap,JdaRowCache rowCache)throws SQLException;
  
	 /**
   * 设置参数
   */
  public void set(PreparedStatement ps,int index,Object value,int typeCode,JdaTypeConverterMap converterMap)throws SQLException;


}
