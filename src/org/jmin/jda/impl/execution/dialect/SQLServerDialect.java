/*
* Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.jda.impl.execution.dialect;

import java.sql.SQLException;

import org.jmin.jda.JdaDialect;

/**
 *  SQLServer区间查询
 * 
 * @author Chris
 * @version 1.0
 */
public class SQLServerDialect implements JdaDialect {
	
	/**
	 * 将一个普通的查询SQL语句转换为方言可以执行的SQL
	 * 	QUERY_SQL limit ?,?
	 */
	public String getPageQuerySql(String sql,int startRow,int offset)throws SQLException{
   return sql;
	}
	
	/**
	 * 获得行的开始行号
	 */
	public int getStartRownum(int startRow,int offset)throws SQLException{
		return startRow;
	}
	
	/**
	 * 获得行的终止号
	 */
	public int getEndRownum(int startRow,int offset)throws SQLException{
		return startRow + offset;
	}
}
