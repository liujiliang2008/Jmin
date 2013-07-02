/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.mapping.param;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;

import org.jmin.jda.JdaContainer;
import org.jmin.jda.impl.exception.ParamMapException;
import org.jmin.jda.impl.property.PropertyException;
import org.jmin.jda.impl.property.PropertyUtil;
import org.jmin.jda.impl.statement.SqlOperationType;
import org.jmin.jda.impl.util.StringUtil;
import org.jmin.jda.mapping.ParamUnit;
import org.jmin.jda.mapping.ParamValueMode;

/**
 * 参数映射验证
 * 
 * @author Chris
 */
public class ParamValidator {
	
	/**
	 * 检查映射参数属性
	 */
	public static void checkParamMap(String id,SqlOperationType sqlType,int paramCount,ParamMapImpl paramMap,JdaContainer container)throws SQLException{
		if(paramMap!=null && !paramMap.isInited()){
			Class paramClass= paramMap.getParamClass();
			ParamUnit[]paramUnits=paramMap.getParamUnits();
			
			if(paramClass==null)
				throw new ParamMapException(id,"Parameter class can't be null");
			if(Collection.class.isAssignableFrom(paramClass))
		  	throw new ParamMapException(id,"Parameter class must be map type or bean type");
			if((paramUnits==null || paramUnits.length==0)&&!container.containsTypePersister(paramClass))
				throw new ParamMapException(id,"Parameter units missed");			
		 
			if(container.containsTypePersister(paramClass)){//参数类为可直接映射
				if(paramCount !=1)
					throw new ParamMapException(id,"Sql parameter count can't match parameter map definition");			
				
				if(paramUnits==null || paramUnits.length==0){
					paramUnits= new ParamUnit[1];
					paramUnits[0]= new ParamUnitImpl(null,paramClass);
					((ParamUnitImpl)paramUnits[0]).setJdbcTypePersister(container.getTypePersister(paramClass));
				}else if(paramUnits.length>=1){
					ParamUnitImpl unit=(ParamUnitImpl)paramUnits[0];
					unit.setPropertyType(paramClass);
					if(!StringUtil.isNull(unit.getParamColumnTypeName()))
						unit.setParamColumnTypeCode(container.getJdbcTypeCode(unit.getParamColumnTypeName()));
					if(unit.getJdbcTypePersister()==null)
						unit.setJdbcTypePersister(container.getTypePersister(unit.getPropertyType(),unit.getParamColumnTypeName()));
				}
			}else{//不可直接映射
				if(paramUnits.length !=paramCount)
					throw new ParamMapException(id,"Sql parameter count can't match parameter map definition: "+ (paramUnits.length) + ":" + paramCount);			
				
				for(int i=0;i<paramUnits.length;i++){
					ParamUnitImpl unit=(ParamUnitImpl)paramUnits[i];
					ParamValueMode valueMode=unit.geParamValueMode();
					
					if(StringUtil.isNull(unit.getPropertyName()))
						throw new ParamMapException(id,"Parameter unit["+i+"]property name can't be null");	
					if(unit.getMapOwner()!=null && unit.getMapOwner()!=paramMap)
						throw new ParamMapException(id,"Parameter unit["+i+"]("+unit.getPropertyName()+")has been used in another map");
					if(!SqlOperationType.Procedure.equals(sqlType) && !ParamValueMode.IN.equals(valueMode))
						throw new ParamMapException(id,"Parameter unit["+i+"]("+unit.getPropertyName()+")can't be out type parameter in sql type:"+sqlType);
					
					if(unit.getPropertyType()==null ){
						if(Map.class.isAssignableFrom(paramClass)){
							 unit.setPropertyType(Object.class);
						}else{
							try {
								 unit.setPropertyType(PropertyUtil.getPropertyType(paramClass,unit.getPropertyName()));
						  } catch (PropertyException e) {
							  throw new ParamMapException(id,"Parameter unit["+i+"]("+unit.getPropertyName()+")property exception",e);
						  }
						}
					}
								
					if(unit.getPropertyType()==null)
						throw new ParamMapException(id,"Parameter unit["+i+"]("+unit.getPropertyName()+")property type can't be null");
					if(!StringUtil.isNull(unit.getParamColumnTypeName()))
						unit.setParamColumnTypeCode(container.getJdbcTypeCode(unit.getParamColumnTypeName()));
					if(unit.getJdbcTypePersister()==null)
						unit.setJdbcTypePersister(container.getTypePersister(unit.getPropertyType(),unit.getParamColumnTypeName()));
					if(unit.getJdbcTypePersister()==null)
						throw new ParamMapException(id,"Parameter unit["+i+"]persister can't be null");	
				 }
		  }
		
			for(int i=0;paramUnits!=null && i<paramUnits.length;i++){
				((ParamUnitImpl)paramUnits[i]).setInited(true);
				((ParamUnitImpl)paramUnits[i]).setMapOwner(paramMap);
			}
			
			paramMap.setInited(true);
		}
	}
 }