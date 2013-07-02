/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.converter.blob;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import org.jmin.jda.JdaTypeConvertException;
import org.jmin.jda.impl.converter.JdaTypeBaseConverter;
import org.jmin.jda.impl.util.BitUtil;
import org.jmin.jda.impl.util.CloseUtil;

/**
 * 转换为Blob
 * 
 * @author chris
 */
public class BlobConverter extends JdaTypeBaseConverter{
	
	/**
	 * 转换为目标类型
	 */
	public Object convert(Object value)throws JdaTypeConvertException{
		if(value ==null){
			return null;
		}else if(value instanceof byte[]){
			return value;
		}else if(value instanceof String){
		  return ((String)value).getBytes();
		}else if(value instanceof Byte){
			return new byte[]{((Byte)value).byteValue()};
		}else if(value instanceof Short){
			return BitUtil.shortToByte(((Short)value).shortValue());
		}else if(value instanceof Integer){
			return BitUtil.intToByte(((Integer)value).intValue());
		}else if(value instanceof Long){
			return BitUtil.longToByte(((Long)value).longValue());
		}else if(value instanceof Float){
			return BitUtil.floatToByte(((Float)value).floatValue());
		}else if(value instanceof Double){
			return BitUtil.doubleToByte(((Double)value).doubleValue());
		}else if(value instanceof Character){
			return new byte[]{(byte)(((Character)value).charValue())};
		}else if(value instanceof BigInteger){
			return BitUtil.longToByte(((BigInteger)value).longValue());
		}else if(value instanceof BigDecimal){
			return BitUtil.doubleToByte(((BigDecimal)value).doubleValue());
		}else if(value instanceof Date){
			long longValue =((Date)value).getTime();
			return BitUtil.longToByte(longValue);
		}else if(value instanceof Calendar){
			long longValue =((Calendar)value).getTime().getTime();
			return BitUtil.longToByte(longValue);
		}else if(value instanceof Blob){
			try {
				Blob blob =(Blob)value;
				int len = (int)blob.length();
				if(len>=1)
					return blob.getBytes(1,len);
				else
					return null;
			}catch(SQLException e) {
				throw new JdaTypeConvertException("Fail to get byte array from blob:"+e.getMessage(),e);
			}
		}else if(value instanceof Serializable){
			ByteArrayOutputStream byteStream=null;
			ObjectOutputStream objectStream=null;
			byteStream=new ByteArrayOutputStream();
			try {
				objectStream=new ObjectOutputStream(byteStream);
				objectStream.writeObject(value);
				objectStream.flush();
				return byteStream.toByteArray();
			} catch (IOException e) {
				 throw new JdaTypeConvertException("Can't get serializable byte array: "+ e.getMessage(),e);
			}finally{
				CloseUtil.close(byteStream);
				CloseUtil.close(objectStream);
			}
		}else{
			throw new JdaTypeConvertException("Doesn't support object conversion from type: "+ value.getClass().getName() + " to type: String");
		}
	}
}
