package org.jmin.test.jda.simple;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jmin.jda.JdaContainer;
import org.jmin.jda.JdaSession;
import org.jmin.jda.JdaSourceInfo;
import org.jmin.jda.JdbcSourceInfo;
import org.jmin.jda.impl.JdaContainerImpl;
import org.jmin.jda.mapping.ParamMap;
import org.jmin.jda.mapping.ParamUnit;

public class InsertTest {
	
	public static void main(String[] args){
		String SQL1 ="insert into USERINFO(name,sex)values('liao','Man')";
		String SQL2 ="insert into USERINFO(name)values(?)";
		
		try {
			JdaSourceInfo dataSourceInfo = new JdbcSourceInfo(Link.getDriver(),Link.getURL(),Link.getUser(),Link.getPassword());
			JdaContainer container = new JdaContainerImpl(dataSourceInfo);		
			//ParamUnit property1 = container.createParamUnit("ID",null);
			ParamUnit property2 = container.createParamUnit("name",null);
			//ParamUnit property3 = container.createParamUnit("password",null);
			
			ParamMap map= container.createParamMap(User.class,new ParamUnit[]{property2});
			container.registerStaticSql("Insert1",SQL1);
			container.registerStaticSql("Insert2",SQL2,map);

			JdaSession session = container.openSession();

			session.insert("Insert2",new User("liao1"));
	    List list = new ArrayList();
			list.add("liao2");
			session.insert("Insert2",list);
			Map map2 = new HashMap();
			map2.put("name","liao3");
			session.insert("Insert2",map2);
			session.insert("Insert2",new Object[]{"liao4"});
			session.insert("Insert2",new Integer(1));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
