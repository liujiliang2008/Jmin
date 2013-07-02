/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.persister.base;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.jmin.jda.JdaTypeConverterMap;
import org.jmin.jda.JdaException;
import org.jmin.jda.impl.converter.JdaTypeConvertFactory;
import org.jmin.jda.impl.persister.ObjectHandler;

/**
 * Double Type
 * 
 * @author Chris
 */
public class DoubleHandler extends ObjectHandler {
	
	/**
	 * 获得持久化类型
	 */
	public Class getPersisterType(){
		return Double.class;
	}

	 /**
   * 设置参数
   */
  public void set(PreparedStatement ps,int index,Object value,int typeCode,JdaTypeConverterMap converterMap)throws SQLException{
    try{
	  	Double parameter =(Double)JdaTypeConvertFactory.convert(value,this.getPersisterType(),converterMap);
	  	if(parameter== null)
	  		ps.setNull(index,typeCode);
	  	else
	  		ps.setDouble(index,parameter.doubleValue());
    } catch (Throwable e) {
			throw new JdaException(null,e);
	  }
  }
}
