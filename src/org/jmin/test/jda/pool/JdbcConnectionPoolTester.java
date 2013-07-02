/*
* Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.test.jda.pool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import org.jmin.jda.impl.connection.ConnectionPool;
import org.jmin.jda.impl.connection.ConnectionPoolInfo;
import org.jmin.jda.impl.connection.ConnectionPoolInfoFactory;

/**
 * Jdbc连接池测试
 * 
 * @author Chris
 * @version 1.0
 */
public class JdbcConnectionPoolTester {
	
	/**
	 * 测试方法
	 */
	public static void main(String[] args)throws SQLException{
		String driver ="sun.jdbc.odbc.JdbcOdbcDriver";							
		String url="jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)}; DBQ=D:\\dev\\projects\\jmin\\src\\org\\jmin\\test\\jda\\test.mdb";
		String user="";
		String password="";
		

		ConnectionPoolInfo info = ConnectionPoolInfoFactory.createJdbcPoolInfo(driver, url, user,password);
		info.setConnectionMaxSize(10);
		ConnectionPool pool = new ConnectionPool(info);
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.SECOND,2);
		for (int i = 0; i < 1000; i++) {
			new ConThread(calendar.getTime().getTime(),pool).start();
		}
	 }
	}
	
	class ConThread extends Thread {
	long runTime;
	ConnectionPool pool;

	public ConThread(long runTime,ConnectionPool pool) {
		this.runTime =runTime;
		this.pool = pool;
	}


	public void run() {
		Connection con = null;
		PreparedStatement st = null;
		ResultSet result = null;
		try {
			long beginTime =System.currentTimeMillis();
			Thread.sleep(runTime-beginTime);
		  beginTime = System.currentTimeMillis();
			con = pool.borrowConnection();
			long endTime = System.currentTimeMillis();
			System.out.println("Thread[" + Thread.currentThread().getName() +"] got a connection take time: " + (endTime-beginTime)/1000 + "s");
			
			String s= "select  * from userinfo";
			
			st = con.prepareStatement(s);
	
			result = st.executeQuery();
			while (result.next()) {
				System.out.println(result.getString(1));
			}
		}catch(InterruptedException e){
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(result!=null)
					result.close();
				if(st!=null)
					st.close();
				if(con!=null)
					con.close();
			} catch (SQLException e) {
			}
		}
	}
}
