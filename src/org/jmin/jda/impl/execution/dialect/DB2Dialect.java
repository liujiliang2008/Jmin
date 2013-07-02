/*
* Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.jda.impl.execution.dialect;

import java.sql.SQLException;

import org.jmin.jda.JdaDialect;

/**
 * DB2区间查询
 * 
 * @author Chris
 * @version 1.0
 */
public class DB2Dialect implements JdaDialect {
	
	/**
	 * 将一个普通SQL转换为分页查询的语句
	 */
	public String getPageQuerySql(String sqlText,int startRow,int offset)throws SQLException{
		return sqlText;
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
