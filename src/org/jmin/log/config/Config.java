/*
*Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.log.config;

import java.util.HashMap;
import java.util.Map;

/**
 * 日记配置类
 * 
 * @author Chris Liao
 */

public class Config {


	/**
	 * 日记输出等级，大于该等级的都将输出
	 */
	private int logLevelCode = 0;
	
	/**
	 * 是否需要日记打印：总开关
	 */
	private boolean needPrintLog = true;
	
	/**
	 * 日记输出模式：同步，异步
	 */
	private boolean needAsynPrint=false;

	/**
	 * 是否需要打印线程信息
	 */
	private boolean needPrintThread = false;
	
	/**
	 * 是否需要打印日记打印的文件类
	 */
	private boolean needPrintClass = false;
	
	/**
	 * 是否需要打印当前用户的日记用户ID
	 */
	private boolean needPrintUserId = false;
	
	/**
	 * 日记输出的时间格式
	 */
	private String logDateFormat="yyyy-MM-dd hh:mm:ss SSS";
	
	/**
	 * 日记分类类型映射
	 */
	private Map logCategoryMap = new HashMap();
	
	
	/**
	 * 是否需要将日记打印在控制台
	 */
	private boolean needPrintConsole = true;
	
	/**
	 * 是否需要将日记输出到文件
	 */
	private boolean needPrintLogFile = false;
	
	/**
	 * 日记文件是否每天残产生一个
	 */
	private boolean needDailyLogFile = false;
	
	/**
	 * 日记的输出文件名
	 */
	private String printLogFileName="log.txt";

	/**
	 * 日记的内容的最大大小,默认大小为10000KB
	 */
	private int maxFileSize=10000;
	

	/**
	 * 日记输出等级，大于该等级的都将输出
	 */
	public int getLogLevelCode() {
		return logLevelCode;
	}
	
	/**
	 * 日记输出等级，大于该等级的都将输出
	 */
	public void setLogLevelCode(int level) {
		this.logLevelCode = level;
	}
	
	/**
	 * 是否需要日记打印：总开关
	 */
	public boolean isNeedPrintLog() {
		return needPrintLog;
	}
	
	/**
	 * 是否需要日记打印：总开关
	 */
	public void setNeedPrintLog(boolean needPrintLog) {
		this.needPrintLog = needPrintLog;
	}
	
	/**
	 * 日记输出模式：同步，异步
	 */
	public boolean isNeedAsynPrint() {
		return needAsynPrint;
	}
	
 /**
	* 日记输出模式：同步，异步
	*/
	public void setNeedAsynPrint(boolean needAsynPrint) {
		this.needAsynPrint = needAsynPrint;
	}

	/**
	 * 是否需要打印线程信息
	 */
	public boolean isNeedPrintThread() {
		return needPrintThread;
	}
	
	/**
	 * 是否需要打印线程信息
	 */
	public void setNeedPrintThread(boolean needPrintThread) {
		this.needPrintThread = needPrintThread;
	}
	
	/**
	 * 是否需要打印日记打印的文件类
	 */
	public boolean isNeedPrintClass() {
		return needPrintClass;
	}
	
	/**
	 * 是否需要打印日记打印的文件类
	 */
	public void setNeedPrintClass(boolean needPrintClass) {
		this.needPrintClass = needPrintClass;
	}
	
	/**
	 * 是否需要打印当前用户的日记用户ID
	 */
	public boolean isNeedPrintUserId() {
		return needPrintUserId;
	}
	
	/**
	 * 是否需要打印当前用户的日记用户ID
	 */
	public void setNeedPrintUserId(boolean needPrintUserId) {
		this.needPrintUserId = needPrintUserId;
	}
	
	/**
	 * 日记输出的时间格式
	 */
	public String getLogDateFormat() {
		return logDateFormat;
	}
	
	/**
	 * 日记输出的时间格式
	 */
	public void setLogDateFormat(String logDateFormat) {
		this.logDateFormat = logDateFormat;
	}
	
	/**
	 * 日记分类类型映射
	 */
	public void setLogCategoryMap(Map logCategoryMap){
		this.logCategoryMap = logCategoryMap;
	}
	
	/**
	 * 日记分类类型映射
	 */
	public Map getLogCategoryMap(){
		return this.logCategoryMap;
	}
	
	
	
	/**
	 * 是否需要将日记打印在控制台
	 */
	public boolean isNeedPrintConsole() {
		return needPrintConsole;
	}
	
	/**
	 * 是否需要将日记打印在控制台
	 */
	public void setNeedPrintConsole(boolean needPrintConsole) {
		this.needPrintConsole = needPrintConsole;
	}
	
	/**
	 * 是否需要将日记输出到文件
	 */
	public boolean isNeedPrintLogFile() {
		return needPrintLogFile;
	}
	
	/**
	 * 是否需要将日记输出到文件
	 */
	public void setNeedPrintLogFile(boolean logFilePrint) {
		this.needPrintLogFile = logFilePrint;
	}
	
	/**
	 * 日记文件是否每天残产生一个
	 */
	public boolean isNeedDailyLogFile() {
		return needDailyLogFile;
	}
	
	/**
	 * 日记文件是否每天残产生一个
	 */
	public void setNeedDailyLogFile(boolean needDailyLogFile) {
		this.needDailyLogFile = needDailyLogFile;
	}

	/**
	 * 日记的输出文件名
	 */
	public String getPrintLogFileName() {
		return printLogFileName;
	}
	
	/**
	 * 日记的输出文件名
	 */
	public void setPrintLogFileName(String printLogFileName) {
		this.printLogFileName = printLogFileName;
	}
	
	/**
	 * 日记的内容的最大大小
	 */
	public int getMaxFileSize() {
		return maxFileSize;
	}
	
	/**
	 * 日记的内容的最大大小
	 */
	public void setMaxFileSize(int maxFileSize) {
		this.maxFileSize = maxFileSize;
	}
}