 /*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.config.mappingfile;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jdom.Element;
import org.jmin.jda.JdaContainer;
import org.jmin.jda.JdaTypePersister;
import org.jmin.jda.impl.exception.ParamMapException;
import org.jmin.jda.impl.util.ClassUtil;
import org.jmin.jda.impl.util.StringUtil;
import org.jmin.jda.mapping.ParamMap;
import org.jmin.jda.mapping.ParamUnit;
import org.jmin.jda.mapping.ParamValueMode;

/**
 * 参数映射表解析
 * 
 * @author Chris Liao
 */

public class ParamMapParser {
	
	/**
	 * 解析参数映射列表节点
	 */
	public ParamMap parse(String mapID,Element elment,JdaContainer container,Map classMap)throws SQLException {
		Class paramClass = null;
		String classname = elment.getAttributeValue("class");
		if(!StringUtil.isNull(classname)) 
			paramClass = this.getClassType(mapID,classname,classMap);
		
		List paramUnitList = new ArrayList();//参数映射单元
		List paramUnitNodeList = elment.getChildren("parameter");
		if(paramUnitNodeList==null || paramUnitNodeList.isEmpty())
		 throw new ParamMapException("Parameter map["+mapID+"]missed 'parameter' children node");
		
		Iterator itor = paramUnitNodeList.iterator();
		while (itor.hasNext()) {
			Element paramElement =(Element)itor.next();
			paramUnitList.add(resolveParamUnitNode(mapID,paramElement,container,classMap));
		}
		
		ParamUnit[] units = (ParamUnit[])paramUnitList.toArray(new ParamUnit[0]);
		return container.createParamMap(paramClass,units);
	}

	/**
	 * 解析单个参数节点
	 */
	private ParamUnit resolveParamUnitNode(String mapID,Element paramUnitNode,JdaContainer container,Map classMap)throws SQLException{
		Class propertyType = null;
		String propertyName =paramUnitNode.getAttributeValue("property");
		String javaType=paramUnitNode.getAttributeValue("javaType");
		String jdbcType = paramUnitNode.getAttributeValue("jdbcType");
		String paramMode= paramUnitNode.getAttributeValue("mode"); 
		String paramPersisterName= paramUnitNode.getAttributeValue("paramPersister"); 
		//String resultConvertName=  paramUnitNode.getAttributeValue("resultConverter"); 
		
		if(StringUtil.isNull(propertyName))
			throw new ParamMapException(null,"ParamMap("+mapID+")Property name can't be null");
		if(!StringUtil.isNull(javaType))
			propertyType = getClassType(mapID,javaType,classMap);
		
		ParamUnit paramUnit=container.createParamUnit(propertyName,propertyType);
		
		if(!StringUtil.isNull(jdbcType)&& !container.containsJdbcType(jdbcType)) 
			throw new ParamMapException(null,"ParamMap("+mapID+")Invalidate jdbc type:"+jdbcType);
		else
			paramUnit.setParamColumnTypeName(jdbcType);
	
		if(!StringUtil.isNull(paramMode)){
	  	ParamValueMode mode = ParamValueMode.getParamMode(paramMode);
	  	if(mode == null)
	  		throw new ParamMapException(null,"ParamMap("+mapID+")Invalidate parameter mode: "+ mode);
	  	paramUnit.setParamValueMode(mode);
	  }

	  if(!StringUtil.isNull(paramPersisterName))
	  	paramUnit.setJdbcTypePersister(loadParamPersister(mapID,paramPersisterName,classMap));
	  
		return paramUnit;
	}
	
	/**
	 * 获得类
	 */
	private Class getClassType(String mapID,String className,Map classMap)throws SQLException{
		try {
			if(classMap.containsKey(className))
				return (Class)(classMap.get(className));
			else
			return ClassUtil.loadClass(className);
		} catch (ClassNotFoundException e) {
			throw new ParamMapException(null,"ParamMap("+mapID+")not found class:" + className,e);
		}
	}
	
	/**
	 * 装载自定义的Type callbackHandler
	 */
	private JdaTypePersister loadParamPersister(String mapID,String className,Map classMap)throws SQLException{
		try {
			Class clazz = null; 
			if(classMap.containsKey(className))
				clazz= (Class)(classMap.get(className));
			else
				clazz= ClassUtil.loadClass(className);
			
			return (JdaTypePersister)clazz.newInstance();
		} catch (Exception e) {
			throw new ParamMapException(null,"ParamMap("+mapID+")failed load persister class:" + className,e);
		}
	}

//	/**
//	 * 装载自定义的Type callbackHandler
//	 */
//	private ResultConverter loadResultConverter(String mapID,String className,Map classMap)throws SQLException{
//		try {
//			Class clazz = null; 
//			if(classMap.containsKey(className))
//				clazz= (Class)(classMap.get(className));
//			else
//				clazz= ClassUtil.loadClass(className);
//
//			return (ResultConverter)clazz.newInstance();
//		} catch (Exception e) {
//			throw new ParamMapException(null,"ParamMap("+mapID+")failed load converter class:" + className,e);
//		}
//	}
}