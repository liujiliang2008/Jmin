/*
 *Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.log.printer;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * 控制台输出
 * 
 * @author Chris Liao 
 */
public class PrintThread extends Thread{

 /**
	* printer list
	*/
	private List printerList=null;

	/**
	 * 打印区
	 */
	private LinkedList printBuffer = new LinkedList();
	
	/**
	 * 构造函数
	 */
	public PrintThread(List printerList){
		this.printerList= printerList;
	}
	
	/**
	 * 线程方法
	 */
	public void run(){
		while(true) {
			if(printBuffer.isEmpty()) {
				synchronized (printBuffer) {
					try {
						this.printBuffer.wait();
					} catch (InterruptedException e) {
					}
				}
			}
			
			synchronized(printBuffer){
				//this.inInPrinting=true;//开始打印
				Iterator itor = printBuffer.iterator();
				while(itor.hasNext()) {      
			     this.printLog((String)itor.next());
				 }
				this.printBuffer.clear();
				//this.inInPrinting=false;//打印完毕
			}
		}
	}

	/**
	 * 将Log放入缓存中:外部使用
	 */
	public void pushLog(String log){
		printBuffer.addLast(log);
		synchronized(printBuffer){
			printBuffer.notify();
		}
	}
	
	/**
	 * 打印输出:内外,都可调用
	 */
	public synchronized void printLog(String log) {
		Iterator itor = printerList.iterator();
		while(itor.hasNext()){
			try {
				LogPrinter printer = (LogPrinter)itor.next();
				printer.getPrintStream().println(log);
				printer.getPrintStream().flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
