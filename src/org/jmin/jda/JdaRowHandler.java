 /*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda;

import java.sql.SQLException;
 
/**
 * 记录行处理器
 * 
 * @author Chris Liao
 */

public interface JdaRowHandler {

	/**
	 * 处理行记录对象
	 */
	public void handleRow(Object rowObject)throws SQLException;

}
