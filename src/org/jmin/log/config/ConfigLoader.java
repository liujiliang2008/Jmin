/*
*Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.log.config;

import java.io.FileNotFoundException;
import java.util.Enumeration;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

import org.jmin.log.LogLevel;

/**
 * 日记配置
 * 
 * @author Chris Liao 
 */

public class ConfigLoader{
	
	/**
	 * 日记参数配置文件名,在当前路径中查找文件:logConfig.properties
	 */
	private final static String ClassPathLogResourceFile ="log";
	
	/**
	 * 默认的日记配置文件名:org.jmin.log.config.logConfig
	 */
	private final static String defaultLogResourceFile="org.jmin.log.config.log";
	
	/**
	 * 在当前路径中查找日记配置信息，并加栽，否则加栽默认的资源
	 */
	private ResourceBundle loadLogResourceBundle()throws FileNotFoundException{
		try {
			return ResourceBundle.getBundle(ClassPathLogResourceFile);
		} catch (MissingResourceException e) {
			try {
				return ResourceBundle.getBundle(defaultLogResourceFile);
			} catch (MissingResourceException e1) {
				throw new FileNotFoundException("Log config file[log.properties]not found in current class path,default configeruation will be used");
			}
		}
	}

	/**
	 * 加载日记的配置信息
	 */
	public Config loadLogCoinfigeruation()throws FileNotFoundException{
		ResourceBundle logBundle = this.loadLogResourceBundle();
		Config config = new Config();
		
		String level = this.getResourceValue(logBundle,ConfigKey.KEY_Level);
		if(LogLevel.DEBUG_DESC.equalsIgnoreCase(level)){
			config.setLogLevelCode(LogLevel.DEBUG);
		}else if(LogLevel.INFO_DESC.equalsIgnoreCase(level)){
			config.setLogLevelCode(LogLevel.INFO);
		}else if(LogLevel.WARN_DESC.equalsIgnoreCase(level)){
			config.setLogLevelCode(LogLevel.WARN);
		}else if(LogLevel.ERROR_DESC.equalsIgnoreCase(level)){
			config.setLogLevelCode(LogLevel.ERROR);
		}else if(LogLevel.FATAL_DESC.equalsIgnoreCase(level)){
			config.setLogLevelCode(LogLevel.FATAL);
		} 
		
		String needPrintLogInd = this.getResourceValue(logBundle,ConfigKey.KEY_Need_Print_Log);
		config.setNeedPrintLog(this.getBooleanValue(needPrintLogInd));
		
		String needAsynPrint = this.getResourceValue(logBundle,ConfigKey.KEY_Need_Asyn_Print);
		config.setNeedAsynPrint(this.getBooleanValue(needAsynPrint));
		
		String needPrintThreadInd = this.getResourceValue(logBundle,ConfigKey.KEY_Need_Print_Thread);
		config.setNeedPrintThread(this.getBooleanValue(needPrintThreadInd));
	
		String needPrintClassInd = this.getResourceValue(logBundle,ConfigKey.KEY_Need_Print_Class);
	  config.setNeedPrintClass(this.getBooleanValue(needPrintClassInd));
	 
		String needPrintUserID = this.getResourceValue(logBundle,ConfigKey.KEY_Need_Print_UserId);
		config.setNeedPrintUserId(this.getBooleanValue(needPrintUserID));
		
		String dateFormat = this.getResourceValue(logBundle,ConfigKey.KEY_DateFormat);
		if(!isNull(dateFormat))
			config.setLogDateFormat(dateFormat.trim());
		
	
		String needPrintConsoleInd = this.getResourceValue(logBundle,ConfigKey.KEY_Need_Print_Console);
		config.setNeedPrintConsole(this.getBooleanValue(needPrintConsoleInd));
		
		String needPrintLogFileInd = this.getResourceValue(logBundle,ConfigKey.KEY_Need_Print_LogFile);
		config.setNeedPrintLogFile(this.getBooleanValue(needPrintLogFileInd));
		
		String needDailyLogFileInd =this.getResourceValue(logBundle,ConfigKey.KEY_Need_Daily_LogFile);
		config.setNeedDailyLogFile(this.getBooleanValue(needDailyLogFileInd));
	
		String printLogFileName = this.getResourceValue(logBundle,ConfigKey.KEY_Pint_Log_FileName);
		if(!isNull(printLogFileName))
			config.setPrintLogFileName(printLogFileName.trim());
		
		
		String maxFileSize = this.getResourceValue(logBundle,ConfigKey.KEY_Max_File_Size);
		if(!isNull(maxFileSize))
			try {
				config.setMaxFileSize(Integer.parseInt(printLogFileName.trim()));
			}catch(Exception e) {
			}
	
		Enumeration keyEnum = logBundle.getKeys();
		Properties categoryMap = new Properties();
		config.setLogCategoryMap(categoryMap);
		
		while(keyEnum.hasMoreElements()){
			String key =(String)keyEnum.nextElement();
			if(key.indexOf(ConfigKey.KEY_Category)!=-1){
				String value= this.getResourceValue(logBundle,key);
				categoryMap.put(key.substring(ConfigKey.KEY_Category.length()+1,key.length()),value);
			}
		}
	
		return config;
	}
	
	 /**
   * A null string ?
   */
  private boolean isNull(String value) {
  	return value == null || value.trim().length()==0;
  }
	
	/**
	 * 获取Resource Value
	 */
	private String getResourceValue(ResourceBundle logBundle,String key){
		try {
			return logBundle.getString(key);
		} catch (MissingResourceException e) {
		  return null;
		}
	}
  
	/**
	 * 获得布耳值
	 */
	private boolean getBooleanValue(String textValue){
		if("True".equalsIgnoreCase(textValue)||"Y".equalsIgnoreCase(textValue)|| "ON".equalsIgnoreCase(textValue))
			return true;
		else if("False".equalsIgnoreCase(textValue)||"N".equalsIgnoreCase(textValue)||"OFF".equalsIgnoreCase(textValue))
			return false;
		else
			return false;
	}
}
