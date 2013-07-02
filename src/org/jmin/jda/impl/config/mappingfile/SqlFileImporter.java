/*
* Copyright(c) jmin Organization. All rights reserved.
*/

package org.jmin.jda.impl.config.mappingfile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jmin.jda.JdaContainer;
import org.jmin.jda.impl.config.SqlFileFinder;
import org.jmin.jda.impl.config.dynamictext.DynamicSqlAnalyzer;
import org.jmin.jda.impl.config.statictext.ParamResult;
import org.jmin.jda.impl.config.statictext.SqlTextUtil;
import org.jmin.jda.impl.config.statictext.StaticSqlAnalyzer;
import org.jmin.jda.impl.exception.ParamMapException;
import org.jmin.jda.impl.exception.ResultMapException;
import org.jmin.jda.impl.exception.SqlDefinitionException;
import org.jmin.jda.impl.exception.SqlDefinitionFileException;
import org.jmin.jda.impl.util.ClassUtil;
import org.jmin.jda.impl.util.CloseUtil;
import org.jmin.jda.impl.util.StringUtil;
import org.jmin.jda.mapping.ParamMap;
import org.jmin.jda.mapping.ResultMap;
import org.jmin.jda.statement.DynTag;

/**
 * 解析映射文件
 * 
 * @author Chris Liao
 */
public class SqlFileImporter {
	
	/**
	 * 参数解析
	 */
	private AliasClassParser aliasClassParser = new AliasClassParser();
	
	/**
	 * 参数解析
	 */
	private ParamMapParser paramMapInfoParser = new ParamMapParser();
	
	/**
	 * 结果解析
	 */
	private ResultMapParser resultMapInfoParser = new ResultMapParser();
	
	/**
	 * 解析映射文件
	 */
	public void importSQLMapFile(URL url,JdaContainer container)throws SQLException {
		String sqlFilename=null;
		InputStream XMLStream = null;
		
		try {
			SqlFileFinder.validateXMLFile(url);
			sqlFilename = url.getFile();
			XMLStream = url.openStream();
			Document document = new SAXBuilder().build(XMLStream);
			Element rootElement = document.getRootElement();
			SqlFileFinder.validateXMLRoot(rootElement,SqlFileXMLNodes.Root);
			
			String spanceID = rootElement.getAttributeValue("space");
			
			List classNodeList = rootElement.getChildren(SqlFileXMLNodes.Class);
			List paramMapNodeList = rootElement.getChildren(SqlFileXMLNodes.ParameterMap);
			List resultMapNodeList = rootElement.getChildren(SqlFileXMLNodes.ResultMap);
			List insertNodeList = rootElement.getChildren(SqlFileXMLNodes.Insert);
			List updateNodeList = rootElement.getChildren(SqlFileXMLNodes.update);
			List deleteNodeList = rootElement.getChildren(SqlFileXMLNodes.Delete);
			List selectNodeList = rootElement.getChildren(SqlFileXMLNodes.Select);
			List procedureNodeList = rootElement.getChildren(SqlFileXMLNodes.Procedure);
			
			Map aliasClassMap = aliasClassParser.loadAliasMap(classNodeList,container);
			Map paramMapTable = this.resolveParamMapNodeList(paramMapNodeList,container,aliasClassMap);
			Map resultMapTable = this.resolveResultMapNodeList(resultMapNodeList,container,aliasClassMap);
	
			this.importSQLNodeList(spanceID,paramMapTable,resultMapTable,insertNodeList,container,aliasClassMap);
			this.importSQLNodeList(spanceID,paramMapTable,resultMapTable,updateNodeList,container,aliasClassMap);
			this.importSQLNodeList(spanceID,paramMapTable,resultMapTable,deleteNodeList,container,aliasClassMap);
			this.importSQLNodeList(spanceID,paramMapTable,resultMapTable,selectNodeList,container,aliasClassMap);
			this.importSQLNodeList(spanceID,paramMapTable,resultMapTable,procedureNodeList,container,aliasClassMap);
		
		}catch(SQLException e){
			throw e;
		} catch (JDOMException e) {
			throw new SqlDefinitionFileException(null,"Failed to parse sql map file:"+sqlFilename,e);
		} catch (IOException e) {
			throw new SqlDefinitionFileException(null,"Failed to open sql map file:"+sqlFilename,e);
		}finally{
			CloseUtil.close(XMLStream);
		}
	}
		
