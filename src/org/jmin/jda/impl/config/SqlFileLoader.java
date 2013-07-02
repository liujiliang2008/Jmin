 /*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.config;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jmin.jda.JdaSourceInfo;
import org.jmin.jda.JdaContainer;
import org.jmin.jda.UserTransactionInfo;
import org.jmin.jda.impl.JdaContainerImpl;
import org.jmin.jda.impl.config.datasource.JdbcSourceNodes;
import org.jmin.jda.impl.config.datasource.JdbcSourceParser;
import org.jmin.jda.impl.config.datasource.JdbcTypeImporter;
import org.jmin.jda.impl.config.datasource.JdbcPersisterImporter;
import org.jmin.jda.impl.config.datasource.JdbcConverterImporter;
import org.jmin.jda.impl.config.datasource.UserTransactionParser;
import org.jmin.jda.impl.config.mappingfile.SqlFileImporter;
import org.jmin.jda.impl.exception.DataSourceException;
import org.jmin.jda.impl.exception.SqlDefinitionFileException;
import org.jmin.jda.impl.exception.TransactionConfigException;
import org.jmin.jda.impl.util.CloseUtil;
import org.jmin.jda.impl.util.StringUtil;
import org.jmin.log.Logger;

/**
 * 配置装载工厂
 * 
 * @author Chris Liao
 */
public class SqlFileLoader {
	
	/**
	 * logger
	 */
	private static Logger logger = Logger.getLogger(SqlFileLoader.class);

	/**
	 * 数据源解析
	 */
	private JdbcSourceParser dataSourceInfoParser = new JdbcSourceParser();
	
	/**
	 * jdbc类型解析
	 */
	private JdbcTypeImporter jdbcTypeInfoImporter = new JdbcTypeImporter();
	
	/**
	 * 事务解析
	 */
	private UserTransactionParser transactionInfoParser = new UserTransactionParser();
	
	/**
	 * jdbc类型解析
	 */
	private JdbcPersisterImporter paramPersisterInfoImporter = new JdbcPersisterImporter();
	
	/**
	 * jdbc类型解析
	 */
	private JdbcConverterImporter resultConverterImporter = new JdbcConverterImporter();
	
	
	
	/**
	 * 映射文件解析
	 */
	private SqlFileImporter mappingFileParser = new SqlFileImporter();
	
	/**
	 * 默认装载的XML配置文件
	 */
	private final String DEFAULT_JDBC_FILE_NAME="/jda.xml";
	
	/**
	 * 默认装载的XML配置文件
	 */
	private final String Default_JDBC_FILE_ENV_NAME ="jda";

	/**
	 * 装载默认文件
	 */
	public JdaContainer load()throws SQLException{
		ClassLoader classLoader =SqlFileLoader.class.getClassLoader();
		URL defaultFileURL = classLoader.getResource(DEFAULT_JDBC_FILE_NAME);
		String defaultEnvFilename = System.getProperty(Default_JDBC_FILE_ENV_NAME);
		
		if(defaultFileURL == null && !StringUtil.isNull(defaultEnvFilename))
			defaultFileURL = SqlFileFinder.find(defaultEnvFilename);
		if(defaultFileURL == null)
			 throw new SqlDefinitionFileException("Not found default sql configeruation file:" + DEFAULT_JDBC_FILE_NAME);
		else
			return load(defaultFileURL);
	}
	
	/**
	 * 装载文件
	 */
	public JdaContainer load(String filename)throws SQLException{
		return load(SqlFileFinder.find(filename));
	}
	
	/**
	 * 装载文件
	 */
	public JdaContainer load(URL url)throws SQLException{
		String mapFilename=null;
		String resourceFilename = null;
		InputStream XMLStream = null;
		try {
			SqlFileFinder.validateXMLFile(url);
			mapFilename =url.getFile();
			XMLStream = url.openStream();
			Document document = new SAXBuilder().build(XMLStream);
			Element rootElement = document.getRootElement();
			SqlFileFinder.validateXMLRoot(rootElement,JdbcSourceNodes.Root);
			
			Element datasourceElment = rootElement.getChild(JdbcSourceNodes.DataSource);
			Element columnTypeElment = rootElement.getChild(JdbcSourceNodes.JdbcTypes);

			JdaSourceInfo dataSourceInfo = dataSourceInfoParser.parse(datasourceElment);
			UserTransactionInfo transactionInfo = transactionInfoParser.parse(datasourceElment);
			dataSourceInfo.setUserTransactionInfo(transactionInfo);
			JdaContainer container = new JdaContainerImpl(dataSourceInfo);
			
			jdbcTypeInfoImporter.importJdbcTypes(columnTypeElment,container);
      paramPersisterInfoImporter.importParamPersisters(datasourceElment,container);
      resultConverterImporter.importResultConverters(datasourceElment,container);
      
			List mappingFileList = datasourceElment.getChildren(JdbcSourceNodes.Mapping);
			Iterator itor = mappingFileList.iterator();
			while(itor.hasNext()){
				Element mapFileElement = (Element)itor.next();
				resourceFilename = mapFileElement.getAttributeValue("resource");
			  logger.debug("Begin to load sql map file: "+ resourceFilename);
				URL fileURL = SqlFileFinder.find(resourceFilename);
			  mappingFileParser.importSQLMapFile(fileURL,container);
			  logger.debug("Finished loading sql map file: "+ resourceFilename);
			}
			return container;
		}catch(DataSourceException e){ 
			throw new SqlDefinitionFileException(null,"Failed to load data source info from file:"+mapFilename,e);
		}catch(TransactionConfigException e){
			throw new SqlDefinitionFileException(null,"Failed to load JTA transaction info from file:"+mapFilename,e);
		} catch (JDOMException e) {
			throw new SqlDefinitionFileException(null,"Failed to parse file:"+mapFilename,e);
		} catch (IOException e) {
			throw new SqlDefinitionFileException(null,"Failed to open file:"+mapFilename,e);
		} catch ( Throwable e) {
			throw new SqlDefinitionFileException(null,"Failed to open file:"+mapFilename,e);
		}finally{
			CloseUtil.close(XMLStream);
		}
	}
}
