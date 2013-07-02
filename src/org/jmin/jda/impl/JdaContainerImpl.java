/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.jmin.jda.JdaContainer;
import org.jmin.jda.JdaTypeConverter;
import org.jmin.jda.JdaTypeConverterMap;
import org.jmin.jda.JdaTypePersister;
import org.jmin.jda.JdaSession;
import org.jmin.jda.JdaSourceInfo;
import org.jmin.jda.impl.connection.ConnectionPool;
import org.jmin.jda.impl.connection.ConnectionPoolFactory;
import org.jmin.jda.impl.converter.JdaTypeConverterMapImpl;
import org.jmin.jda.impl.converter.JdaTypeConvertFactory;
import org.jmin.jda.impl.converter.JdaTypeBaseConverter;
import org.jmin.jda.impl.converter.base.BoolConverter;
import org.jmin.jda.impl.converter.base.ByteConverter;
import org.jmin.jda.impl.converter.base.BytesConverter;
import org.jmin.jda.impl.converter.base.CharConverter;
import org.jmin.jda.impl.converter.base.DoubleConverter;
import org.jmin.jda.impl.converter.base.FloatConverter;
import org.jmin.jda.impl.converter.base.IntegerConverter;
import org.jmin.jda.impl.converter.base.LongConverter;
import org.jmin.jda.impl.converter.base.ShortConverter;
import org.jmin.jda.impl.converter.base.StringConverter;
import org.jmin.jda.impl.converter.blob.BlobConverter;
import org.jmin.jda.impl.converter.clob.ClobConverter;
import org.jmin.jda.impl.converter.date.CalendarConverter;
import org.jmin.jda.impl.converter.date.DateConverter;
import org.jmin.jda.impl.converter.date.DateTimeConverter;
import org.jmin.jda.impl.converter.date.DateTimestampConverter;
import org.jmin.jda.impl.converter.date.UtilDateConverter;
import org.jmin.jda.impl.converter.math.BigDecimalConverter;
import org.jmin.jda.impl.converter.math.BigIntegerConverter;
import org.jmin.jda.impl.dynamic.DynTagValidator;
import org.jmin.jda.impl.exception.ParamMapException;
import org.jmin.jda.impl.exception.ResultMapException;
import org.jmin.jda.impl.exception.SqlDefinitionException;
import org.jmin.jda.impl.exception.SqlDynTagException;
import org.jmin.jda.impl.mapping.param.ParamMapImpl;
import org.jmin.jda.impl.mapping.param.ParamUnitImpl;
import org.jmin.jda.impl.mapping.param.ParamValidator;
import org.jmin.jda.impl.mapping.result.RelationUnitImpl;
import org.jmin.jda.impl.mapping.result.ResultMapImpl;
import org.jmin.jda.impl.mapping.result.ResultUnitImpl;
import org.jmin.jda.impl.mapping.result.ResultValidator;
import org.jmin.jda.impl.persister.ObjectHandler;
import org.jmin.jda.impl.persister.base.BooleanHandler;
import org.jmin.jda.impl.persister.base.ByteHandler;
import org.jmin.jda.impl.persister.base.BytesHandler;
import org.jmin.jda.impl.persister.base.DoubleHandler;
import org.jmin.jda.impl.persister.base.FloatHandler;
import org.jmin.jda.impl.persister.base.IntegerHandler;
import org.jmin.jda.impl.persister.base.LongHandler;
import org.jmin.jda.impl.persister.base.ShortHandler;
import org.jmin.jda.impl.persister.base.StringHandler;
import org.jmin.jda.impl.persister.blob.BlobHandler;
import org.jmin.jda.impl.persister.clob.ClobHandler;
import org.jmin.jda.impl.persister.date.CalendarDateHandler;
import org.jmin.jda.impl.persister.date.CalendarDateTimeHandler;
import org.jmin.jda.impl.persister.date.CalendarDateTimestampHandler;
import org.jmin.jda.impl.persister.date.DateHandler;
import org.jmin.jda.impl.persister.date.DateTimeHandler;
import org.jmin.jda.impl.persister.date.DateTimestampHandler;
import org.jmin.jda.impl.persister.date.UtilDateHandler;
import org.jmin.jda.impl.persister.date.UtilDateTimeHandler;
import org.jmin.jda.impl.persister.date.UtilDateTimestampHandler;
import org.jmin.jda.impl.persister.math.BigDecimalHandler;
import org.jmin.jda.impl.persister.math.BigIntegerHandler;
import org.jmin.jda.impl.statement.SqlBaseStatement;
import org.jmin.jda.impl.statement.SqlDynStatement;
import org.jmin.jda.impl.statement.SqlOperationType;
import org.jmin.jda.impl.statement.SqlStaticStatement;
import org.jmin.jda.impl.transaction.TransactionManager;
import org.jmin.jda.impl.util.StringUtil;
import org.jmin.jda.mapping.ParamMap;
import org.jmin.jda.mapping.ParamUnit;
import org.jmin.jda.mapping.RelationUnit;
import org.jmin.jda.mapping.ResultMap;
import org.jmin.jda.mapping.ResultUnit;
import org.jmin.jda.statement.DynTag;
import org.jmin.jda.statement.tag.ChooseTag;
import org.jmin.jda.statement.tag.TextTag;
import org.jmin.log.Logger;

