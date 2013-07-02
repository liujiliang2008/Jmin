/*
*Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.log;

/**
 * 打印Log的来源
 * 
 * @author Chris Liao 
 */

public class LoggerInfo {
	
	/**
	 * 打印Log的Class的分类
	 */
	private String category = null;
	
	/**
	 * 打印日记的类名
	 */
	private String className = null;
	
	/**
	 * 日记打印的类短名
	 */
	private String classShortName = null;
	
	public LoggerInfo(String className){
		this.className = className;
	}
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getClassShortName() {
		return classShortName;
	}

	public void setClassShortName(String classShortName) {
		this.classShortName = classShortName;
	}
}
