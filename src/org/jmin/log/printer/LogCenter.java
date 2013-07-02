/*
 *Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.log.printer;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jmin.log.LogLevel;
import org.jmin.log.LoggerInfo;
import org.jmin.log.config.Config;
import org.jmin.log.config.ConfigLoader;

/**
 * 打印管理中心
 * 
 * @author Chris Liao 
 */

public class LogCenter {

	/**
	 * 打印配置
	 */
	private Config config = null;
	
	/**
	 * 日期根式化
	 */
	protected DateFormat dateFormat=null;
	
	/**
	 * 打印信息的Printer列表
	 */
	private List printerList = new ArrayList();
	
	/**
	 * 打印驱动线程
	 */
	private PrintThread printThread=new PrintThread(printerList);
	
	/**
	 * 构造
	 */
	public LogCenter(){
		this.createLogWriter();
	}
	
	/**
	 * 创建日记的打印者
	 */
	private void createLogWriter(){
		try {
			ConfigLoader configLoader = new ConfigLoader();
			this.config = configLoader.loadLogCoinfigeruation();
		} catch (FileNotFoundException e) {
			System.err.println(e);
			this.config = new Config();
		}
		
		if(config.isNeedPrintConsole())
			printerList.add(new ConsolePrinter(config));
	
		if(config.isNeedPrintLogFile()){
			if(isNull(config.getPrintLogFileName())){
				System.err.println("Config error,log filename can't be null,default log filename will be used.");
				config.setPrintLogFileName("log.txt");
			}
			
			if(config.isNeedDailyLogFile())
				printerList.add(new DailyFilePrinter(config));
			else 
				printerList.add(new FilePrinter(config));
		}
		
		//指定打印日期格式
		String dateFormatDesc = config.getLogDateFormat();
		if(!isNull(dateFormatDesc)){
			try{
				dateFormat = new SimpleDateFormat(dateFormatDesc);
			}catch(Exception e){
				System.err.println("Error log date format:"+dateFormatDesc +",default date format[yyyy-MM-dd hh:mm:ss SSS] will be used.");
				dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss SSS");
			}
		}
		
	 if(config.isNeedAsynPrint()){
		 printThread.setDaemon(true);
		 printThread.start();
		}
	}
	
	/**
	 *打印debug级别消息
	 */
	public void debug(Object obj,LoggerInfo logSource) {
		debug(null,obj,logSource);
	}
	
	/**
	 *打印debug级别消息
	 */
	public void debug(Object obj,Throwable e,LoggerInfo logSource) {
		debug(null,obj,e,logSource);
	}
	
	/**
	 *打印debug级别消息
	 */
	public void debug(Object OID,Object obj,LoggerInfo logSource) {
		debug(OID,obj,null,logSource);
	}

	/**
	 *打印debug级别消息
	 */
	public void debug(Object OID,Object obj,Throwable e,LoggerInfo logSource) {
		print(LogLevel.DEBUG,OID,obj,e,logSource);
	}
	
	/**
	 *打印info级别消息
	 */
	public void info(Object obj,LoggerInfo logSource) {
		info(null,obj,logSource);
	}
	
	/**
	 *打印info级别消息
	 */
	public void info(Object obj,Throwable e,LoggerInfo logSource) {
		info(null,obj,e,logSource);
	}
	
	/**
	 *打印info级别消息
	 */
	public void info(Object OID,Object obj,LoggerInfo logSource) {
		info(OID,obj,null,logSource);
	}

	/**
	 *打印info级别消息
	 */
	public void info(Object OID,Object obj,Throwable e,LoggerInfo logSource) {
		print(LogLevel.INFO,OID,obj,e,logSource);
	}
	
	/**
	 *打印warn级别消息
	 */
	public void warn(Object obj,LoggerInfo logSource) {
		warn(null,obj,logSource);
	}
	
	/**
	 *打印warn级别消息
	 */
	public void warn(Object obj,Throwable e,LoggerInfo logSource) {
		warn(null,obj,e,logSource);
	}
	
	/**
	 *打印warn级别消息
	 */
	public void warn(Object OID,Object obj,LoggerInfo logSource) {
		warn(OID,obj,null,logSource);
	}

	/**
	 *打印warn级别消息
	 */
	public void warn(Object OID,Object obj,Throwable e,LoggerInfo logSource) {
		print(LogLevel.WARN,OID,obj,e,logSource);
	}
	
