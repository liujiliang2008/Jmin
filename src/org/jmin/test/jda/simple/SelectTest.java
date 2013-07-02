package org.jmin.test.jda.simple;

import java.sql.SQLException;
import java.util.List;

import org.jmin.jda.JdaContainer;
import org.jmin.jda.JdaSession;
import org.jmin.jda.JdaSourceInfo;
import org.jmin.jda.JdbcSourceInfo;
import org.jmin.jda.impl.JdaContainerImpl;
import org.jmin.jda.mapping.ResultMap;
import org.jmin.jda.mapping.ResultUnit;

public class SelectTest {
	
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
			 
//			Map map = session.findObjectMap("select","name");
//			System.out.println(map);
			List list = session.findList("select","name",2,3);
			for(int i=0;i<list.size();i++){
				System.out.println(list.get(i));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}