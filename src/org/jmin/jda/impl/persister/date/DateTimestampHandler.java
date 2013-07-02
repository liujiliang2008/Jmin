/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.persister.date;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.jmin.jda.JdaTypeConverterMap;
import org.jmin.jda.JdaException;
import org.jmin.jda.impl.converter.JdaTypeConvertFactory;
import org.jmin.jda.impl.persister.ObjectHandler;

/**
 * 日期
 *
 * @author Chris
 */
public class DateTimestampHandler extends ObjectHandler {
	
	/**
	 * 获得持久化类型
	 */
	public Class getPersisterType(){
		return Timestamp.class;
	}
	
	 /**
   * 设置参数
   */
  public void set(PreparedStatement ps,int index,Object value,int typeCode,JdaTypeConverterMap converterMap)throws SQLException{
   try{
  	Timestamp parameter =(Timestamp)JdaTypeConvertFactory.convert(value,this.getPersisterType(),converterMap);
  	if(parameter== null)
  		ps.setNull(index,typeCode);
  	else
  		ps.setTimestamp(index,parameter);
   } catch (Throwable e) {
	 		throw new JdaException(null,e);
	 }	
  }
}