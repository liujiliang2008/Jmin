/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.config.datasource;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.jdom.Element;
import org.jmin.jda.JdaContainer;

/**
 * 数据源解析
 * 
 * @author Chris Liao
 */

public class JdbcTypeImporter {

	/**
	 * 数据源解析
	 */
	public void importJdbcTypes(Element element, JdaContainer container)throws SQLException {
		if (element != null) {
			String code = null, name = null;
			List typeNodeList = element.getChildren("type");
			Iterator itor = typeNodeList.iterator();
			while (itor.hasNext()) {
				Element subElement = (Element) itor.next();
				code = subElement.getAttributeValue("code");
				name = subElement.getAttributeValue("name");
				container.addJdbcType(name, Integer.parseInt(code));
			}
		}
	}
}