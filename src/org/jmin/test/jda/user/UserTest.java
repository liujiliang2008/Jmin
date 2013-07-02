package org.jmin.test.jda.user;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.jmin.jda.JdaContainer;
import org.jmin.jda.JdaSession;
import org.jmin.jda.impl.config.SqlFileLoader;

public class UserTest {
	
	/**
	 * test method
	 */
	public static void main(String[] args){
		try {
			String file ="org/jmin/test/jda/user/jdbc.xml";
			SqlFileLoader loader = new SqlFileLoader();
			JdaContainer sessionFactory = loader.load(file);
			JdaSession session = sessionFactory.openSession();
		
			Map map = new HashMap();
			map.put("name","chris");
			map.put("sex","Man");
			System.out.println("list1: " + session.findOne("user.find4",map));

			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.SECOND,10);
		  for(int i=0;i<=1;i++){
		  	new SYNThread(session,calendar.getTime().getTime()).start();
		  }
		 
		  Object SYN = new Object();
		  synchronized (SYN) {
	     try {
	       SYN.wait();
	     }
	     catch (InterruptedException ex) {}
	   }
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}


class SYNThread extends Thread {
	private JdaSession session;
	private long runTime;
	public SYNThread(JdaSession session,long runTime) {
		this.session = session;
		this.runTime = runTime;
	}
	
	public void run() {
		try {
			long beginTime =System.currentTimeMillis();
			Thread.sleep(runTime-beginTime);
			
			session.insert("user.Insert1");
			session.insert("user.Insert2",new User("chris", "Man"));
			session.insert("user.Insert3",new User("chris2","Man"));
 	
			session.update("user.Update1",new User("chris2","Girl"));
			session.update("user.Update2",new User("liao","Girl"));
			
			session.delete("user.Delete1");
  		session.delete("user.Delete2",new String("chris"));
			session.delete("user.Delete3",new String("chris2"));
		
			System.out.println("Find Object:" + session.findOne("user.find1","Summer"));
			System.out.println("list1: " + session.findList("user.find3").size());
			System.out.println("list2: " + session.findList("user.find3",1,2));
			System.out.println("map1 size: " + session.findMap("user.find2","name").size());
			System.out.println("map2 size: " + session.findMap("user.find2","name","sex").size());
			System.out.println("page size: " + session.findPageList("user.find2",3).getTotalSize());
 	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
