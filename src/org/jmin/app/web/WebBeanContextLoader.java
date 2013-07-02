/*
* Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.app.web;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.jmin.ioc.BeanException;
import org.jmin.jec.impl.xml.EventCenterBean;
import org.jmin.job.xml.JobScheduleDefinition;
import org.jmin.log.Logger;

/**
 * logic context loader
 *
 * @version 1.0
 */

public class WebBeanContextLoader extends HttpServlet{
	
	/**
	 * logger
	 */
	private static Logger logger =Logger.getLogger(WebBeanContextLoader.class);
	
	/**
	 * 配置文件的位置
	 */
	public static final String ContextFile="contextFile";
	
	/**
	 * 配置Ioc上下文Context的名字
	 */
	public static final String ContextName="contextName";
	
	
	/**
	 * Dao模板Id
	 */
	public static final String DaoTemplate_Id="daoTemplateId";
	
	/**
	 * Job排期Id
	 */
	public static final String JobScheduler_Id="jobSchedulerId";
	
	/**
	 * Event事件发布者Id
	 */
	public static final String EventCenter_Id="eventCenterId";
	
	
  /**
   * init servlet
   */
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    WebBeanContext context = this.loadBeanFile(config);
    this.saveBeanContext(config,context);
    this.afterLoadBeanFile(config,context);
 
  }
 
  //在文件装载后执行
  protected WebBeanContext loadBeanFile(ServletConfig config)throws ServletException{
  	try {
  		String contextFile = this.getServletConfig().getInitParameter(ContextFile);
  	  if(this.isNull(contextFile)) 
  	     throw new NullPointerException("Please config init-param with name 'contextFile'");
  	  
  	  String[] filenames = this.getContextFilenames(config,contextFile);
			return new WebBeanContext(filenames);
		} catch (BeanException e) {
			logger.error("Failed to load Ioc files",e);
			throw new ServletException(e);
		}
  }
  
  //保存BeanContext
  protected void saveBeanContext(ServletConfig config,WebBeanContext beanContext)throws ServletException{
  	String contextName = this.getServletConfig().getInitParameter(ContextName);
  	if(this.isNull(contextName)){
  	 WebGlobalHolder.defaultWebBeanContext=beanContext;
		 WebBeanContext.putDefaultLogicContext(beanContext);
  	}else{
		 WebBeanContext.putLogicContext(contextName.trim(),beanContext);
  	}
  }
   
  /**
   * 获取IOC上下闻
   */
  private String[] getContextFilenames(ServletConfig config,String contextFile){
    List fileList = new ArrayList();
  	StringTokenizer token = new StringTokenizer(contextFile, ",");
    while(token.hasMoreTokens()) {
    	String item = token.nextToken().trim();
    	String fileName = config.getServletContext().getRealPath(item);
    	File file = new File(fileName);
    	if(file.exists()&& file.isFile())
    	   fileList.add(fileName);
    	else
    	   fileList.add(item);
    }
   return (String[])fileList.toArray(new String[0]);
  }
  
  /**
   * 在BeanContext执行之后做一些操作
   */
  protected void afterLoadBeanFile(ServletConfig config,WebBeanContext beanContext)throws ServletException{
  	 String daoIocIds = this.getServletConfig().getInitParameter(DaoTemplate_Id);
     if(daoIocIds!=null &&daoIocIds.trim().length()>0){
    	this.loadDaoTemplate(config,beanContext);
     }
  	
   	 String jobSchedulerId = this.getServletConfig().getInitParameter(JobScheduler_Id);
     if(jobSchedulerId!=null && jobSchedulerId.trim().length()>0){
    	this.loadJobScheduler(config,beanContext);
     }
     
   	 String eventPublisherId = this.getServletConfig().getInitParameter(EventCenter_Id);
     if(eventPublisherId!=null && eventPublisherId.trim().length()>0){
    	this.loadEventPublisher(config,beanContext);
     }
  }
  
  /**
   * 装载数据库模
   */
  private void loadDaoTemplate(ServletConfig config,WebBeanContext beanContext)throws ServletException{
 	 String daoIocIds = this.getServletConfig().getInitParameter(DaoTemplate_Id);
   if(daoIocIds!=null &&daoIocIds.trim().length()>0){
  	 try {
  			StringTokenizer token = new StringTokenizer(daoIocIds,",");
  			while(token.hasMoreElements()){
  				String templateId = (String)token.nextElement();
  				 DaoTemplate daoTemplate = (DaoTemplate)beanContext.getBean(templateId.trim());
  				 if(daoTemplate!=null){
  					 System.out.println();
  					 logger.info("Begin to load sql mapping files by bean["+templateId.trim()+"]");
  					 daoTemplate.loadSource();
  					 logger.info("End to load sql mapping files by bean["+templateId.trim()+"]");
  				 }
  			}
		} catch (Throwable e){
			logger.error("Failed to load load sql mapping files",e);
		  throw new ServletException(e);
		}finally{
			System.gc();
		}
	  }
  }
  
  /**
   * 装载Job排期
   */
  private void loadJobScheduler(ServletConfig config,WebBeanContext beanContext)throws ServletException{
  	try{
  		String scheduleID = this.getServletConfig().getInitParameter(JobScheduler_Id);
  		if(scheduleID!=null && scheduleID.trim().length()>0){
	  		StringTokenizer token = new StringTokenizer(scheduleID,",");
	 			while(token.hasMoreElements()){
					String iocId =(String)token.nextElement();
					JobScheduleDefinition jobXmlScheduler =((JobScheduleDefinition)beanContext.getBean(iocId.trim()));
					if(jobXmlScheduler!=null){
					 System.out.println();
					 logger.info("Begin to load job info by bean["+iocId.trim()+"]");
					 new StartThread(jobXmlScheduler).start();
					 logger.info("Begin to load job info by bean["+iocId.trim()+"]");
					}
	 			}
  		} 
     }catch(Exception e){
       throw new ServletException(e.getMessage(),e);
     }
  }
  
  class StartThread extends Thread{
	  JobScheduleDefinition runner;
	  public StartThread(JobScheduleDefinition runner){
		  this.runner = runner;
	  }
	  
	  public void run(){
		try {
			runner.run();
		} catch (RuntimeException e) {
		   e.printStackTrace();
		}
	  }
  }
  
  /**
   * 装载事件发布
   */
  private void loadEventPublisher(ServletConfig config,WebBeanContext beanContext)throws ServletException{
  	try{
  		String eventCenterid = this.getServletConfig().getInitParameter(EventCenter_Id);
  		if(eventCenterid!=null && eventCenterid.trim().length()>0){
       EventCenterBean centerInfo =((EventCenterBean)beanContext.getBean(eventCenterid));
			 if(centerInfo!=null){
				 System.out.println();
				 logger.info("Begin to load event info by bean["+eventCenterid+"]");
				 WebGlobalHolder.defaultEventPublisher=centerInfo.load();
				 logger.info("Begin to load event info by bean["+eventCenterid+"]");
			 }
  		} 
     }catch(Exception e){
       throw new ServletException(e.getMessage(),e);
     }
  }
  
  /**
	 * 判断字符是否为空
	 */
	private boolean isNull(String value) {
		return value == null || value.trim().length()==0;
	}
}