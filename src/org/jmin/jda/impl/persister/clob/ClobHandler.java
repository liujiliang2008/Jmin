/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.persister.clob;

import java.io.StringReader;
import java.sql.Clob;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.jmin.jda.JdaTypeConverterMap;
import org.jmin.jda.JdaException;
import org.jmin.jda.impl.converter.JdaTypeConvertFactory;
import org.jmin.jda.impl.persister.ObjectHandler;

/**
 * Clob 类型
 * 
 * @author Chris Liao
 */

public class ClobHandler extends ObjectHandler {
	
	
	/**
	 * 获得持久化类型
	 */
	public Class getPersisterType(){
		return Clob.class;
	}
	
	 /**
   * 设置参数
   */
  public void set(PreparedStatement ps,int index,Object value,int typeCode,JdaTypeConverterMap converterMap)throws SQLException{
  	try{
	  	String clobText =(String)JdaTypeConvertFactory.convert(value,this.getPersisterType(),converterMap);
	  	if(clobText==null)
	  		ps.setNull(index,typeCode);
	  	else{
	      StringReader reader = new StringReader(clobText);
	      ps.setCharacterStream(index,reader,clobText.length());
	  	}
   } catch (Throwable e) {
			throw new JdaException(null,e);
	 }	
  }
}