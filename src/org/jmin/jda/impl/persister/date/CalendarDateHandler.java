/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.persister.date;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;

import org.jmin.jda.JdaTypeConverterMap;
import org.jmin.jda.JdaException;
import org.jmin.jda.impl.converter.JdaTypeConvertFactory;
import org.jmin.jda.impl.persister.ObjectHandler;

/**
 * 日期
 *
 * @author Chris
 */
public class CalendarDateHandler extends ObjectHandler {
	
	/**
	 * 获得持久化类型
	 */
	public Class getPersisterType(){
		return Calendar.class;
	}
	
	 /**
   * 设置参数
   */
  public void set(PreparedStatement ps,int index,Object value,int typeCode,JdaTypeConverterMap converterMap)throws SQLException{
  try{
  	Calendar parameter =(Calendar)JdaTypeConvertFactory.convert(value,this.getPersisterType(),converterMap);
  	if(parameter== null)
  		ps.setNull(index,typeCode);
  	else
  		ps.setDate(index,new java.sql.Date(parameter.getTime().getTime()));
   } catch (Throwable e) {
		throw new JdaException(null,e);
   }	
  }
}