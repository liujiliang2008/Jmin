/*
 *Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.net.impl.util;

/**
 * 数字辅助类
 * 
 * @author Chris Liao 
 */
public class DigitUtil {
	
	/**
	 * 将Short数据转换为byte
	 */
	public static byte[] getBytes(short value) {
		return getBytes(value,true);
	}
	
	/**
	 * 将Short数据转换为byte
	 */
	public static byte[] getBytes(short value, boolean asc) {
		byte[] buf = new byte[2];
		if (asc)
			for (int i = buf.length - 1; i >= 0; i--) {
				buf[i] = (byte) (value & 0x00ff);
				value >>= 8;
			}
		else
			for (int i = 0; i < buf.length; i++) {
				buf[i] = (byte) (value & 0x00ff);
				value >>= 8;
			}
		return buf;
	}
	
	/**
	 * 将Int数据转换为byte
	 */
	public static byte[] getBytes(int value) {
		return getBytes(value,true);
	}
	
	/**
	 * 将Int数据转换为byte
	 */
	public static byte[] getBytes(int value, boolean asc) {
		byte[] buf = new byte[4];
		if (asc)
			for (int i = buf.length - 1; i >= 0; i--) {
				buf[i] = (byte) (value & 0x000000ff);
				value >>= 8;
			}
		else
			for (int i = 0; i < buf.length; i++) {
				buf[i] = (byte) (value & 0x000000ff);
				value >>= 8;
			}
		return buf;
	}
	
	/**
	 * 将long数据转换为byte
	 */
	public static byte[] getBytes(long value) {
		return getBytes(value,true);
	}
	
	/**
	 * 将long数据转换为byte
	 */
	public static byte[] getBytes(long value, boolean asc) {
		byte[] buf = new byte[8];
		if (asc)
			for (int i = buf.length - 1; i >= 0; i--) {
				buf[i] = (byte) (value & 0x00000000000000ff);
				value >>= 8;
			}
		else
			for (int i = 0; i < buf.length; i++) {
				buf[i] = (byte) (value & 0x00000000000000ff);
				value >>= 8;
			}
		return buf;
	}
	
	/**
	 * 将byte数据转换为Short
	 */
	public static short getShort(byte[] buf) {
		return getShort(buf,false);
	}
	
	/**
	 * 将byte数据转换为Short
	 */
	public static short getShort(byte[] buf, boolean asc) {
		if (buf == null) {
			throw new IllegalArgumentException("byte array is null!");
		}
		if (buf.length > 2) {
			throw new IllegalArgumentException("byte array size > 2 !");
		}
		short r = 0;
		if (asc)
			for (int i = buf.length - 1; i >= 0; i--) {
				r <<= 8;
				r |= (buf[i] & 0x00ff);
			}
		else
			for (int i = 0; i < buf.length; i++) {
				r <<= 8;
				r |= (buf[i] & 0x00ff);
			}
		return r;
	}
	
	
	/**
	 * 将byte数据转换为int
	 */
	public static int getInt(byte[] buf) {
		return getInt(buf,false);
	}
	
	/**
	 * 将byte数据转换为int
	 */
	public static int getInt(byte[] buf, boolean asc) {
		if (buf == null) {
			throw new IllegalArgumentException("byte array is null!");
		}
		if (buf.length > 4) {
			throw new IllegalArgumentException("byte array size > 4 !");
		}
		int r = 0;
		if (asc)
			for (int i = buf.length - 1; i >= 0; i--) {
				r <<= 8;
				r |= (buf[i] & 0x000000ff);
			}
		else
			for (int i = 0; i < buf.length; i++) {
				r <<= 8;
				r |= (buf[i] & 0x000000ff);
			}
		return r;
	}

	/**
	 * 将byte数据转换为int
	 */
	public static long getLong(byte[] buf) {
		return getLong(buf,false);
	}
	
	/**
	 * 将byte数据转换为long
	 */
	public static long getLong(byte[] buf, boolean asc) {
		if (buf == null) {
			throw new IllegalArgumentException("byte array is null!");
		}
		if (buf.length > 8) {
			throw new IllegalArgumentException("byte array size > 8 !");
		}
		long r = 0;
		if (asc)
			for (int i = buf.length - 1; i >= 0; i--) {
				r <<= 8;
				r |= (buf[i] & 0x00000000000000ff);
			}
		else
			for (int i = 0; i < buf.length; i++) {
				r <<= 8;
				r |= (buf[i] & 0x00000000000000ff);
			}
		return r;
	}
	
	public static void main(String[] args){
		System.out.println(getLong(getBytes(9999)));
	}
}
