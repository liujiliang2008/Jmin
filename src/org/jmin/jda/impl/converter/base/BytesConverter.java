/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.converter.base;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.SQLException;

import org.jmin.jda.JdaTypeConvertException;
import org.jmin.jda.impl.converter.JdaTypeBaseConverter;
import org.jmin.jda.impl.util.BitUtil;
import org.jmin.jda.impl.util.CloseUtil;

/**
 * Byte数组转换器
 * 
 * @author Chris Liao
 */
public class BytesConverter extends JdaTypeBaseConverter{
	
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
		}else if(value instanceof Character){
			return new byte[]{(byte)(((Character)value).charValue())};
		}else if(value instanceof Number){
			Number num =(Number)value;
			return BitUtil.longToByte(num.longValue());
		}else if(value instanceof Blob){
			try {
				Blob blob =(Blob)value;
				int len = (int)blob.length();
				if(len>=1)
					return blob.getBytes(1,len);
				else
					return null;
			} catch (SQLException e) {
				throw new JdaTypeConvertException("Failed to get byte array from blob: "+ e.getMessage(),e);
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
			throw new JdaTypeConvertException("Doesn't support object conversion from type: "+ value.getClass().getName() + " to type: byte[]");
		}
	}
}