/**
 * SQL映射容器工厂实现
 * 
 * @author Chris liao
 */

public class JdaContainerImpl implements JdaContainer{
	
	/**
	 *存放jdbc Typ映射
	 */
	private Map jdbcTypeMap=new HashMap();
	
	/**
	 *存放jdbc type的映射
	 */
	private Map paramPersisterMap=new HashMap();
	
	/**
	 * 存放SQL描述定义
	 */
	private Map sqlDefinitionMap=new HashMap();
	
	/**
	 * 转换器列表
	 */
	private JdaTypeConverterMapImpl converterMap = new JdaTypeConverterMapImpl();
	
	/**
	 * 数据源定义
	 */
	private JdaSourceInfo dataSourceInfo;

	/**
	 * 连接代理池
	 */
	private ConnectionPool connectionPool;
	
	/**
	 * 事务管理
	 */
	private TransactionManager transactionManager;
	
	/**
	 * logger
	 */
	private static Logger logger = Logger.getLogger(JdaContainerImpl.class);
	
	/**
	 * 构造函数
	 */
	public JdaContainerImpl(JdaSourceInfo dataSourceInfo)throws SQLException{
		this.registerDefaultJdbcType();
		this.registerDefaultConverter();
		this.registerDefaultParamPersister();
		this.dataSourceInfo = dataSourceInfo;
		this.connectionPool = ConnectionPoolFactory.createConnectionPool(dataSourceInfo);
		this.transactionManager= new TransactionManager(dataSourceInfo.getUserTransactionInfo());
	}

	/**
	 * 是否包含表达式
	 */
	public boolean containsSql(String id)throws SQLException{
		return this.sqlDefinitionMap.containsKey(id);
	}
	
	/**
	 * 是否为静态SQL
	 */
	public boolean isStaticSql(String id)throws SQLException{
		SqlBaseStatement statement = this.getSqlStatement(id);
		return (statement instanceof SqlStaticStatement);
	}

	/**
	 * 是否为动态SQL
	 */
	public boolean isDynamicSql(String id)throws SQLException{
		SqlBaseStatement statement = this.getSqlStatement(id);
		return (statement instanceof SqlDynStatement);
	}
	
	/**
	 * 注销SQL
	 */
	public void unregisterSql(String id)throws SQLException{
		this.sqlDefinitionMap.remove(id);
		logger.debug("unregistered sql with id:"+id );
	}
	
	/**
	 * 注册SQL的表达式
	 */
	public void registerStaticSql(String id,String sql)throws SQLException{
		this.registerStaticSql(id,sql,null,null);
	}
	
	/**
	 * 注册SQL的表达式
	 */
	public void registerStaticSql(String id,String sql,ParamMap paramMap)throws SQLException{
		this.registerStaticSql(id,sql,paramMap,null);
	}
	
