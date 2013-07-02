/*
* Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.jda.impl.execution.worker;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jmin.jda.JdaTypeConverterMap;
import org.jmin.jda.JdaRowCache;
import org.jmin.jda.impl.JdaSessionImpl;
import org.jmin.jda.impl.exception.ResultMapException;
import org.jmin.jda.impl.exception.SqlExecutionException;
import org.jmin.jda.impl.execution.SqlRequest;
import org.jmin.jda.impl.execution.base.JdbRowCacheImpl;
import org.jmin.jda.impl.execution.select.ObjectRelateFinder;
import org.jmin.jda.impl.mapping.result.RelationUnitImpl;
import org.jmin.jda.impl.mapping.result.ResultMapImpl;
import org.jmin.jda.impl.mapping.result.ResultUnitImpl;
import org.jmin.jda.impl.property.PropertyException;
import org.jmin.jda.impl.property.PropertyUtil;
import org.jmin.jda.impl.statement.SqlBaseStatement;
import org.jmin.jda.impl.statement.SqlOperationType;
import org.jmin.jda.impl.statement.SqlStaticStatement;
import org.jmin.jda.impl.util.ClassUtil;
import org.jmin.jda.impl.util.StringUtil;
import org.jmin.jda.mapping.ParamUnit;
import org.jmin.jda.mapping.RelationUnit;
import org.jmin.jda.mapping.ResultMap;
import org.jmin.jda.mapping.ResultUnit;

/**
 * 结果辅助类
 * 
 * @author chris 
 */

public class ResultFactory {
	
	/**
	 * 设置属性值
	 */
	public static void setPropertyValue(String sqlId,Object bean,String propertyName,Object value)throws SQLException{
		try {
			if(propertyName!=null && propertyName.trim().length()>0){
				PropertyUtil.setPropertyValue(bean,propertyName,value);
			}else{
				throw new PropertyException("Property info can't be null");
			}
		} catch (Throwable e) {
			throw new SqlExecutionException(sqlId,e);
		}
	}
	
