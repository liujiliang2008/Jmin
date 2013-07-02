/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.app.web;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jmin.jda.JdaContainer;
import org.jmin.jda.JdaSession;
import org.jmin.log.Logger;

/**
 * Session辅助类
 * 
 * @author Chris Liao
 */

public class DaoTemplateUtil {
	
	/**
	 * 存放Web Session对应的dao session
	 */
	private static Map userSessionMap = new HashMap();

	/**
	 * Session作用域
	 */
	private static ThreadLocal userThreadLocal = new ThreadLocal();
	
	/**
	 * webRequest作用域
	 */
	private static ThreadLocal webRequestLocal = new ThreadLocal();
	
	/**
	 * logger
	 */
	private static Logger logger = Logger.getLogger(DaoTemplateUtil.class);
	
	
	/**
	 *将当前访问的WebRequest放入ThreadLocal
	 */
	public static void putHttpServletRequest(HttpServletRequest request){
		webRequestLocal.set(request);
	}
	
	/**
	 * 获取Session
	 */
	public static JdaSession getSession(JdaContainer container,DaoTemplate template)throws SQLException{
		Integer templateHashCode = new Integer(template.hashCode());
		HttpServletRequest request =(HttpServletRequest)webRequestLocal.get();

		if(request!=null){
			HttpSession webSession =(HttpSession)request.getSession(true);
			String webSessionId = webSession.getId();
		 
			Map daoSessionMap = (Map)userSessionMap.get(webSessionId);
			if(daoSessionMap==null){
				daoSessionMap = new HashMap();
				userSessionMap.put(webSessionId,daoSessionMap);
			}
			
			JdaSession session =(JdaSession)daoSessionMap.get(templateHashCode);
			if(session== null || session.isClosed()){
				session = container.openSession();
				daoSessionMap.put(templateHashCode,session);
			}
			return session;
		}else{
			Map userLocalMap = (Map)userThreadLocal.get();
			if(userLocalMap==null){
				userLocalMap = new HashMap();
				userThreadLocal.set(userLocalMap);
			}
			
			JdaSession session = (JdaSession)userLocalMap.get(templateHashCode);
			if(session== null || session.isClosed()){
				session = container.openSession();
				userLocalMap.put(templateHashCode,session);
			}
			return session;
		}
	}

	
	/**
	 * 清理dao的Session
	 */
	public static void removeDaoSession(String webSessionId){
		Map daoSessionMap = (Map)userSessionMap.remove(webSessionId);
		if(daoSessionMap!=null){
			Iterator itor = daoSessionMap.values().iterator();
			while(itor.hasNext()){
				JdaSession session =(JdaSession)itor.next();
				try{
					if(session!=null && !session.isClosed() && session.isInTransaction()){
						try{
		  				session.rollbackTransaction();
						} catch (Throwable e) {
						}
						session.close();
					}
			 }catch (SQLException e) {
				logger.error(e);
			 } 
			}
		}
	}
}
