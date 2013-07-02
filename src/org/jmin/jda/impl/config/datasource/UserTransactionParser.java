 /*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.config.datasource;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.jdom.Element;
import org.jmin.jda.UserTransactionInfo;
import org.jmin.jda.impl.util.StringUtil;
import org.jmin.log.Logger;

/**
 * 事务解析
 * 
 * @author Chris Liao
 */

public class UserTransactionParser {
	
	private static Logger logger = Logger.getLogger(UserTransactionParser.class);

	/**
	 * 事务解析
	 */
	public UserTransactionInfo parse(Element element)throws SQLException {
		
		if(element == null){
			return null;
		}else{
			String name=null,factory=null,provider=null,principal=null,credentials ="";
			List infoList = element.getChildren("property");
			Iterator itor = infoList.iterator();
			while(itor.hasNext()){
				Element subElement =(Element)itor.next();
				String attrName = (String)subElement.getAttributeValue("name");
				if(JdbcSourceNodes.transactionJtaName.equals(attrName)){
					name = subElement.getTextTrim();
					logger.info("jta.name: " + name);
				}else if(JdbcSourceNodes.transactionJtaFactory.equals(attrName)){
					factory = subElement.getTextTrim();
					logger.info("jta.factory: " + factory);
				}else if(JdbcSourceNodes.transactionJtaProvider.equals(attrName)){
					provider = subElement.getTextTrim();
					logger.info("jta.provider: " + provider);
				}else if(JdbcSourceNodes.transactionJtaPrincipal.equals(attrName)){
					principal = subElement.getTextTrim();
					logger.info("jta.principal: " + principal);
				}else if(JdbcSourceNodes.transactionJtaCredentials.equals(attrName)){
					credentials = subElement.getTextTrim();
					logger.info("jta.credentials: " + credentials);
				}
			}
			
			if(!StringUtil.isNull(name))
			 return new UserTransactionInfo(name,factory,provider,principal,credentials);
			else
				return null;
		}
	}
}
