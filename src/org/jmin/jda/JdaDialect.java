/*
* Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.jda;

import java.sql.SQLException;

/**
 * SQL方言
 * 
 * @author Chris
 */
public interface JdaDialect {
	
	/**
	 * 将一个普通SQL转换为分页查询的语句
	 */
	public String getPageQuerySql(String sqlText,int startRow,int offset)throws SQLException;
	
	/**
	 * 获得行的开始行号
	 */
	public int getStartRownum(int startRow,int offset)throws SQLException;
	
	/**
	 * 获得行的终止号
	 */
	public int getEndRownum(int startRow,int offset)throws SQLException;
	
}
