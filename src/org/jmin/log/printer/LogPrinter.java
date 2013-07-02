/*
*Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.log.printer;

import java.io.IOException;
import java.io.PrintStream;

import org.jmin.log.config.Config;

/**
 * 日记输出
 * 
 * @author Chris Liao 
 */
public abstract class LogPrinter {
	
	/**
	 * 配置
	 */
	protected Config config;

	/**
	 * 构造
	 */
	public LogPrinter(Config config){
		this.config = config;
	}
	
	/**
	 * 获得打印流
	 */
	protected abstract PrintStream getPrintStream()throws IOException;

}