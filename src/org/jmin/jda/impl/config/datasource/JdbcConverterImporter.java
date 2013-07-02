 /*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.config.datasource;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.jdom.Element;
import org.jmin.jda.JdaContainer;
import org.jmin.jda.JdaTypeConverter;
import org.jmin.jda.impl.util.ClassUtil;

/**
 * 导入Handler类型
 * 
 * @author chris liao
 */
public class JdbcConverterImporter {
	
	/**
	 * 导入Handler类型
	 */
	public void importResultConverters(Element element, JdaContainer container)throws SQLException{
	 if (element != null) {
			String javaType=null,className=null;
			List list = element.getChildren(JdbcSourceNodes.ResultConverter);
			Iterator itor = list.iterator();
			while (itor.hasNext()) {
				Element subElement = (Element) itor.next();
				javaType = subElement.getAttributeValue("javaType");
				className = subElement.getAttributeValue("class");
				
				Class type = loadClass(javaType);
				JdaTypeConverter handler = createTypeHandler(className);
				container.addTypeConverter(type,handler);
			}
		}
	}

	/**
	 * 创建TypeHandler
	 */
	private JdaTypeConverter createTypeHandler(String converterClassname)throws SQLException{
		try {
			Class JavaType = loadClass(converterClassname);
			Object handler = JavaType.newInstance();
			if(handler instanceof JdaTypeConverter){
				return (JdaTypeConverter)handler;
			}else{
				throw new SQLException("Class " + converterClassname  + " is not a validate result converter");
			}
		} catch (InstantiationException e) {
			throw new SQLException(e.getMessage());
		} catch (IllegalAccessException e) {
			throw new SQLException(e.getMessage());
		}
	}
	
	private Class loadClass(String className)throws SQLException{
		try {
			return ClassUtil.loadClass(className,true,JdbcPersisterImporter.class.getClassLoader());
		} catch (ClassNotFoundException e) {
			throw new SQLException(e.getMessage());
		}
	}
}
