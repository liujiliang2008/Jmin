/*
* Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.jda.impl.execution.worker;

import java.sql.SQLException;

import org.jmin.jda.JdaTypePersister;
import org.jmin.jda.impl.JdaSessionImpl;
import org.jmin.jda.mapping.ParamUnit;
import org.jmin.jda.mapping.ParamValueMode;

/**
 * 关联辅助类
 * 
 * @author chris 
 */

public class RelationFactory {
	
	/**
	 * 获得参数名
	 */
	public static String[] getParamNames(ParamUnit[]paramUnits)throws SQLException{
		String[] paramNames = new String[paramUnits==null?0:paramUnits.length];
		for(int i=0;i<paramNames.length;i++){
			paramNames[i]=paramUnits[i].getPropertyName();
		}
		return paramNames;
	}
	
	/**
	 * 获得参数值
	 */
	public static Object[] getParamValues(JdaSessionImpl session,String sqlID,Class paramClass,Object paramObj,ParamUnit[]paramUnits)throws SQLException{
		if(session.supportsPersisterType(paramClass)){//直接可映射类型
			return new Object[]{paramObj};
		}else{
			Object[] paramValues = new Object[paramUnits==null?0:paramUnits.length];
			for(int i=0;i<paramValues.length;i++){
				paramValues[i]= ParamFactory.getPropertyValue(sqlID,paramObj,paramUnits[i].getPropertyName());
			}
			return paramValues;
	  }
	}
	
	/**
	 * 获得参数名
	 */
	public static Class[] getParamTypes(ParamUnit[]paramUnits,Object[]paramValues)throws SQLException{
		Class[] paramTypes = new Class[paramUnits==null?0:paramUnits.length];
		for(int i=0;i<paramTypes.length;i++){
			if(paramUnits[i].getPropertyType()!=null)
			 paramTypes[i]= paramUnits[i].getPropertyType();
			else
				paramTypes[i]= (paramValues[i]==null)?Object.class:paramValues[i].getClass();
		}
		return paramTypes;
	}
	
	/**
	 * 获得参数名
	 */
	public static int[] getParamSQLTypes(ParamUnit[]paramUnits)throws SQLException{
		int[] sqlTypes = new int[paramUnits==null?0:paramUnits.length];
		for(int i=0;i<sqlTypes.length;i++){
			sqlTypes[i]=paramUnits[i].getParamColumnTypeCode();
		}
		return sqlTypes;
	}
	
	/**
	 * 获得参数值模式
	 */
  public static ParamValueMode[] getParamValueMode(ParamUnit[]paramUnits)throws SQLException{
  	ParamValueMode[] modes = new ParamValueMode[paramUnits==null?0:paramUnits.length];
  	for(int i=0;i<modes.length;i++){
  		modes[i]=paramUnits[i].geParamValueMode();
		}
  	return modes;
  }
	
	
  /**
	 * 获得参数持久器
	 */
  public static JdaTypePersister[] getParamTypePersisters(JdaSessionImpl session,ParamUnit[]paramUnits,Object[] paramValues)throws SQLException{
  	JdaTypePersister[] persisters = new JdaTypePersister[paramUnits==null?0:paramUnits.length];
  	for(int i=0;i<persisters.length;i++){
  		if(paramUnits[i].getJdbcTypePersister()!=null){
  			persisters[i]=paramUnits[i].getJdbcTypePersister();
  		}else{
  			Class paramType = paramUnits[i].getPropertyType(); 
  			if(paramType==null && paramValues[i]!=null){
  				paramType = paramValues[i].getClass();
  			}
  			persisters[i]= session.getTypePersister(paramType,paramUnits[i].getParamColumnTypeName());
  		}
		}
  	return persisters;
  }
}