	/**
	 * 注册SQL的表达式
	 */
	public void registerStaticSql(String id,String sql,ResultMap resultMap)throws SQLException{
		this.registerStaticSql(id,sql,null,resultMap);
	}
	
	/**
	 * 注册SQL的表达式
	 */
	public void registerStaticSql(String id,String sql,ParamMap paramMap,ResultMap resultMap)throws SQLException{
		int paramCount = StringUtil.getWordCount(sql,"?");//通过SQL中?来获得参数个数。
		SqlOperationType sqlType = this.getStaticOpertionType(id,sql);
		if(paramCount ==1 && paramMap == null)
			paramMap = this.createParamMap(Object.class,null);
	
		ParamValidator.checkParamMap(id,sqlType,paramCount,(ParamMapImpl)paramMap,this);
    ResultValidator.checkResultMap(id,sqlType,(ResultMapImpl)resultMap,this);
		this.sqlDefinitionMap.put(id,new SqlStaticStatement(id,sql,sqlType,paramMap,resultMap));
		logger.debug("Registered static sql("+id+ "): " + sql);
	}

	/**
	 * 注册动态SQL的
	 */
	public void registerDynamicSql(String id, DynTag[] tags,Class paramClass)throws SQLException{
		this.registerDynamicSql(id,tags,paramClass,null);
	}
	
	/**
	 * 注册动态SQL的
	 */
	public void registerDynamicSql(String id,DynTag[] tags,Class paramClass,ResultMap resultMap)throws SQLException{
		SqlOperationType sqlOpType = this.getDynamicOpertionType(id,tags);
		this.checkDynTags(id,tags,paramClass,sqlOpType);
		ResultValidator.checkResultMap(id,sqlOpType,(ResultMapImpl)resultMap,this);
		
		this.sqlDefinitionMap.put(id,new SqlDynStatement(id,sqlOpType,tags,paramClass,resultMap));
		logger.debug("Registered dynamic sql("+id+ ")");
	}

	/**
	 * 创建参数属性
	 */
	public ParamUnit createParamUnit(String name)throws SQLException{
		return createParamUnit(name,null);
	}

	/**
	 * 创建结果属性
	 */
	public ResultUnit createResultUnit(String name)throws SQLException{
		return createResultUnit(name,null);
	}
	
	/**
	 * 创建参数属性
	 */
	public ParamUnit createParamUnit(String name,Class type)throws SQLException{
		if(StringUtil.isNull(name))throw new ParamMapException(null,"Parameter property name can't be null");
		return new ParamUnitImpl(name,type);
	}
	
	
	/**
	 * 创建结果属性
	 */
	public ResultUnit createResultUnit(String name,Class type)throws SQLException{
		if(StringUtil.isNull(name))throw new ParamMapException(null,"Result property name can't be null");
		return new ResultUnitImpl(name,type);
	}
	
	/**
	 * 创建关联属性
	 */
	public RelationUnit createRelationUnit(String name,String sqlId)throws SQLException{
		return this.createRelationUnit(name,null,sqlId);
	}
	
	/**
	 * 创建关联属性
	 */
	public RelationUnit createRelationUnit(String name,Class type,String sqlId)throws SQLException{
		if(StringUtil.isNull(sqlId))throw new ResultMapException(null,"Relation sql id can't be null");
		if(StringUtil.isNull(name))throw new ParamMapException(null,"Relation property name can't be null");
		return new RelationUnitImpl(sqlId,name,type);
	}
	
	
	
	/**
	 * 创建参数映射列表
	 */
	public ParamMap createParamMap(Class paramClass,ParamUnit[]paramUnits)throws SQLException{
		return new ParamMapImpl(paramClass,paramUnits);
	}
	
	/**
	 * 创建结果映射列表
	 */
	public ResultMap createResultMap(Class resultClass,ResultUnit[]resultUnits)throws SQLException{
		return new ResultMapImpl(resultClass,resultUnits);
	}
	
	/**
	 * 创建结果映射列表
	 */
	public ResultMap createResultMap(Class resultClass,ResultUnit[]resultUnits,RelationUnit[] relationUnits)throws SQLException{
		return new ResultMapImpl(resultClass,resultUnits,relationUnits);
	}
	
