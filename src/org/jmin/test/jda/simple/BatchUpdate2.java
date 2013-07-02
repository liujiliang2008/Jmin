package org.jmin.test.jda.simple;

import java.sql.SQLException;
import java.util.Calendar;

import org.jmin.jda.JdaContainer;
import org.jmin.jda.JdaSession;
import org.jmin.jda.JdaSourceInfo;
import org.jmin.jda.JdbcSourceInfo;
import org.jmin.jda.impl.JdaContainerImpl;
import org.jmin.jda.mapping.ParamMap;
import org.jmin.jda.mapping.ParamUnit;

public class BatchUpdate2 {
	
	public static void main(String[] args) {
		String SQL1 = "insert into USERINFO(name,sex)values(?,?)";
		String SQL2 = "update USERINFO set sex=? where name=?";
		try {
			JdaSourceInfo dataSourceInfo = new JdbcSourceInfo(Link.getDriver(),Link.getURL(),Link.getUser(),Link.getPassword());
			JdaContainer container = new JdaContainerImpl(dataSourceInfo);
			ParamUnit property1 = container.createParamUnit("name",null);
			ParamUnit property2 = container.createParamUnit("sex",null);
			
			ParamMap map= container.createParamMap(User.class,new ParamUnit[]{property1,property2});
			container.registerStaticSql("Insert",SQL1,map);
			container.registerStaticSql("update",SQL2,map);

			/**
			 * 计算时间，让所有Thread一起访问
			 */
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.SECOND,20);
		  for(int i=0;i<=10;i++){
		  	new SYNThread(container.openSession(),calendar.getTime().getTime()).start();
		  }
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	static class SYNThread extends Thread {
		JdaSession session;
		private long runTime;
	
		public SYNThread(JdaSession session,long runTime) {
			this.session = session;
			this.runTime = runTime;
		}

		public void run() {
			try {
				long beginTime =System.currentTimeMillis();
				Thread.sleep(runTime-beginTime);
				session.beginTransaction();
				session.startBatch();
				User userInfo = null;
				for (int i = 0; i < 10; i++) {
					if (i % 2 == 0) {
						userInfo = new User("emily", "Girl");
					} else {
						userInfo = new User("liao", "Man");
					}
					session.insert("Insert", userInfo);
				}

				for (int i = 0; i < 100; i++) {
					if (i % 2 == 0) {
						userInfo = new User("emily", "Girl1");
					} else {
						userInfo = new User("liao", "Man1");
					}
					session.update("update", userInfo);
				}
				int rows = session.executeBatch();
				session.commitTransaction();
				System.out.println("Batch rows: " + rows);
			} catch (Exception e) {
				try {
					if(session.isInTransaction())
						session.rollbackTransaction();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
		}
	}
}
