/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.persister.math;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.jmin.jda.JdaTypeConverterMap;
import org.jmin.jda.JdaException;
import org.jmin.jda.impl.converter.JdaTypeConvertFactory;
import org.jmin.jda.impl.persister.ObjectHandler;

/**
 * 大型整形数字
 * 
 * @author Chris Liao
 */

public class BigIntegerHandler extends ObjectHandler {
	
	
	/**
	 * 获得持久化类型
	 */
	public Class getPersisterType(){
		return BigInteger.class;
	}

	 /**
   * 设置参数
   */
  public void set(PreparedStatement ps,int index,Object value,int typeCode,JdaTypeConverterMap converterMap)throws SQLException{
    try{
	  	BigInteger decimal =(BigInteger)JdaTypeConvertFactory.convert(value,this.getPersisterType(),converterMap);
	  	if(decimal== null)
	  		ps.setNull(index,typeCode);
	  	else
	  		ps.setLong(index,decimal.longValue());
    } catch (Throwable e) {
	 		throw new JdaException(null,e);
	  }
  }
}
