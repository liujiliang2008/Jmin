/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.config.statictext;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jmin.jda.JdaContainer;
import org.jmin.jda.JdaTypePersister;
import org.jmin.jda.impl.exception.ParamMapException;
import org.jmin.jda.impl.util.ClassUtil;
import org.jmin.jda.impl.util.StringUtil;
import org.jmin.jda.mapping.ParamMap;
import org.jmin.jda.mapping.ParamUnit;
import org.jmin.jda.mapping.ParamValueMode;


/**
 * SQL语句分析器
 *
 * @author Chris
 * @version 1.0
 * 
 * //#propertyName:columnTypeName:PropertyType:ParamPersister:ParamValueMode#
 */
public class StaticSqlAnalyzer {
	
	/**
	 * 分析参数的位置信息
	 */
	public static ParamResult analyzeStaticSQL(String sqlId,String SQL,Class paramClass,JdaContainer container)throws SQLException{
		int startPos = 0;
		ParamPosition currentPosition = null;
		List paramUnitList = new ArrayList();
		ParamResult result = new ParamResult();
		
		if(SQL.indexOf(ParamSymbol.Symbol1.getStartSymbol())==-1  
			&& SQL.indexOf(ParamSymbol.Symbol2.getStartSymbol())==-1 
			&& SQL.indexOf(ParamSymbol.Symbol3.getStartSymbol())==-1
			&& SQL.indexOf(ParamSymbol.Symbol4.getStartSymbol())==-1
			&& SQL.indexOf(ParamSymbol.Symbol5.getStartSymbol())==-1){//无参数
			
			result.setExeSQL(SQL);
			result.setParamMap(null);
			return result;
		}else{
			while(startPos < SQL.length()){
				currentPosition = StaticSqlAnalyzer.getMinPosition(SQL,startPos,ParamSymbol.Symbols);
				if(currentPosition != null){	
				  if(currentPosition.getEndIndex() == -1){
				  	throw new ParamMapException(sqlId,"one parameter miss end symbol:'"+currentPosition.getParamSymbol().getEndSymbol() +"'");
				  }else{
				  	String blockText = SQL.substring(
				  			currentPosition.getStartIndex() + currentPosition.getParamSymbol().getStartSymbol().length(),
				  			currentPosition.getEndIndex());
				  	
				  	SQL = StringUtil.replace(SQL,currentPosition.getStartIndex(),
				  			                         currentPosition.getEndIndex() 
				  			                         +currentPosition.getParamSymbol().getEndSymbol().length()-1,"?");
				  	
				  	//#propertyName:columnTypeName:PropertyType:ParamPersister:ParamValueMode#
				  	String[]items = StringUtil.split(blockText,":");
				  	if(StringUtil.isNull(items[0]))
				  		throw new ParamMapException(sqlId,"Parameter name can't be null ");
				  	ParamUnit paramUnit=container.createParamUnit(items[0]);
				  	if(items.length >=2 && !StringUtil.isNull(items[1]))
				  		paramUnit.setParamColumnTypeName(items[1]);
				  	
				  	if(items.length >=3 && !StringUtil.isNull(items[2]))
							try {
								paramUnit.setPropertyType(ClassUtil.loadClass(items[2]));
							} catch (Throwable e) {
								throw new ParamMapException(sqlId,"Can't loand parameter type: "+ items[2],e);
							}
							
				  	if(items.length >=4 && !StringUtil.isNull(items[3]))
							try {
								paramUnit.setJdbcTypePersister((JdaTypePersister)ClassUtil.loadClass(items[3]).newInstance());
							} catch (Throwable e) {
								throw new ParamMapException(sqlId,"Can't create parameter persister instance for class: "+ items[3],e);
							}
							
				  	if(items.length >=5 && !StringUtil.isNull(items[5]))
				  		paramUnit.setParamValueMode(ParamValueMode.getParamMode(items[4]));
				  	
				  	paramUnitList.add(paramUnit);
				  }
				}else{
					break;
				}
		  }
			
			result.setExeSQL(SQL);
			if(paramUnitList.size() == 1){
				if(paramClass== null) 
					paramClass = Object.class;
			}
		
			if(paramUnitList.size() >0){
				ParamMap map = container.createParamMap(paramClass,(ParamUnit[])paramUnitList.toArray(new ParamUnit[0]));
				result.setParamMap(map);
			}
			return result;
		}
 	}
  

	/**
	 * 搜索字符串
	 */
  private static ParamPosition getMinPosition(String value,int beginIndex,ParamSymbol[] symbols){
  	List positionList = new ArrayList();
		int startIndex = -1, endIndex = -1;
		for (int i = 0; i < symbols.length; i++) {
			startIndex = -1;
			endIndex = -1;
			ParamSymbol symbol = symbols[i];
			startIndex = value.indexOf(symbol.getStartSymbol(), beginIndex);
			if(startIndex!= -1){
				 endIndex = value.indexOf(symbol.getEndSymbol(),startIndex+ symbol.getStartSymbol().length());
				 if(endIndex != -1)
					 positionList.add(new ParamPosition(startIndex,endIndex,symbol));
			}
		}
		
		Collections.sort(positionList);
		if (!positionList.isEmpty()) {
			return (ParamPosition) positionList.get(0);
		} else {
			return null;
		}
  }
  
//  public static void main(String[] args)throws SQLException{
//   	String sql ="select * from User where name=%#{dd:varchar}% and sex=#{dd:varchar}";
//   	JdaSourceInfo dataSourceInfo = new JdbcSourceInfo(Link.getDriver(),Link.getURL(),Link.getUser(),Link.getPassword());
//		JdaContainer container = new JdaContainerImpl(dataSourceInfo);	
//   	
//   	ParamResult result = StaticSqlAnalyzer.analyzeStaticSQL("123",sql,String.class,container);
//   	System.out.println(result.getExeSQL());
//   	ParamUnit[] units = result.getParamMap().getParamUnits();
//   	
//  	for(int i =0;i<units.length;i++){
//  		System.out.println(units[i].getPropertyName());
//  		System.out.println(units[i].getParamColumnTypeName());
//  	  System.out.println("--------------");
//  	}
//  	
//  	String sql2 ="select * from User where name=#[dd:varchar] and sex=#[dd]";
//  	result = StaticSqlAnalyzer.analyzeStaticSQL("123",sql2,String.class,container);
//   	System.out.println(result.getExeSQL());
//   	units = result.getParamMap().getParamUnits();
//   	
//  	for(int i =0;i<units.length;i++){
//  		System.out.println(units[i].getPropertyName());
//  		System.out.println(units[i].getParamColumnTypeName());
//  	  System.out.println("--------------");
//  	}
//  	
  	
//  
//   	String value3 ="select * from User where name=[?|%#dd:varchar:123#%] and sex=[?|#dd#] and sex=[?|#dd#] and sex=[?|#dd#] and sex=[?|#dd#]";
//   	SQLParamResult result3 = SQLParamAnalyzer.analyzeSQL(value3);
//   	System.out.println(result3.getExecutableSQL());
//  	parmeters = result3.getInlineParameters();
//  	for(int i =0;i<parmeters.length;i++){
//  		System.out.println(parmeters[i].getPropertyName());
//  		System.out.println(parmeters[i].getColumnType());
//  		System.out.println(parmeters[i].getNullValue());
//  	  System.out.println("Suffix " + parmeters[i].getParameterPrefix() +" " + parmeters[i].getParameterSuffix());
//  	  System.out.println("--------------");
//  	}
//  }
}