	/**
	 * 依据映射读出结果
	 */
	public static Object readResultObject(JdaSessionImpl session,Connection con,SqlBaseStatement statement,ResultSet resultSet,Object resultObject)throws SQLException{
		try {
			Class resultLazyClass = null;
			String sqlId=statement.getSqlId();
			Class resultClass= statement.getResultClass();
			List fieldNameList = getResultNameList(resultSet);
	
			JdaTypeConverterMap typeConverterMap = session.getTypeConverterMap();
			ResultUnit[]resultUnits=ResultFactory.getResultProperty(session,statement,resultSet);
			RelationUnit[]relationUnits=ResultFactory.getRelationUnits(session,statement,resultSet);

			ResultMapImpl resultMap =(ResultMapImpl)statement.getResultMap();
			resultLazyClass = resultMap.getLayLoadresultClass();
			
			if(session.supportsConversionType(resultClass)){//直接可映射结果类
				String columnName = resultUnits[0].getResultColumnName();
				if(StringUtil.isNull(columnName))
					columnName = resultSet.getMetaData().getColumnName(1);
				 return resultUnits[0].getJdbcTypePersister().get(resultSet,columnName,typeConverterMap,new JdbRowCacheImpl());
			}else{
				JdaRowCache RowCache = new JdbRowCacheImpl();
				if(resultObject==null )
					resultObject=ClassUtil.createInstance(resultLazyClass);
				
				//读出结果属性
				for(int i=0;i<resultUnits.length; i++) {
					ResultUnitImpl unit =(ResultUnitImpl)resultUnits[i];
          if(fieldNameList.contains(unit.getResultColumnName().toUpperCase())){
					 Object propetyValue= unit.getJdbcTypePersister().get(resultSet,unit.getResultColumnName(),typeConverterMap,RowCache);
					 ResultFactory.setPropertyValue(sqlId,resultObject,unit.getPropertyName(),propetyValue);
          }
				 }
			
				//读出关联属性
				for(int i=0;relationUnits!=null && i<relationUnits.length;i++) {
					RelationUnitImpl unit =(RelationUnitImpl)relationUnits[i];
					SqlBaseStatement subStatement=session.getSqlStatement(unit.getSqlId());	
					if(!(subStatement instanceof SqlStaticStatement))
						throw new SqlExecutionException(sqlId,"Relation unit["+i+":"+unit.getPropertyName()+"]sql("+unit.getSqlId()+")is not a static statement");
					if(!SqlOperationType.Select.equals(subStatement.getSqlType()))
						throw new ResultMapException(sqlId,"Relation unit["+i+":"+unit.getPropertyName()+"]sql("+unit.getSqlId()+")is not select statement");
					
					SqlStaticStatement relateStatement =(SqlStaticStatement)subStatement;
					String relationSqlId=relateStatement.getSqlId();
					Class relationParamType =relateStatement.getParamClass();
					ParamUnit[]paramUnits=relateStatement.getParamUnits();
					String[]relateColumns=relationUnits[i].getRelationColumnNames();
					
					int relateParamsLen =(paramUnits!=null)?paramUnits.length:0;
					int columnNamesLen =(relateColumns!=null)?relateColumns.length:0;
					if(relateParamsLen!=columnNamesLen)
						throw new SqlExecutionException(sqlId,"Relation unit["+i+":"+unit.getPropertyName()+"]column names length not match sql("+unit.getSqlId()+")parameter units");
				
					Object[] relateParamValues = readRelationValue(sqlId,RowCache,resultSet,relateColumns,session);
					SqlRequest request = new SqlRequest(session,relationSqlId,relationParamType,new Object());
					request.setDefinitionType(subStatement.getSqlType());
					request.setSqlText(relateStatement.getSql());
					
					
					//设置关联查询的属性
					request.setParamValues(relateParamValues);
					request.setParamNames(RelationFactory.getParamNames(paramUnits));
 					request.setParamValueTypes(RelationFactory.getParamTypes(paramUnits,request.getParamValues()));
					request.setParamSqlTypeCodes(RelationFactory.getParamSQLTypes(paramUnits));
					request.setParamValueModes(RelationFactory.getParamValueMode(paramUnits));
					request.setParamTypePersisters(RelationFactory.getParamTypePersisters(session,paramUnits,relateParamValues));
					request.setRelationUnit(unit);
					//设置关联查询的属性
					
					if(!unit.isLazyLoad()){//不需要延迟加载属性
						request.setConnection(con);
						Object relatePropValue = ObjectRelateFinder.getRelaionValue(request,false);
						ResultFactory.setPropertyValue(sqlId,resultObject,unit.getPropertyName(),relatePropValue);
					}else{//需要延迟加载属性
					 	String lazyPropertyName=unit.getPropertyName() +"$SqlRequest";
					 	String methodName = PropertyUtil.getPropertySetMethodName(lazyPropertyName);
						//System.out.println("resultLazyClass" + resultLazyClass);
					 	Method method = resultLazyClass.getDeclaredMethod(methodName, new Class[]{org.jmin.jda.impl.execution.SqlRequest.class});
					 	method.invoke(resultObject,new Object[]{request});
					}	
				}
 
				return resultObject;
			}
		}catch(Throwable e){
			throw new SqlExecutionException(statement.getSqlId(),e.getMessage(),e);
		}
	}
	
	
	/**
	* 读取结果集，并将注入结果对象中
	*/
	private static Object[] readRelationValue(String sqlId,JdaRowCache rowCache,ResultSet resultSet,String[] relateColumnNames,JdaSessionImpl session)throws SQLException{
		Object[] relateParamValues = new Object[relateColumnNames==null?0:relateColumnNames.length];
		for(int i=0;i<relateParamValues.length;i++){
			if(rowCache!=null && rowCache.isSetted(relateColumnNames[i].trim().toLowerCase()))
				relateParamValues[i] = rowCache.get(relateColumnNames[i].trim().toLowerCase());
			else
				relateParamValues[i] = resultSet.getObject(relateColumnNames[i].trim().toLowerCase());
		}
		
		return relateParamValues;
	}

	/**
	 * 读出结果映射属性
	 */
	public static ResultUnit[] getResultProperty(JdaSessionImpl session,SqlBaseStatement sqlStatement,ResultSet resultSet)throws SQLException{
		ResultMap resultMap = sqlStatement.getResultMap();
		ResultUnit[] resultUnits = resultMap.getResultUnits();
		if(resultUnits== null || resultUnits.length ==0){
			readResultUnitFromResult(session,sqlStatement,resultSet);
		}
		return resultMap.getResultUnits();
	}
	
