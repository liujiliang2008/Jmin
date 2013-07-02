/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.persister;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.jmin.jda.JdaTypeConverterMap;
import org.jmin.jda.JdaException;
import org.jmin.jda.JdaTypePersister;
import org.jmin.jda.JdaRowCache;
import org.jmin.jda.impl.converter.JdaTypeConvertFactory;

/**
 * Boolean Type
 * 
 * @author Chris
 */
public class ObjectHandler implements JdaTypePersister {
	
	/**
	 * 获得持久化类型
	 */
	public Class getPersisterType(){
		return Object.class;
	}
	
  /**
   * 读取结果对象
   */
  public Object get(ResultSet rs,int index,JdaTypeConverterMap converterMap,JdaRowCache rowMap)throws SQLException{
		try {
			if(rowMap.isSetted(index)) {
				return JdaTypeConvertFactory.convert(rowMap.get(index),this.getPersisterType(),converterMap);
			}else{
				Object value = rs.getObject(index);
				rowMap.set(index, value);
				return JdaTypeConvertFactory.convert(value,this.getPersisterType(),converterMap);
			}
		} catch (Throwable e) {
			throw new JdaException(null,e);
		}
  }
  
  /**
   * 读取结果对象
   */
  public Object get(ResultSet rs,String columnName,JdaTypeConverterMap converterMap,JdaRowCache rowMap)throws SQLException{
		try {
			if(rowMap.isSetted(columnName)) {
				return JdaTypeConvertFactory.convert(rowMap.get(columnName),this.getPersisterType(),converterMap);
			}else{
				Object value = rs.getObject(columnName);
				rowMap.set(columnName.toLowerCase(), value);
				return JdaTypeConvertFactory.convert(value,this.getPersisterType(),converterMap);
			}
		} catch (Throwable e) {
			throw new JdaException(null,e);
		}
  }
  
	 /**
   * 设置参数
   */
  public void set(PreparedStatement ps,int index,Object value,int typeCode,JdaTypeConverterMap converterMap)throws SQLException{
   try {
  	Object parameter = JdaTypeConvertFactory.convert(value,this.getPersisterType(),converterMap);
  	if(parameter== null)
  		ps.setNull(index,typeCode);
  	else
  		ps.setObject(index,parameter);
  	} catch (Throwable e) {
			throw new JdaException(null,e);
		}
  }
}