	/**
	 * 参数映射列表
	 */
	private Map resolveParamMapNodeList(List paramMapNodeList,JdaContainer container,Map classMap)throws SQLException{
		Map listMap = new HashMap();
		Iterator itor = paramMapNodeList.iterator();
		while(itor.hasNext()){
			Element mapNode =(Element)itor.next();
			String mapId = mapNode.getAttributeValue("id");
			if(StringUtil.isNull(mapId)) 
				throw new ParamMapException("Parameter map id can't be null");
			ParamMap paramMap = paramMapInfoParser.parse(mapId,mapNode,container,classMap);
			
			listMap.put(mapId,paramMap);
		}
		return listMap;
	}
	
	/**
	 * 获得映射的列表
	 */
	private Map resolveResultMapNodeList(List resultMapNodeList,JdaContainer container,Map classMap)throws SQLException{
		Map listMap = new HashMap();
		Iterator itor = resultMapNodeList.iterator();
		while(itor.hasNext()){
			Element mapNode =(Element)itor.next();
			String mapID = mapNode.getAttributeValue("id");
			if(StringUtil.isNull(mapID)) 
				throw new ResultMapException("Parameter map id can't be null");
			
			ResultMap info = resultMapInfoParser.parse(mapID,mapNode,container,classMap);
			listMap.put(mapID,info);
		}
		return listMap;
	}

	/**
	 * 解析SQL定义
	 */
	private void importSQLNodeList(String spaceID,Map paramListMap,Map resultListMap,List sqlNodeList,JdaContainer container,Map classMap)throws SQLException{
		Iterator itor = sqlNodeList.iterator();
		while(itor.hasNext()){//循环找出所有的SQL接点
			Element sqlElement =(Element)itor.next();
			
			String sqlID = sqlElement.getAttributeValue("id");
			if(StringUtil.isNull(sqlID))
				throw new SqlDefinitionException(null,"Missed sql id");
			String sqlText = sqlElement.getTextTrim();
			
			if(StringUtil.isNull(sqlText))
				throw new SqlDefinitionException(sqlID,"Missed sql text");
			SqlTextUtil.removeCdata(sqlID,sqlText);
			if(StringUtil.isNull(sqlText))
				throw new SqlDefinitionException(sqlID,"Missed sql text");
			
			if(!StringUtil.isNull(spaceID)) 
				sqlID = spaceID+"."+sqlID;
			
			if(SqlTextUtil.isDynamicText(sqlText)){//动态SQL 
				importDynamicSQL(sqlID,sqlText,sqlElement,container,paramListMap,resultListMap,classMap);
			}else{//静态SQL
				importStaticSQL(sqlID,sqlText,sqlElement,container,paramListMap,resultListMap,classMap);
			}
		}
	}

	
	/**
	 * 导入静态SQL
	 */
	private void importStaticSQL(String sqlID,String sqlText,Element sqlElement,JdaContainer container,Map paramListMap,Map resultListMap,Map classMap)throws SQLException{
		ParamMap paramMap =null;
		ResultMap resultMap=null;
		Class paramClass = null;
		Class resultClass = null;
		
		String strParamClass = sqlElement.getAttributeValue("parameterType");
		if(StringUtil.isNull(strParamClass))
		  strParamClass = sqlElement.getAttributeValue("parameterClass");
		String strResultClass =sqlElement.getAttributeValue("resultType");
		if(StringUtil.isNull(strResultClass))
			strResultClass = sqlElement.getAttributeValue("resultClass");
		
		String strParamMapID = sqlElement.getAttributeValue("parameterMap");
		String strResultMapID = sqlElement.getAttributeValue("resultMap");

		if(!StringUtil.isNull(strParamClass) && !StringUtil.isNull(strParamMapID))
			throw new SqlDefinitionException(sqlID,"Can't config 'parameterClass' and 'parameterMap' meantime");
		if(!StringUtil.isNull(strResultClass) && !StringUtil.isNull(strResultMapID))
			throw new SqlDefinitionException(sqlID,"Can't config 'resultClass' and 'resultMap' meantime");
		
		if(!StringUtil.isNull(strParamClass)){
			paramClass =loadClass(sqlID,strParamClass,true,classMap);
			if(sqlText.indexOf("?")>0)
				throw new SqlDefinitionException(sqlID,"Can't contains '?' in sql text for 'parameter class' mapping");
		}
		if(!StringUtil.isNull(strResultClass))
			resultClass =loadClass(sqlID,strResultClass,false,classMap);
		if(!StringUtil.isNull(strParamMapID)){
			paramMap=(ParamMap)paramListMap.get(strParamMapID);
			if(paramMap==null)
			 throw new SqlDefinitionException(sqlID,"Not found param map with id["+strParamMapID+"]");
			if(sqlText.indexOf("?")==-1)
				throw new SqlDefinitionException(sqlID,"must contains '?' in sql text for 'parameter mapping");
		}
		
		if(!StringUtil.isNull(strResultMapID)){
			resultMap=(ResultMap)resultListMap.get(strResultMapID);
			if(resultMap==null)
			 throw new SqlDefinitionException(sqlID,"Not found result map with id["+strResultMapID+"]");
		}
		
		if(resultClass!=null)
			resultMap = container.createResultMap(resultClass,null);
		else
			resultMap=(ResultMap)resultListMap.get(strResultMapID);
		
	
		if(paramMap==null){
			ParamResult result = StaticSqlAnalyzer.analyzeStaticSQL(sqlID,sqlText,paramClass,container);
			sqlText = result.getExeSQL();
			paramMap = result.getParamMap();
		}

		container.registerStaticSql(sqlID,sqlText,paramMap,resultMap);		
	}
	
