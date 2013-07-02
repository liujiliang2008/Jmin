/*
* Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.jda.impl.execution.dialect;

import java.sql.SQLException;

import org.jmin.jda.JdaDialect;

/**
 * MySQL区间查询
 * 
 * @author Chris
 * @version 1.0
 */

public class MySQLDialect implements JdaDialect {
	
	/**
	 * 将一个普通的查询SQL语句转换为方言可以执行的SQL
	 *  //QUERY_SQL limit ?,?  使用limit关键字，第一个"?"是起始行号，第二个"?"是返回条目数
	 */
	public String getPageQuerySql(String sql,int startRow,int offset)throws SQLException{
		StringBuffer pagingSelect = new StringBuffer(100);   
	  pagingSelect.append(sql);  
	  pagingSelect.append(" limit ?,?");
	  return pagingSelect.toString();
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
