package org.jmin.test.jda.simple;

import java.sql.SQLException;

import org.jmin.jda.JdaContainer;
import org.jmin.jda.JdaSession;
import org.jmin.jda.JdaSourceInfo;
import org.jmin.jda.JdbcSourceInfo;
import org.jmin.jda.impl.JdaContainerImpl;
import org.jmin.jda.mapping.ParamMap;
import org.jmin.jda.mapping.ParamUnit;

public class DeleteTest {
	public static void main(String[] args){
		
		String SQL1 ="delete from USERINFO where name='Liao'";
		String SQL2 ="delete from USERINFO where name=?";
	
		try {
			JdaSourceInfo dataSourceInfo = new JdbcSourceInfo(Link.getDriver(),Link.getURL(),Link.getUser(),Link.getPassword());
			JdaContainer container = new JdaContainerImpl(dataSourceInfo);		
			ParamUnit property1 = container.createParamUnit("name",null);
			container.registerStaticSql("delete1",SQL1);
			ParamMap map= container.createParamMap(User.class,new ParamUnit[]{property1});
			container.registerStaticSql("delete2",SQL2,map);
			JdaSession session = container.openSession();
			session.delete("delete1");
  		session.delete("delete2",new User("liao"));
  		session.delete("delete2",new User("emily"));
 
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
