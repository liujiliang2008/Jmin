package org.jmin.test.jda.simple;

import java.sql.SQLException;

import org.jmin.jda.JdaContainer;
import org.jmin.jda.JdaPageList;
import org.jmin.jda.JdaSession;
import org.jmin.jda.JdaSourceInfo;
import org.jmin.jda.JdbcSourceInfo;
import org.jmin.jda.impl.JdaContainerImpl;
import org.jmin.jda.mapping.ResultMap;
import org.jmin.jda.mapping.ResultUnit;

public class PageSelectTest {
	
	public static void main(String[] args){
		String SQL1 ="select name,sex from USERINFO";
	
		try {
			JdaSourceInfo dataSourceInfo = new JdbcSourceInfo(Link.getDriver(),Link.getURL(),Link.getUser(),Link.getPassword());
			//dataSourceInfo.setSqlDialect(new OracleDialect());
			
			JdaContainer container = new JdaContainerImpl(dataSourceInfo);	
			ResultUnit property1 = container.createResultUnit("name",null);
			ResultUnit property2 = container.createResultUnit("sex",null);
 
			ResultMap map= container.createResultMap(User.class,new ResultUnit[]{property1,property2});
			container.registerStaticSql("select",SQL1,map);
			JdaSession session = container.openSession();
			JdaPageList list = session.findPageList("select",3);
			list.moveNextPage();
			list.moveToPage(3);
			Object[] array = list.getCurrentPage();
			for(int i=0;i<array.length;i++)
				System.out.println(array[i]);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}