	/**
	 * 验证映射列表中是否包含Jdbc type
	 */
	public boolean containsJdbcType(String typeName)throws SQLException{
		if(typeName!=null)typeName = typeName.toUpperCase();
		return this.jdbcTypeMap.containsKey(typeName);
	}
	
	/**
	 *  获得jdbc type code
	 */
	public int getJdbcTypeCode(String typeName)throws SQLException{
		if(typeName!=null)typeName = typeName.toUpperCase();
		Integer code =(Integer)this.jdbcTypeMap.get(typeName);
		if(code!=null)
			return code.intValue();
		else
			throw new ParamMapException("Not found jdbc type for name:"+typeName);
	}

	/**
	 * 注册Jdbc类
	 */
	public void removeJdbcType(String typeName)throws SQLException{
		if(typeName!=null)typeName = typeName.toUpperCase();
		this.jdbcTypeMap.remove(typeName);
	}
	
	/**
	 * 注册Jdbc type
	 */
	public void addJdbcType(String typeName,int typeCode)throws SQLException{
		if(typeName!=null)typeName=typeName.toUpperCase();
		this.jdbcTypeMap.put(typeName,new Integer(typeCode));
	}


	/**
	 * 删除类型转换器
	 */
	public void removeTypeConverter(Class type)throws SQLException{
		this.converterMap.removeTypeConverter(type);
	}
	
	/**
	 * 是否包含类型转换器
	 */
	public boolean containsTypeConverter(Class type)throws SQLException{
		return this.converterMap.supportsType(type);
	}
	
  /**
   * 将对象转换为目标类型对象
   */
  public Object convertObject(Object obj,Class type)throws SQLException{
  	return JdaTypeConvertFactory.convert(obj,type,converterMap);
  }

	/**
	 * 获得类型转换器
	 */
	public JdaTypeConverter getTypeConverter(Class type)throws SQLException{
		return this.converterMap.getTypeConverter(type);
	}
	
	/**
	 * 注册类型转换器
	 */
	public void addTypeConverter(Class type,JdaTypeConverter converter)throws SQLException{
		this.converterMap.putTypeConverter(type,converter);
	}
	
	/**
	 * 获得类型转换器列表
	 */
	public JdaTypeConverterMap getTypeConverterMap()throws SQLException{
		return this.converterMap;
	}
	
	/**
	 * 删除参数持久器
	 */
	public void removeTypePersister(Class type)throws SQLException{
		this.removeTypePersister(type,null);
	}

	/**
	 *  删除参数持久器
	 */
	public void removeTypePersister(Class type,String jdbcName)throws SQLException{
		Map subMap=(Map)paramPersisterMap.get(type);
		if(jdbcName!=null)jdbcName=jdbcName.toLowerCase();
		if(subMap != null)
			subMap.remove(jdbcName);
	}
	
	/**
	 * 获得参数持久器
	 */
	public JdaTypePersister getTypePersister(Class type)throws SQLException{
		return this.getTypePersister(type,null);
	}
	
	/**
	 * 获得参数持久器
	 */
	public JdaTypePersister getTypePersister(Class type,String jdbcName)throws SQLException{
		JdaTypePersister paramPersister= null;
		Map subMap=(Map)paramPersisterMap.get(type);
		if(jdbcName!=null)jdbcName=jdbcName.toLowerCase();
		if(subMap != null){
			paramPersister=(JdaTypePersister)subMap.get(jdbcName);	
			if(paramPersister == null)
				paramPersister=(JdaTypePersister)subMap.get(null);
		}
		
		return paramPersister;
	}

	/**
	 * 删除参数持久器
	 */
	public boolean containsTypePersister(Class type)throws SQLException{
		return this.paramPersisterMap.containsKey(type);
	}
	
	/**
	 * 删除参数持久器
	 */
	public boolean containsTypePersister(Class type,String jdbcName)throws SQLException{
		Map subMap =(Map)this.paramPersisterMap.get(type);
		if(jdbcName!=null)jdbcName=jdbcName.toLowerCase();
		if (subMap!= null)
			return subMap.containsKey(jdbcName);
		else 
			return false;
	}
	
