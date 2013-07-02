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
 * Boolean Type
 * 
 * @author Chris
 */
public class BytesHandler extends ObjectHandler {
	
	/**
	 * 获得持久化类型
	 */
	public Class getPersisterType(){
		return byte[].class;
	}
	
	 /**
	 * 设置参数
	 */
  public void set(PreparedStatement ps,int index,Object value,int typeCode,JdaTypeConverterMap converterMap)throws SQLException{
   try{
	  	byte[] parameter =(byte[])JdaTypeConvertFactory.convert(value,this.getPersisterType(),converterMap);
	  	if(parameter== null)
	  		ps.setNull(index,typeCode);
	  	else
	  		ps.setBytes(index,parameter);
    } catch (Throwable e) {
			throw new JdaException(null,e);
	  }
  }
}
