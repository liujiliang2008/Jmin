/*
 *Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.log;

/**
 * 日记输出等级
 * 
 * @author Chris Liao
 */
public class LogLevel {
	
	/**
	 * debug level
	 */
	public static final int DEBUG = 0;

	/**
	 * Info level
	 */
	public static final int INFO = 1;
	
	/**
	 * warning level
	 */
	public static final int WARN = 2;
	
	/**
	 * error level
	 */
	public static final int ERROR = 3;
	
	/**
	 * Fatal level
	 */
	public static final int FATAL = 4;
	

	/**
	 * debug level
	 */
	public static final String DEBUG_DESC = "DEBUG";

	/**
	 * Info level
	 */
	public static final String INFO_DESC = "INFO";
	
	/**
	 * warning level
	 */
	public static final String WARN_DESC = "WARN";
	
	/**
	 * error level
	 */
	public static final String ERROR_DESC = "ERROR";
	
	/**
	 * Fatal level
	 */
	public static final String FATAL_DESC = "FATAL";
	
	/**
	 * 等级描述
	 */
	public static String[] desc = new String[] { 
		DEBUG_DESC, 
		INFO_DESC, 
		WARN_DESC,
		ERROR_DESC, 
		FATAL_DESC};
	
	/**
	 * 获得等级描述
	 */
	public static String toLevelDesc(int code){
		return desc[code];
	}
}
