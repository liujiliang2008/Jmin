/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.gui.component;

/**
 * Table数据列描述
 * 
 * @author Chris
 */

public interface GuiTableRowValue {
	
	/**
	 * 获得属性名
	 */
	public String[] getPropertyNames();
	
	/**
	 * 获得属性名
	 */
	public Object getPropertyValue(String name);
	
	/**
	 * 设置获得属
	 */
	public void setPropertyValue(String name,Object value);

}
