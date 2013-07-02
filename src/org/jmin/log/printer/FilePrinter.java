/*
 *Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.log.printer;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.jmin.log.config.Config;

/**
 * 日记文件写入
 *
 * @author Chris Liao
 */

public class FilePrinter extends LogPrinter {
	
	/**
	 * 打印的日记文件的序号
	 */
	protected int fileNo;;
	
	/**
	 *打印文件
	 */
	protected File file= null;

	/**
	 * 流打印
	 */
	protected PrintStream printStream= null;
	
	/**
	 * 构造
	 */
	public FilePrinter(Config config) {
		super(config);
	}

	/**
	 *获取打印流
	 */
	protected PrintStream getPrintStream()throws IOException{
		if(this.file == null){
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
	 * 创建日记文件
	 */
	protected File createLogFile(String filename)throws IOException{
		this.prepareFileFolder(filename);
		return new File(getLogFileName(filename));
	}
	
	
	/**
	 * 准备文件名的目录
	 */
	protected void prepareFileFolder(String filename)throws IOException{
		File file =new File(filename);
		if(!file.exists()){
			String parentFolderName = file.getParent();
			if(parentFolderName!=null && parentFolderName.trim().length() >0){
			 File parent=new File(parentFolderName);
			 if(parent != null && !parent.exists()) 
				 parent.mkdirs();
			}
		}
	}

	/**
	 * 解析获得Log文件名
	 */
	protected String getLogFileName(String filename){
		int index = filename.lastIndexOf(".");
		if(index > 0){
			return filename.substring(0,index)+"_"+(fileNo++)+"."+filename.substring(index+1);
		}else{
			return filename.substring(0,index)+"_"+(fileNo++);
		}
	}
	
	/**
	 * 垃圾回收调用方法
	 */
	protected void finalize(){
		try{
			printStream.close();
		}catch(Exception e){
		}
	}
}