	/**
	 *打印error级别消息
	 */
	public void error(Object obj,LoggerInfo logSource) {
		error(null,obj,logSource);
	}
	
	/**
	 *打印error级别消息
	 */
	public void error(Object obj,Throwable e,LoggerInfo logSource) {
		error(null,obj,e,logSource);
	}
	
	/**
	 *打印error级别消息
	 */
	public void error(Object OID,Object obj,LoggerInfo logSource) {
		error(OID,obj,null,logSource);
	}

	/**
	 *打印error级别消息
	 */
	public void error(Object OID,Object obj,Throwable e,LoggerInfo logSource) {
		print(LogLevel.ERROR,OID,obj,e,logSource);
	}

	/**
	 *打印fatal级别消息
	 */
	public void fatal(Object obj,LoggerInfo logSource) {
		fatal(null,obj,logSource);
	}
	
	/**
	 *打印fatal级别消息
	 */
	public void fatal(Object obj,Throwable e,LoggerInfo logSource) {
		fatal(null,obj,e,logSource);
	}
	
	/**
	 *打印fatal级别消息
	 */
	public void fatal(Object OID,Object obj,LoggerInfo logSource) {
		fatal(OID,obj,null,logSource);
	}

	/**
	 *打印fatal级别消息
	 */
	public void fatal(Object OID,Object obj,Throwable e,LoggerInfo logSource) {
		print(LogLevel.FATAL,OID,obj,e,logSource);
	}

	/**
	 *打印消息
	 */
	public void print(int levelCode,Object OID,Object obj,Throwable e,LoggerInfo logSource) {
		if(this.config.isNeedPrintLog()){
			if(levelCode >= this.config.getLogLevelCode()){
				String log = createLog(levelCode,LogLevel.toLevelDesc(levelCode),OID,obj,e,logSource);
				if(config.isNeedAsynPrint()){//异步打印
				   printThread.pushLog(log);
				 }else{//同步打印
					 printThread.printLog(log);
				 }
			}
		}
	}
	
	/**
	 * 创建消息打印内容
	 */
	private String createLog(int levelCode,String levelDesc,Object oid,Object obj,Throwable e,LoggerInfo loggerInfo) {
		StringBuffer buff = new StringBuffer();
		buff.append(dateFormat.format(new Date()));
		String category = loggerInfo.getCategory();

		if (!isNull(category))
			buff.append(" [" + category + "-" + levelDesc + "]");
		else
			buff.append(" [" + levelDesc + "]");

		if(config.isNeedPrintUserId())
			buff.append("[User:"+LogVariable.get(LogUserIdSetter.Log_User_ID) + "]");
		if(config.isNeedPrintThread())
			buff.append("[Thread:" + Thread.currentThread().getName() + "]");
		if(this.config.isNeedPrintClass())
			buff.append("[Class:" + loggerInfo.getClassName() + "]");
		if(oid != null)
			buff.append("[ID:" + oid + "]");
		
		buff.append(" - ");
		if(obj != null){
			if(obj instanceof Throwable){
				buff.append(getExceptionStack((Throwable)obj));
			}else{
				buff.append(obj);
			}
		}
		
		if(e!= null) {
			if(obj!=null)
				buff.append("\n");
			buff.append(getExceptionStack(e));
		}
		return buff.toString();
	}

	/**
	 * 获取分类
	 */
	public String getCategory(String invokeClass){
		String category ="";
		Iterator itor = config.getLogCategoryMap().entrySet().iterator();
		while(itor.hasNext()){
			Map.Entry entry = (Map.Entry)itor.next();
			String key = (String)entry.getKey();
			if(invokeClass.indexOf(key)!=-1){
				category = (String)entry.getValue();
				break;
			}
		}
		return category;
	}

  /**
   * 获得异常堆栈信息
   */
  private String getExceptionStack(Throwable e){
		ByteArrayOutputStream byteStream =null;
		PrintStream printTemp =null;
		
		try {
			 byteStream = new ByteArrayOutputStream();
			 printTemp = new PrintStream(byteStream);
			 e.printStackTrace(printTemp);
			 return byteStream.toString();
		}finally{
			try {
				byteStream.close();
			} catch (Throwable e1) {
			}
			try {
				printTemp.close();
			} catch (Throwable e1) {
			}
		}
  }
  
	/**
  * A null string ?
  */
 private boolean isNull(String value) {
 	return value == null || value.trim().length()==0;
 }
}
