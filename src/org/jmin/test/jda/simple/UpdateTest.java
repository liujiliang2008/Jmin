package org.jmin.test.jda.simple;

import java.sql.SQLException;

import org.jmin.jda.JdaSession;
import org.jmin.jda.JdaSourceInfo;
import org.jmin.jda.JdbcSourceInfo;
import org.jmin.jda.impl.JdaContainerImpl;
import org.jmin.jda.mapping.ParamMap;
import org.jmin.jda.mapping.ParamUnit;

public class UpdateTest {
	
	public static void main(String[] args){
		String SQL1 ="update USERINFO set sex=? where name=?";
		
		try {
			JdaSourceInfo dataSourceInfo = new JdbcSourceInfo(Link.getDriver(),Link.getURL(),Link.getUser(),Link.getPassword());
			JdaContainerImpl container = new JdaContainerImpl(dataSourceInfo);		
			ParamUnit property1 = container.createParamUnit("name",null);
			ParamUnit property2 = container.createParamUnit("sex",null);
			ParamMap map= container.createParamMap(User.class,new ParamUnit[]{property1,property2});
			
			container.registerStaticSql("update",SQL1,map);
			JdaSession session = container.openSession();
			session.update("update",new User("chris","Girl"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
