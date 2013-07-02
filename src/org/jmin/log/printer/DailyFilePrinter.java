/*
 *Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.log.printer;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.jmin.log.config.Config;

/**
 * 每日记文件写入 
 *
 * @author Chris Liao
 */

public class DailyFilePrinter extends FilePrinter {
	
	/**
	 * 当前日期
	 */
	private String currentDay ="";
	
	/**
	 * 日记的日历时间
	 */
	private LogCalendar logCalendar;
	
	/**
	 * 构造
	 */
	public DailyFilePrinter(Config config){
		super(config);
		this.logCalendar= LogCalendar.getCalendar();
	}
	
	/**
	 *获取打印流
	 */
	protected PrintStream getPrintStream()throws IOException{
		String tempDate = this.getCurrentDay();
		if(!tempDate.equalsIgnoreCase(currentDay)){
			this.fileNo=0;
			this.currentDay = tempDate;
			if(this.printStream!=null){
				this.printStream.close();
				this.printStream = null;
			}
		}
		
		//文件流为空
		if(this.file==null){
		  this.file =this.createLogFile(this.config.getPrintLogFileName());
			FileOutputStream fileStream = new FileOutputStream(this.file,false);
			this.printStream = new PrintStream(new BufferedOutputStream(fileStream,1024));
		}else{
			FileInputStream tempStream=new FileInputStream(file);
			int fileLength=tempStream.available();
		  tempStream.close();
			if(fileLength/1024 > config.getMaxFileSize()){//文件超过指定大小，使用新的文件
				this.printStream.close();
			  this.file =this.createLogFile(this.config.getPrintLogFileName());
				this.printStream = new PrintStream(new BufferedOutputStream(new FileOutputStream(this.file,false),1024));
			}
		}
		return this.printStream;
	}
	
	/**
	 * 解析获得Log文件名
	 */
	protected String getLogFileName(String filename){
		int index = filename.lastIndexOf(".");
		if(index > 0){
			return filename.substring(0,index)+ "_"+currentDay+"_"+(fileNo++)+"."+filename.substring(index+1);
		}else{
			return filename.substring(0,index)+ "_"+currentDay+"_"+(fileNo++);
		}
	}
	
	
	
	
	/**
	 * 获得当前日期
	 */
	private String getCurrentDay() {
		this.logCalendar.setTimeInMillis(System.currentTimeMillis());
		int year = this.logCalendar.get(Calendar.YEAR);
		int month = this.logCalendar.get(Calendar.MONTH)+1;
		int day = this.logCalendar.get(Calendar.DATE);
		
		return ""+ year +((month>=10)?""+month:"0"+month) + ((day>=10)?""+day:"0"+day);
	}
  static class LogCalendar extends GregorianCalendar{
		public static synchronized LogCalendar getCalendar() {
			return new LogCalendar();
		}
		public long getTimeInMillis() {
			return super.getTimeInMillis();
		}
		public void setTimeInMillis(long millis) {
			super.setTimeInMillis(millis);
		}
	}
}