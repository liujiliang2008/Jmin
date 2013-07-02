 /*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.config.datasource;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.jdom.Element;
import org.jmin.jda.JdaContainer;
import org.jmin.jda.JdaTypePersister;
import org.jmin.jda.impl.util.ClassUtil;

/**
 * 导入Handler类型
 * 
 * @author chris liao
 */
public class JdbcPersisterImporter {
	
	/**
	 * 导入Handler类型
	 */
	public void importParamPersisters(Element element, JdaContainer container)throws SQLException{
	 if (element != null) {
			String javaType=null,jdbcType=null,className=null;
				
			List list = element.getChildren(JdbcSourceNodes.ParamPersister);
			Iterator itor = list.iterator();
			while (itor.hasNext()) {
				Element subElement = (Element) itor.next();
				javaType = subElement.getAttributeValue("javaType");
				jdbcType = subElement.getAttributeValue("jdbcType");
				className = subElement.getAttributeValue("class");
				
				Class type = loadClass(javaType);
				JdaTypePersister handler = createTypeHandler(className);
				container.addTypePersister(type,jdbcType,handler);
			}
		}
	}

	/**
	 * 创建TypeHandler
	 */
	private JdaTypePersister createTypeHandler(String hanlderClassName)throws SQLException{
		try {
			Class JavaType = loadClass(hanlderClassName);
			Object handler = JavaType.newInstance();
			if(handler instanceof JdaTypePersister){
				return (JdaTypePersister)handler;
			}else{
				throw new SQLException("Class " + hanlderClassName  + " is not a validate type handler");
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
