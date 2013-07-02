/*
* Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.jda.impl.execution.select;

import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.jmin.jda.impl.JdaSessionImpl;
import org.jmin.jda.impl.statement.SqlBaseStatement;

/**
 * 叠达器查询
 * 
 * @author Chris liao
 */

public class ObjectIterateFinder {
	
	/**
	 * 多结果的查询，返回一个Iterator
	 */
	public static Iterator findIterator(JdaSessionImpl session,SqlBaseStatement statement,Object paramObject)throws SQLException {
		return ((List)ObjectListFinder.find(session,statement,paramObject,0,0,List.class)).iterator();
	}
	
	/**
	 * 多结果的查询，返回一个Enumeration
	 */
	public static Enumeration findEnumeration(JdaSessionImpl session,SqlBaseStatement statement,Object paramObject)throws SQLException {
		return ((Vector)ObjectListFinder.find(session,statement,paramObject,0,0,Vector.class)).elements();
	}
}