	/**
	 * 注册参数持久器
	 */
	public void addTypePersister(Class type,JdaTypePersister persister)throws SQLException{
		this.addTypePersister(type,null,persister);
	}
 	
	/**
	 *  注册参数持久器
	 */
	public void addTypePersister(Class type,String jdbcName,JdaTypePersister persister)throws SQLException{
	  Map subMap =(Map)this.paramPersisterMap.get(type);
		if(jdbcName!=null)jdbcName = jdbcName.toLowerCase();
	  if(subMap == null) {
	  	subMap = new HashMap();
			this.paramPersisterMap.put(type,subMap);
		}
	  
	  subMap.put(jdbcName,persister);
	}
 
  /**
   * 创建Session
   */
  public JdaSession openSession() throws SQLException{ 
  	return new JdaSessionImpl(this);
  }
  
 
	/****************************以下为内部使用方法********************************/
  
  /**
	 * 获取数据库的连接池
	 */
  ConnectionPool getConnectionPool(){
  	return this.connectionPool;
  }
  
  /**
   * 获得数据源信息
   */
	JdaSourceInfo getDataSourceInfo() {
		return this.dataSourceInfo;
	}
	
  /**
   * 获得数据源信息
   */
	TransactionManager getTransactionManager() {
		return this.transactionManager;
	}
  
	/**
	* 获取一个SQL定义
	*/
	public SqlBaseStatement getSqlStatement(String id)throws SQLException{
		SqlBaseStatement statement = (SqlBaseStatement)this.sqlDefinitionMap.get(id);
		if(statement==null)
			throw new SqlDefinitionException(id,"SQL definition not found");
		else
		  return statement;
	 }
	
