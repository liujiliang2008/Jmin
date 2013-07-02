package org.jmin.test.jda.simple;

import java.sql.SQLException;

import org.jmin.jda.JdaContainer;
import org.jmin.jda.JdaSession;
import org.jmin.jda.JdaSourceInfo;
import org.jmin.jda.JdbcSourceInfo;
import org.jmin.jda.impl.JdaContainerImpl;
import org.jmin.jda.mapping.ParamMap;
import org.jmin.jda.mapping.ParamUnit;
import org.jmin.jda.mapping.ParamValueMode;

public class CallTest {
	public static void main(String[] args){
		String SQL1 ="{call addInteger(?,?,?)}";
		String SQL2 ="{?=call getAge(?,?)}";
		try{
			JdaSourceInfo dataSourceInfo = new JdbcSourceInfo(Link.getDriver(),Link.getURL(),Link.getUser(),Link.getPassword());
			JdaContainer container = new JdaContainerImpl(dataSourceInfo);
			ParamUnit property1 = container.createParamUnit("a",null);
			ParamUnit property2 = container.createParamUnit("b",null);
			ParamUnit property3 = container.createParamUnit("c",null);
			property3.setParamValueMode(ParamValueMode.OUT);
			property3.setParamColumnTypeName("INTEGER");

			ParamUnit property4 = container.createParamUnit("a",null);
			ParamUnit property5 = container.createParamUnit("b",null);
			ParamUnit property6 = container.createParamUnit("c",null);
			property6.setParamValueMode(ParamValueMode.OUT);
			property6.setParamColumnTypeName("INTEGER");

			ParamMap map1= container.createParamMap(CallParam.class,new ParamUnit[]{property1,property2,property3});
			ParamMap map2= container.createParamMap(CallParam.class,new ParamUnit[]{property6,property4,property5});
			
			container.registerStaticSql("call1",SQL1,map1);
			container.registerStaticSql("call2",SQL2,map2);
			
			JdaSession session = container.openSession();
			CallParam param = new CallParam(1,2);
			session.update("call1",param);
  		System.out.println("call1 Result: " + param.c);
  		
  		param.c =0;
  		session.update("call2",param);
  		System.out.println("call2 Result: " + param.c);
  		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
