package org.jmin.jda.impl.execution.dialect;

import java.sql.SQLException;

import org.jmin.jda.JdaDialect;

public class InterBaseDialect  implements JdaDialect  {
	
	/**
	 * 将一个普通的查询SQL语句转换为方言可以执行的SQL
	 * QUERY_SQL row ? to ?
	 */
	public String getPageQuerySql(String sqlText,int startRow,int offset)throws SQLException{
		StringBuffer buff = new StringBuffer(100);  
	  buff.append(sqlText);  
	  buff.append(" row ? to ?");  
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