/*
 *Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.log.printer;

import java.io.IOException;
import java.io.PrintStream;

import org.jmin.log.config.Config;

/**
 * 控制台输出
 * 
 * @author Chris Liao 
 */

public class ConsolePrinter extends LogPrinter {
 
	/**
	 * 构造器
	 */
	public ConsolePrinter(Config config){
		super(config);
	}
	
	/**
	 * 获得打印流
	 */
	protected PrintStream getPrintStream()throws IOException{
   return System.out;
	}
}