	/**
	 * 导入静态SQL
	 */
	private void importDynamicSQL(String sqlID,String sqlText,Element sqlElement,JdaContainer container,Map paramListMap,Map resultListMap,Map classMap)throws SQLException{
		ResultMap resultMap=null;
		Class paramClass = null;
		Class resultClass = null;
 
		String strParamClass = sqlElement.getAttributeValue("parameterClass");
		String strParamMapID = sqlElement.getAttributeValue("parameterMap");
	  String strResultClass = sqlElement.getAttributeValue("resultClass");
		String strResultMapID = sqlElement.getAttributeValue("resultMap");

		if(StringUtil.isNull(strParamClass))
			throw new SqlDefinitionException(sqlID,"Parameter class can't not be configed");
		if(StringUtil.isNull(strParamClass) && !StringUtil.isNull(strParamMapID))
			throw new SqlDefinitionException(sqlID,"Parameter map don't be supported by dynamic sql");
		if(!StringUtil.isNull(strResultClass) && !StringUtil.isNull(strResultMapID))
			throw new SqlDefinitionException(sqlID,"Can't config 'resultClass' and 'resultMap' meantime");
		
		if(!StringUtil.isNull(strParamClass))
			paramClass =loadClass(sqlID,strParamClass,true,classMap);
		if(!StringUtil.isNull(strResultClass))
			resultClass =loadClass(sqlID,strResultClass,false,classMap);
		if(!StringUtil.isNull(strResultMapID))
			resultMap=(ResultMap)resultListMap.get(strResultMapID);
		if(resultClass!=null)
			resultMap = container.createResultMap(resultClass,null);
		else
			resultMap=(ResultMap)resultListMap.get(strResultMapID);
		
		
		DynTag[]tags = DynamicSqlAnalyzer.analyzeDynamicSQL(sqlID,sqlText,paramClass,container);
		container.registerDynamicSql(sqlID,tags,paramClass,resultMap);	
	}
	
	/**
	 * 获得类
	 */
	private Class loadClass(String sqlId,String className,boolean isParameter,Map classMap)throws SQLException{
		try {
			if(classMap.containsKey(className))
				return (Class)(classMap.get(className));
			else
			return ClassUtil.loadClass(className);
		} catch (ClassNotFoundException e) {
			if(isParameter)
				throw new ParamMapException(sqlId,"Parameter Class("+className+")not found",e);
			else
				throw new ResultMapException(sqlId,"Result Class("+className+")not found",e);
		}
	}
}
