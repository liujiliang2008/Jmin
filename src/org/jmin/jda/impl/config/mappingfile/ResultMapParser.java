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
import org.jmin.jda.impl.exception.ResultMapException;
import org.jmin.jda.impl.util.ClassUtil;
import org.jmin.jda.impl.util.StringUtil;
import org.jmin.jda.mapping.RelationUnit;
import org.jmin.jda.mapping.ResultMap;
import org.jmin.jda.mapping.ResultUnit;

/**
 * 结果映射表解析
 * 
 * @author Chris Liao
 */

public class ResultMapParser {
	
	/**
	 * 解析结果映射列表节点
	 */
	public  ResultMap parse(String mapId,Element elment,JdaContainer container,Map classMap)throws SQLException {
		Class resultClass = null;
		String classname = elment.getAttributeValue("class");
		if(!StringUtil.isNull(classname)) 
			resultClass = this.getClassType(mapId,classname,classMap);
		
		List resultUnitList = new ArrayList();
		List relationUnitList = new ArrayList();
		
		List resultNodeList = elment.getChildren("result");
		if(resultNodeList==null || resultNodeList.isEmpty())
			 throw new ResultMapException("Result map["+mapId+"] missed 'result' children node");
		
		Iterator itor = resultNodeList.iterator();
		while (itor.hasNext()) {
			Element resultElement=(Element)itor.next();
			String selectID= resultElement.getAttributeValue("select"); 
			if(StringUtil.isNull(selectID)){
				resultUnitList.add(resolveResultUnitNode(mapId,resultElement,container,classMap));
			}else {
				relationUnitList.add(resolveRelationUnitNode(mapId,resultElement,container,classMap));
			}
		}
		
		ResultUnit[] resultUnits = (ResultUnit[])resultUnitList.toArray(new ResultUnit[0]);
		RelationUnit[] relationUnits = (RelationUnit[])relationUnitList.toArray(new RelationUnit[0]);
		return container.createResultMap(resultClass,resultUnits,relationUnits);
	}
	
	/**
	 * 解析单个参数节点
	 */
	private ResultUnit resolveResultUnitNode(String mapID,Element resultUnitNode,JdaContainer container,Map classMap)throws SQLException{
		Class propertyType = null;
		String propertyName = resultUnitNode.getAttributeValue("property");
		String javaType = resultUnitNode.getAttributeValue("javaType");
		String columnName= resultUnitNode.getAttributeValue("column");
		String resultConvertName=  resultUnitNode.getAttributeValue("resultConverter"); 
		
		if(StringUtil.isNull(propertyName))
			throw new ResultMapException(null,"ResultMap("+mapID+")Property name can't be null");
		if(!StringUtil.isNull(javaType))
			propertyType = getClassType(mapID,javaType,classMap);
		
		ResultUnit resultUnit=container.createResultUnit(propertyName,propertyType);
		
	  if(!StringUtil.isNull(columnName))
	  	resultUnit.setResultColumnName(columnName);
	  if(!StringUtil.isNull(resultConvertName))
	  	resultUnit.setJdbcTypePersister(loadResultConverter(mapID,resultConvertName,classMap));
	 
		return resultUnit;
	}
	
	/**
	 * 解析单个参数节点
	 */
	private RelationUnit resolveRelationUnitNode(String mapID,Element relationUnitNode,JdaContainer container,Map classMap)throws SQLException{
		Class propertyType = null;
		String propertyName = relationUnitNode.getAttributeValue("property");
		String javaType = relationUnitNode.getAttributeValue("javaType");
		String columnName= relationUnitNode.getAttributeValue("column");
		String selectID= relationUnitNode.getAttributeValue("select"); 
		String keyPropertyName= relationUnitNode.getAttributeValue("keyProperty"); 
		String valuePropertyName= relationUnitNode.getAttributeValue("valueProperty"); 
		String lazyValue= relationUnitNode.getAttributeValue("lazy"); 
		
		if(StringUtil.isNull(propertyName))
			throw new ResultMapException(null,"ResultMap("+mapID+")Property name can't be null");
		if(!StringUtil.isNull(javaType))
			propertyType = getClassType(mapID,javaType,classMap);
		
		RelationUnit relationUnit=container.createRelationUnit(propertyName,propertyType,selectID);
		
		if(!StringUtil.isNull(columnName)) {
			relationUnit.setRelationColumnNames(StringUtil.split(columnName,","));
		} else {
			throw new ResultMapException(null,"ResultMap("+mapID+")Missed relation fields");
		}

		if(!StringUtil.isNull(keyPropertyName)) 
			relationUnit.setMapKeyPropertyName(keyPropertyName);
		
		if (!StringUtil.isNull(valuePropertyName)) 
			relationUnit.setMapValuePropertyName(valuePropertyName);
		
		if (!StringUtil.isNull(lazyValue)) 
			relationUnit.setLazyLoad(Boolean.getBoolean(lazyValue));
	
		return  relationUnit;
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
			throw new ResultMapException(null,"ResultMap("+mapID+")not found parameter class:" + className,e);
		}
	}
	
	/**
	 * 装载自定义的Type callbackHandler
	 */
	private JdaTypePersister loadResultConverter(String mapID, String className,Map classMap)throws SQLException{
		try {
			Class clazz = null; 
			if(classMap.containsKey(className))
				clazz= (Class)(classMap.get(className));
			else
				clazz= ClassUtil.loadClass(className);
			return (JdaTypePersister)clazz.newInstance();
		} catch (Exception e) {
			throw new ParamMapException(null,"ResultMap("+mapID+")failed load converter class:" + className,e);
		}
	}
}