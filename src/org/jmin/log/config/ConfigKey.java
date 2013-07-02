/*
*Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.log.config;

/**
 * 日记配置类
 * 
 * @author Chris Liao
 */

public class ConfigKey {
	
		/**
		 * 日记等级
		 */
	 	public static final String KEY_Level = "log.Level";
	 	
		/**
		 * 是否输出日记：总开关
		 */
    public static final String KEY_Need_Print_Log = "log.NeedPrintLog";
    
  	/**
  	 * 是否需要异步打印
  	 */
   	public static final String KEY_Need_Asyn_Print = "log.NeedAsynPrint";
   
  	/**
		 * 是否打印产生日记的类名
		 */
    public static final String KEY_Need_Print_Class = "log.NeedPrintClass";
    
  	/**
		 * 是否打印Thread信息
		 */
    public static final String KEY_Need_Print_Thread = "log.NeedPrintThread";
    
  	/**
		 * 是否打印产生日记的用户ID
		 */
    public static final String KEY_Need_Print_UserId = "log.NeedPrintUserId";
   
		/**
		 * 日记输出日期格式
		 */
    public static final String KEY_DateFormat = "log.DateFormat";
    
  
		/**
		 * 是否需要控制台输出
		 */
    public static final String KEY_Need_Print_Console = "log.NeedPrintConsole";
	
		/**
		 * 是否需要文件输出
		 */
    public static final String KEY_Need_Print_LogFile = "log.NeedPrintLogFile";
    
		/**
		 * 是否需要每日更换文件输出
		 */
    public static final String KEY_Need_Daily_LogFile = "log.NeedDailyLogFile";
    
	 	/**
	 	 * log输出文件名
	 	 */
		public static final String KEY_Pint_Log_FileName = "log.LogFileName";
		
		 /**
		 * 文件的最大内容:xxK
		 */
    public static final String KEY_Max_File_Size = "log.MaxFileSize";
		
  	/**
		 * 日记分类前缀
		 */
    public static final String KEY_Category = "log.Category";
}