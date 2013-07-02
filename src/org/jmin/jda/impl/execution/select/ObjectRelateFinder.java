/*
* Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.jda.impl.execution.select;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Vector;

import org.jmin.jda.impl.JdaSessionImpl;
import org.jmin.jda.impl.exception.SqlExecutionException;
import org.jmin.jda.impl.execution.SqlRequest;
import org.jmin.jda.impl.execution.SqlRequestHandler;
import org.jmin.jda.impl.execution.worker.ParamFactory;
import org.jmin.jda.impl.execution.worker.ResultFactory;
import org.jmin.jda.impl.mapping.result.RelationUnitImpl;
import org.jmin.jda.impl.property.PropertyException;
import org.jmin.jda.impl.property.PropertyUtil;
import org.jmin.jda.impl.statement.SqlStaticStatement;
import org.jmin.jda.impl.util.ClassUtil;
import org.jmin.jda.impl.util.CloseUtil;
import org.jmin.jda.impl.util.StringUtil;
import org.jmin.jda.mapping.RelationUnit;

/**
 * 关联查询
 * 
 * @author Chris Liao
 */

public class ObjectRelateFinder {

	/**
	 * 为延迟加载准备
	 */
	public static Object getRelaionValue(SqlRequest request,boolean needReleaseCon)throws SQLException{
		ResultSet resultSet = null;
		Object resultValue = null;
		RelationUnitImpl relateUnit =null;
		
		try{
			relateUnit = request.getRelationUnit();
			Class relationType= relateUnit.getPropertyType();
			JdaSessionImpl session = request.getRequestSession();
			resultSet = SqlRequestHandler.handleQueryRequest(request);
			Connection connection = request.getConnection();
			
			SqlStaticStatement statement =(SqlStaticStatement)session.getSqlStatement(request.getSqlId());
			
			if(ListIterator.class.isAssignableFrom(relationType)){
				List list = (List)readCollection(session,connection,resultSet,statement,List.class,relateUnit);
				return list.listIterator();
			}else if(Iterator.class.isAssignableFrom(relationType)){
				List list = (List)readCollection(session,connection,resultSet,statement,List.class,relateUnit);
				return list.iterator();
			}else if(Enumeration.class.isAssignableFrom(relationType)){	
				Vector vector = (Vector)readCollection(session,connection,resultSet,statement,List.class,relateUnit);
				return vector.elements();
			}else if(Collection.class.isAssignableFrom(relationType)){
				resultValue = readCollection(session,connection,resultSet,statement,relationType,relateUnit);
			}else if(Map.class.isAssignableFrom(relationType)){
				resultValue = readMap(session,connection,resultSet,statement,relateUnit);
			}else if(Object[].class.isAssignableFrom(relationType)){
				Collection col = readCollection(session,connection,resultSet,statement,relationType,relateUnit);
				return col.toArray();
			}else{
				resultValue = readObject(session,connection,resultSet,statement,relateUnit);
			}
			return resultValue;
		}finally {
			if(resultSet!= null){
				CloseUtil.close(resultSet);
				resultSet=null;
			}
			
			if(needReleaseCon && request!=null && request.getConnection()!= null){
				request.getRequestSession().releaseConnection(request.getConnection());
				request.setConnection(null);
			}
		}
	}
	
	/**
	 * 读出一个对象
	 */
	private static Object readObject(JdaSessionImpl session,Connection con,ResultSet resultSet,SqlStaticStatement statement,RelationUnit relationUnit)throws SQLException{
		try {
			Object resultObject=null;
			String sqlId = statement.getSqlId();
	 
			if(resultSet.next())
				resultObject = ResultFactory.readResultObject(session,con,statement,resultSet,null);
			else
				throw new SqlExecutionException(sqlId,"No any records existed");
			
			if(resultSet.next())
				throw new SqlExecutionException(sqlId,"Multiple row records existed");
	
			return resultObject;
		}catch(Throwable e){
			throw new SqlExecutionException(statement.getSql(),"Failed to execute sinle relation for propery name:"+relationUnit.getSqlId(),e);
		}
	}
	
	/**
	 * 读出一个普通的collection
	 */
	private static Collection readCollection(JdaSessionImpl session,Connection con,ResultSet resultSet,SqlStaticStatement statement,Class collectionClass,RelationUnit relationUnit)throws SQLException{
		try {
			Collection collection =(Collection)ClassUtil.createInstance(collectionClass);
			while(resultSet.next()) 
				collection.add(ResultFactory.readResultObject(session,con,statement,resultSet,null));
			return collection;
		}catch(Throwable e){
			throw new SqlExecutionException(statement.getSql(),"Failed to execute list relation for propery name:"+relationUnit.getSqlId(),e);
		}
	}
	
	/**
	 * 读出一个普通的Map
	 */
	private static Map readMap(JdaSessionImpl session,Connection con,ResultSet resultSet,SqlStaticStatement statement,RelationUnit relationUnit)throws SQLException{
		try {
			String sqlId = statement.getSqlId();
			Class beanClass = statement.getResultClass();
			Class relaitonType =relationUnit.getPropertyType();
		
			String keyPropertyname = relationUnit.getMapKeyPropertyName();
			String ValuePropertyName = relationUnit.getMapValuePropertyName();
			
			Map map =(Map)ClassUtil.createInstance(relaitonType);
			
			if(!StringUtil.isNull(keyPropertyname) && !Map.class.isAssignableFrom(beanClass))
				try {
					PropertyUtil.getPropertyType(beanClass,keyPropertyname);
				} catch (PropertyException e) {
					throw new SqlExecutionException(sqlId,"Failed find map key property["+keyPropertyname+"]",e);
				}
				
			if(!StringUtil.isNull(ValuePropertyName) && !Map.class.isAssignableFrom(beanClass))
				try {
					PropertyUtil.getPropertyType(beanClass,ValuePropertyName);
				} catch (PropertyException e) {
					throw new SqlExecutionException(sqlId,"Failed find map value property["+ValuePropertyName+"]",e);
				}
			
			while(resultSet.next()) {
				Object resultObj = ResultFactory.readResultObject(session,con,statement,resultSet,null);
				Object keyValue = ParamFactory.getPropertyValue(sqlId,resultObj,keyPropertyname);
				if(!StringUtil.isNull(ValuePropertyName))
				  resultObj = ParamFactory.getPropertyValue(sqlId,resultObj,ValuePropertyName); 
				map.put(keyValue,resultObj);
			}
			return map;
		}catch(Throwable e){
			throw new SqlExecutionException(statement.getSql(),"Failed to execute map relation for propery name:"+relationUnit.getSqlId(),e);
		}
	}
}
