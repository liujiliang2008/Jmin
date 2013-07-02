 /*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.config.mappingfile;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jdom.Element;
import org.jmin.jda.JdaContainer;
import org.jmin.jda.impl.exception.SqlDefinitionFileException;
import org.jmin.jda.impl.util.ClassUtil;
import org.jmin.jda.impl.util.StringUtil;

/**
 * 导入别名类型
 * 
 * @author chris liao
 */
public class AliasClassParser {
	
	/**
	 * 数据源解析
	 */
	public Map loadAliasMap(List elementList,JdaContainer container)throws SQLException{
		Map aliasMap = new HashMap();
		try {
			Iterator itor = elementList.iterator();
			while(itor.hasNext()){
				Element subElement=(Element)itor.next();
				String aliasName =subElement.getAttributeValue("name");
				String className =subElement.getAttributeValue("type");
				
				if(StringUtil.isNull(aliasName))
					throw new SqlDefinitionFileException(null,"Alias name can't be null");
				if(StringUtil.isNull(className))
					throw new SqlDefinitionFileException(null,"Class name can't be null at alias[" + aliasName+"]");
				
				aliasMap.put(aliasName,ClassUtil.loadClass(className));
				return aliasMap;
			}
		} catch (ClassNotFoundException e) {
			throw new SqlDefinitionFileException(null,e.getMessage(),e);
		}
 
		return aliasMap;
	}
}
