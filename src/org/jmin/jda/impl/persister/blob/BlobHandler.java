/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.persister.blob;

import java.io.ByteArrayInputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.jmin.jda.JdaTypeConverterMap;
import org.jmin.jda.JdaException;
import org.jmin.jda.impl.converter.JdaTypeConvertFactory;
import org.jmin.jda.impl.persister.ObjectHandler;

/**
 * Blob 类型
 * 
 * @author Chris Liao
 */

public class BlobHandler extends ObjectHandler {
	
	
	/**
	 * 获得持久化类型
	 */
	public Class getPersisterType(){
		return Blob.class;
	}
	
	 /**
   * 设置参数
   */
  public void set(PreparedStatement ps,int index,Object value,int typeCode,JdaTypeConverterMap converterMap)throws SQLException{
   try{
	  	byte[] bytes =(byte[])JdaTypeConvertFactory.convert(value,this.getPersisterType(),converterMap);
	  	if(bytes==null)
	  		ps.setNull(index,typeCode);
	  	else{
		    ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		    ps.setBinaryStream(index,bis,bytes.length);
	  	}
   } catch (Throwable e) {
			throw new JdaException(null,e);
	 }
  }
}