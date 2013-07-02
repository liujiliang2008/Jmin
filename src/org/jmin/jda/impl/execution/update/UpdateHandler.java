/*
* Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.jda.impl.execution.update;

import java.sql.SQLException;

import org.jmin.jda.impl.JdaSessionImpl;
import org.jmin.jda.impl.execution.SqlRequest;
import org.jmin.jda.impl.execution.SqlRequestFactory;
import org.jmin.jda.impl.execution.SqlRequestHandler;
import org.jmin.jda.impl.statement.SqlBaseStatement;
import org.jmin.jda.impl.statement.SqlOperationType;

/**
 * 一般性普通更新操作执行
 * 
 * @author Chris Liao
 */

public class UpdateHandler {
	
	/**
	 * 执行更新操作
	 */
   public static int update(JdaSessionImpl session,SqlBaseStatement statement,Object paramObject,SqlOperationType operateType)throws SQLException {
  	 SqlRequest request=null;
  	 try {
  		 request=SqlRequestFactory.createSqlRequest(session,statement,paramObject,operateType);
  		 if(session.getBatchUpdateList()!=null){//当前已经创建一个批量处理列表，所以需要登记进入批量处理
				 session.getBatchUpdateList().addBatchRequest(request);
				 return 0;
			 }else{
				 return SqlRequestHandler.handleUpdateRequest(request);
			 }
		} finally {
			if(request!=null && request.getConnection()!=null)
			 session.releaseConnection(request.getConnection());
   }
  }
}