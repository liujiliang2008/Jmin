/*
* Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.jda.impl.execution.base;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.jmin.jda.JdaRowCache;

/**
 * 记录行
 * 
 * @author Chris
 */

public class JdbRowCacheImpl implements JdaRowCache{
	
	/**
	 * 结构
	 */
	private Map dataMap = new HashMap();
	
	/**
	 * 获得列值
	 */
	public boolean isSetted(int index)throws SQLException{
		return	dataMap.containsKey(new Integer(index));
	}

	/**
	 * 获得列值
	 */
	public boolean isSetted(String column)throws SQLException{
		if(column!=null)
			return dataMap.containsKey(column.trim().toUpperCase());
		else
			return false;
	}

	/**
	 * 获得列值
	 */
	public Object get(int index)throws SQLException{
		return	dataMap.get(new Integer(index));
	}

	/**
	 * 获得列值
	 */
	public Object get(String column)throws SQLException{
		if(column!=null)
			return dataMap.get(column.trim().toUpperCase());
		else
			return null;
	}
	
	/**
	 *设置列值
	 */
	public void set(int index,Object value)throws SQLException{
		dataMap.put(new Integer(index),value);
	}
	
	/**
	 *设置列值
	 */
	public void set(String column,Object value)throws SQLException{
		if(column!=null)
			dataMap.put(column.trim().toUpperCase(),value);
	}
}
