/*
* Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.jda.impl.execution.dialect;

import java.sql.SQLException;

import org.jmin.jda.JdaDialect;

/**
 * Oracle区间查询
 * 
 * @author Chris
 * @version 1.0
 */
public class OracleDialect implements JdaDialect {

	/**
	 * 将一个普通的查询SQL语句转换为方言可以执行的SQL
	 * 	
	 * select * from (select a.*,rownum no from (SQL)a where rownum <=10) b where b.no >= 1
	 */
	public String getPageQuerySql(String sql,int startRow,int offset)throws SQLException{
		StringBuffer buff = new StringBuffer(100);
	  buff.append("select * from (select a.*,rownum no from (");
	  buff.append(sql);
	  buff.append(")a where rownum <=?)b where b.no >=?");   
	  return buff.toString();  
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