	/**
	 *从结果集中，读出结果映射属性,需要保持同步
	 */
	private static synchronized void readResultUnitFromResult(JdaSessionImpl session,SqlBaseStatement sqlStatement,ResultSet resultSet)throws SQLException{
		ResultMap resultMap = sqlStatement.getResultMap();
		ResultUnit[] resultUnits = resultMap.getResultUnits();
		if(resultUnits== null || resultUnits.length ==0){
			resultUnits = createResultProperty(session,sqlStatement.getResultClass(),resultSet);
			((ResultMapImpl)resultMap).setResultUnits(resultUnits);
		}
	}
	
	/**
	 * 获得关联属性,保持同步
	 */
	private static RelationUnit[] getRelationUnits(JdaSessionImpl session,SqlBaseStatement sqlStatement,ResultSet resultSet)throws SQLException{
		if(sqlStatement instanceof SqlStaticStatement){
			SqlStaticStatement staticSQL = (SqlStaticStatement)sqlStatement;
			return staticSQL.getRelationUnits();
		}else{
			return null;
		}
	}
	
	
	/**
	 * 当前查询结果不存在一个结果映射时候，则调用该方法获取结果映射属性
	 */
	private static ResultUnit[] createResultProperty(JdaSessionImpl session,Class resultClass,ResultSet resultSet)throws SQLException{
		ResultSetMetaData meta = resultSet.getMetaData();
		int resultFieldCount = meta.getColumnCount();
		                       
		if(Map.class.isAssignableFrom(resultClass)){//如果结果类为Map,则结果集合中所有字段都接受
			ResultUnit[]properties = new ResultUnit[resultFieldCount];
			for(int i=0;i<resultFieldCount;i++){
				properties[i]= new ResultUnitImpl(meta.getColumnName(i+1).toLowerCase(),Object.class);
				properties[i].setResultColumnName(meta.getColumnName(i+1));
				((ResultUnitImpl)properties[i]).setJdbcTypePersister(session.getTypePersister(Object.class));
			}
			return properties;
		}else{
			if(session.supportsConversionType(resultClass)){//结果类为直接可映射类
				ResultUnit[]properties = new ResultUnit[1];
				properties[0]= new ResultUnitImpl(null,resultClass);
				properties[0].setResultColumnName(meta.getColumnName(1));
				((ResultUnitImpl)properties[0]).setJdbcTypePersister(session.getTypePersister(resultClass));
				return properties;
			}else{//结果类为不可直接可映射类
				List resultPropertyList = new ArrayList();
				Method[] methods = resultClass.getMethods();
				String methodName = null;
				Class[] paramTypes = null;
				for(int i=0;i<resultFieldCount;i++){
					for(int j=0;j<methods.length;j++){
					  methodName = methods[j].getName();  
					  paramTypes = methods[j].getParameterTypes();
					 
					 if(methodName.equalsIgnoreCase("set"+meta.getColumnName(i+1))&&paramTypes.length==1&&session.supportsConversionType(paramTypes[0])){
						  String propertyName = methodName.substring(3,4).toLowerCase() + methodName.substring(4);
							ResultUnit unit = new ResultUnitImpl(propertyName,paramTypes[0]);
							unit.setResultColumnName(meta.getColumnName(i+1));
							((ResultUnitImpl)unit).setJdbcTypePersister(session.getTypePersister(unit.getPropertyType()));
							resultPropertyList.add(unit);
				  }
				}
			}
			return (ResultUnit[])resultPropertyList.toArray(new ResultUnit[0]);
		}
	 }
  }
	
	/**
	 * 找出结果集合中所有字段名
	 */
	private static List getResultNameList(ResultSet re)throws SQLException{
		List fieldList = new ArrayList();
		ResultSetMetaData meta = re.getMetaData();
		int count = meta.getColumnCount();
		for(int i=1;i<=count;i++){
			fieldList.add(meta.getColumnName(i).toUpperCase());
		}
		return fieldList;
	}
}
