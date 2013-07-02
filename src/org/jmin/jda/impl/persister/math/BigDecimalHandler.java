/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.persister.math;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.jmin.jda.JdaTypeConverterMap;
import org.jmin.jda.JdaException;
import org.jmin.jda.impl.converter.JdaTypeConvertFactory;
import org.jmin.jda.impl.persister.ObjectHandler;

/**
 * 大型数字类型
 * 
 * @author Chris Liao
 */
public class BigDecimalHandler extends ObjectHandler {
	
	
	/**
	 * 获得持久化类型
	 */
	public Class getPersisterType(){
		return BigDecimal.class;
	}
	
	 /**
   * 设置参数
   */
  public void set(PreparedStatement ps,int index,Object value,int typeCode,JdaTypeConverterMap converterMap)throws SQLException{
    try{
	  	BigDecimal decimal =(BigDecimal)JdaTypeConvertFactory.convert(value,this.getPersisterType(),converterMap);
	  	if(decimal== null)
	  		ps.setNull(index,typeCode);
	  	else
	  		ps.setBigDecimal(index,decimal);
    } catch (Throwable e) {
	 		throw new JdaException(null,e);
	  }
  }
}
