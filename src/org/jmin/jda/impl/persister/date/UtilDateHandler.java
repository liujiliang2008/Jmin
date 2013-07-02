/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.persister.date;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import org.jmin.jda.JdaTypeConverterMap;
import org.jmin.jda.JdaException;
import org.jmin.jda.impl.converter.JdaTypeConvertFactory;
import org.jmin.jda.impl.persister.ObjectHandler;

/**
 * 日期
 *
 * @author Chris
 */
public class UtilDateHandler extends ObjectHandler {
	
	/**
	 * 获得持久化类型
	 */
	public Class getPersisterType(){
		return Date.class;
	}
	 /**
   * 设置参数
   */
  public void set(PreparedStatement ps,int index,Object value,int typeCode,JdaTypeConverterMap converterMap)throws SQLException{
  	try{
	  	Date parameter =(Date)JdaTypeConvertFactory.convert(value,this.getPersisterType(),converterMap);
	  	if(parameter== null)
	  		ps.setNull(index,typeCode);
	  	else
	  		ps.setDate(index,new java.sql.Date(parameter.getTime()));
  	} catch (Throwable e) {
	 		throw new JdaException(null,e);
	  }	
  }
}