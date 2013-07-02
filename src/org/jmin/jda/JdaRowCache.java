/*
* Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.jda;

import java.sql.SQLException;

/**
 * 记录行
 * 
 * @author Chris
 */

public interface JdaRowCache {
	
	/**
	 * 获得列值
	 */
	public Object get(int index)throws SQLException;

	/**
	 * 获得列值
	 */
	public Object get(String column)throws SQLException;
	
	/**
	 *设置列值
	 */
	public void set(int index,Object value)throws SQLException;
	
	/**
	 *设置列值
	 */
	public void set(String column,Object value)throws SQLException;
	
	/**
	 * 获得列值
	 */
	public boolean isSetted(int index)throws SQLException;
	
	/**
	 * 获得列值
	 */
	public boolean isSetted(String column)throws SQLException;
	
}