	/**
	 * 初始化JDbc type
	 */
	private void registerDefaultJdbcType(){
		try{
			this.jdbcTypeMap = new HashMap();
			this.addJdbcType("BOOLEAN",Types.BIT);
			this.addJdbcType("BIT",Types.BIT);
			this.addJdbcType("TINYINT",Types.TINYINT);
			this.addJdbcType("SMALLINT",Types.SMALLINT);
			this.addJdbcType("INTEGER",Types.INTEGER);
			this.addJdbcType("BIGINT",Types.BIGINT);
			this.addJdbcType("FLOAT",Types.FLOAT);
			this.addJdbcType("REAL",Types.REAL);
			this.addJdbcType("DOUBLE",Types.DOUBLE);
			this.addJdbcType("NUMERIC",Types.NUMERIC);
			this.addJdbcType("DECIMAL",Types.DECIMAL);
			this.addJdbcType("CHAR",Types.CHAR);
			this.addJdbcType("VARCHAR",Types.VARCHAR);
			this.addJdbcType("LONGVARCHAR",Types.LONGVARCHAR);
			this.addJdbcType("TIME",Types.TIME);
			this.addJdbcType("DATE",Types.DATE);
			this.addJdbcType("TIMESTAMP",Types.TIMESTAMP);
			this.addJdbcType("BINARY",Types.BINARY);
			this.addJdbcType("VARBINARY",Types.VARBINARY);
			this.addJdbcType("LONGVARBINARY",Types.LONGVARBINARY);
			this.addJdbcType("NULL",Types.NULL);
			this.addJdbcType("OTHER",Types.OTHER);
			this.addJdbcType("JAVA_OBJECT",Types.JAVA_OBJECT);
			this.addJdbcType("DISTINCT",Types.DISTINCT);
			this.addJdbcType("STRUCT",Types.STRUCT);
			this.addJdbcType("ARRAY",Types.ARRAY);
			this.addJdbcType("BLOB",Types.BLOB);
			this.addJdbcType("CLOB",Types.CLOB);
			this.addJdbcType("REF",Types.REF);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 初始化type handler
	 */
	private void registerDefaultConverter(){
			JdaTypeConverter converter = new BoolConverter();
			this.converterMap.putTypeConverter(Boolean.class,converter);
			this.converterMap.putTypeConverter(boolean.class,converter);
		
			converter = new ByteConverter();
			this.converterMap.putTypeConverter(Byte.class,converter);
			this.converterMap.putTypeConverter(byte.class,converter);
 
			converter = new ShortConverter();
			this.converterMap.putTypeConverter(Short.class,converter);
			this.converterMap.putTypeConverter(short.class,converter);
			
			converter = new IntegerConverter();
			this.converterMap.putTypeConverter(Integer.class,converter);
			this.converterMap.putTypeConverter(int.class,converter);
			
			converter = new LongConverter();
			this.converterMap.putTypeConverter(Long.class,converter);
			this.converterMap.putTypeConverter(long.class,converter);

			converter = new FloatConverter();
			this.converterMap.putTypeConverter(Float.class,converter);
			this.converterMap.putTypeConverter(float.class,converter);
		
			converter = new DoubleConverter();
			this.converterMap.putTypeConverter(Double.class,converter);
			this.converterMap.putTypeConverter(double.class,converter);
 
			converter = new CharConverter();
			this.converterMap.putTypeConverter(char.class,converter);
			this.converterMap.putTypeConverter(Character.class,converter);
			
			this.converterMap.putTypeConverter(Object.class,new JdaTypeBaseConverter());
			this.converterMap.putTypeConverter(BigInteger.class,new BigIntegerConverter());
			this.converterMap.putTypeConverter(BigDecimal.class,new BigDecimalConverter());
			this.converterMap.putTypeConverter(String.class,new StringConverter());
		
			this.converterMap.putTypeConverter(Clob.class,new ClobConverter());
			this.converterMap.putTypeConverter(Blob.class,new BlobConverter());
			this.converterMap.putTypeConverter(byte[].class,new BytesConverter());

		
			this.converterMap.putTypeConverter(Date.class,new DateConverter());
			this.converterMap.putTypeConverter(Time.class,new DateTimeConverter());
			this.converterMap.putTypeConverter(Timestamp.class,new DateTimestampConverter());
	
			this.converterMap.putTypeConverter(Calendar.class,new CalendarConverter());
			this.converterMap.putTypeConverter(java.util.Date.class,new UtilDateConverter());
	
	}
	
	/**
	 * 初始化type handler
	 */
	private void registerDefaultParamPersister(){
		try {
			JdaTypePersister persister = new BooleanHandler();
			this.addTypePersister(Boolean.class,persister);
			this.addTypePersister(boolean.class,persister);

			persister = new ByteHandler();
			this.addTypePersister(Byte.class,persister);
			this.addTypePersister(byte.class,persister);
		 		
			persister = new ShortHandler();
			this.addTypePersister(Short.class,persister);
			this.addTypePersister(short.class,persister);
		 		
			persister = new IntegerHandler();
			this.addTypePersister(Integer.class,persister);
			this.addTypePersister(int.class,persister);
		 		
			persister = new LongHandler();
			this.addTypePersister(Long.class,persister);
			this.addTypePersister(long.class,persister);
		 		
			persister = new FloatHandler();
			this.addTypePersister(Float.class,persister);
			this.addTypePersister(float.class,persister);
			 		
			persister = new DoubleHandler();
			this.addTypePersister(Double.class,persister);
			this.addTypePersister(double.class,persister);
		 		
			this.addTypePersister(BigInteger.class,new BigIntegerHandler());
			this.addTypePersister(BigDecimal.class,new BigDecimalHandler());
		    this.addTypePersister(String.class,new StringHandler());
			this.addTypePersister(Object.class,new ObjectHandler());
			this.addTypePersister(Clob.class,new ClobHandler());
			this.addTypePersister(Blob.class,new BlobHandler());	
			this.addTypePersister(byte[].class,new BytesHandler());	
 
			this.addTypePersister(java.sql.Date.class,new DateHandler());
			this.addTypePersister(java.sql.Time.class,new DateTimeHandler());
			this.addTypePersister(java.sql.Timestamp.class,new DateTimestampHandler());
			
			this.addTypePersister(java.util.Date.class,new UtilDateHandler());
			this.addTypePersister(java.util.Date.class,"DATE",new UtilDateHandler());
			this.addTypePersister(java.util.Date.class,"TIME",new UtilDateTimeHandler());
			this.addTypePersister(java.util.Date.class,"TIMESTAMP",new UtilDateTimestampHandler());
 
			this.addTypePersister(java.util.Calendar.class,new CalendarDateHandler());
			this.addTypePersister(java.util.Calendar.class,"DATE",new CalendarDateHandler());
			this.addTypePersister(java.util.Calendar.class,"TIME",new CalendarDateTimeHandler());
			this.addTypePersister(java.util.Calendar.class,"TIMESTAMP",new CalendarDateTimestampHandler());
			
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}

	/**
	 * 检查映射结果属性
	 */
	private SqlOperationType getStaticOpertionType(String id,String sql)throws SQLException{
		if(StringUtil.isNull(id))
			throw new ParamMapException("SQL id can't be null");
		if(StringUtil.isNull(sql))
			throw new SqlDefinitionException(id,"SQL can't be null");
		if(this.containsSql(id))
			throw new SqlDefinitionException(id,"SQL id has been registered with another sql");

		return getSqlOperateType(id,sql);
	}
	
	/**
	 * 检查映射结果属性
	 */
	private SqlOperationType getDynamicOpertionType(String id,DynTag[] tas)throws SQLException{
		if(StringUtil.isNull(id))
			throw new ParamMapException("SQL id can't be null");
		if(tas==null || tas.length==0)
			throw new SqlDefinitionException(id,"Dynamic sql tags can't be null or emtpty");
		if(this.containsSql(id))
			throw new SqlDefinitionException(id,"SQL id has been registered with another sql");
		
		String firstBlock = getFistTextBlock(id,tas[0]);
		return getSqlOperateType(id,firstBlock);
	}

	/**
	 * 检查节点
	 */
	private void checkDynTags(String id,DynTag[]tags,Class paramClass,SqlOperationType sqlOpType)throws SQLException{
		if(tags== null || tags.length==0)
			throw new SqlDefinitionException(id,"Sql dynamic tags can't be null or empty");
		if(paramClass==null)
			throw new SqlDefinitionException(id,"Parameter class can't be null");
		if(this.containsTypePersister(paramClass))
			throw new SqlDefinitionException(id,"Parameter class can't be base direct map type for dynamic sql");
		
		for(int i=0;i<tags.length;i++)
			DynTagValidator.checkDynTag(id,tags[i],paramClass,sqlOpType,this);
	}
	
	/**
	 * 获得第一个SQL文本片段
	 */
	private String getFistTextBlock(String id,DynTag tag)throws SQLException{
		if(tag instanceof TextTag){
			return ((TextTag)tag).getText();
		}else if(tag instanceof ChooseTag){
			if(((ChooseTag)tag).getSubWhenTagCount()==0)
				throw new SqlDynTagException(id,"Invalid dynamic sql,missed children in tag["+tag.getTagName()+"]");
			return getFistTextBlock(id,((ChooseTag)tag).getSubWhenTag(0));
		}else {
			if(tag.getChildrenCount()==0)
				throw new SqlDynTagException(id,"Invalid dynamic sql,missed children in tag["+tag.getTagName()+"]");
			return getFistTextBlock(id,tag.getChildren(0));
		}
	}
	
	/**
	 * 获得SQL操作类型
	 */
	private SqlOperationType getSqlOperateType(String id,String sql)throws SqlDefinitionException {
		int pos =sql.trim().indexOf(" ");
		if(pos == -1)throw new SqlDefinitionException(id,"SQL definition error:"+sql);
		
		String firstWord=sql.substring(0,pos);//第一个单词
		if(firstWord.startsWith("{")){
			return SqlOperationType.Procedure;
		}else if("insert".equalsIgnoreCase(firstWord)){
			return SqlOperationType.Insert;
		}else if("update".equalsIgnoreCase(firstWord)){
			return SqlOperationType.Update;
		}else if("delete".equalsIgnoreCase(firstWord)){
			return SqlOperationType.Delete;
		}else if("select".equalsIgnoreCase(firstWord)){
			return SqlOperationType.Select;
		}else {
			return SqlOperationType.Unknown;
		}
	}
}