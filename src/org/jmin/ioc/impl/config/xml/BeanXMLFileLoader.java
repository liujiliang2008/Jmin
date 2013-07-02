/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.impl.config.xml;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jmin.ioc.BeanContainer;
import org.jmin.ioc.BeanElement;
import org.jmin.ioc.BeanException;
import org.jmin.ioc.impl.config.xml.element.BeanElementXMLFactory;
import org.jmin.ioc.impl.exception.BeanClassNotFoundException;
import org.jmin.ioc.impl.exception.BeanConfiguerationFileException;
import org.jmin.ioc.impl.exception.BeanDefinitionException;
import org.jmin.ioc.impl.exception.BeanIdDuplicateRegisterException;
import org.jmin.ioc.impl.util.ClassUtil;
import org.jmin.ioc.impl.util.StringUtil;
import org.jmin.log.Logger;

/**
 * 部署文件Loader
 * 
 * @author chris
 */

public class BeanXMLFileLoader {
	
	/**
	 * 默认装载的XML配置文件
	 */
	private static final String IOC_FILE_NAME = "ioc";

	/**
	 * 默认装载的XML配置文件
	 */
	private static final String DEFAULT_IOC_FILE_NAME = "/ioc.xml";
	
	/**
	* 日记打印
	*/
	private static final Logger log = Logger.getLogger(BeanXMLFileLoader.class);
	

	/**
	 * 装载默认文件
	 */
	public static void loadDefaultFile(BeanContainer container) throws BeanException {
		try {
			URL fileURL = getDefaultIocFile();
			loadIocFile(container, new URL[]{fileURL});
		} catch (BeanException e) {
		}
	}

	/**
	 * 装载默认文件
	 */
	public static void loadIocFile(BeanContainer container, URL fileURL)throws BeanException {
		if(fileURL == null)
			throw new BeanConfiguerationFileException("File url can't be null");
		loadIocFile(container,new URL[]{fileURL});
	}

	/**
	 * 装载默认文件
	 */
	public static void loadIocFile(BeanContainer container, File file)throws BeanException {
		if(file == null)
			throw new BeanConfiguerationFileException("File can't be null");
		loadIocFile(container,new File[]{file});
	}
	
	/**
	 * 装载默认文件
	 */
	public static void loadIocFile(BeanContainer container, String filename)throws BeanException {
		if(StringUtil.isNull(filename))
			throw new BeanConfiguerationFileException("File name can't be null");
		loadIocFile(container,new String[]{filename});
	}

	/**
	 * 装载默认文件
	 */
	public static void loadIocFile(BeanContainer container, File[] files)throws BeanException {
		if(files ==null)
		 throw new BeanConfiguerationFileException("File array can't be null");
		for(int i=0;i<files.length;i++){
			if(files[i]!=null)
				try {
					load(container,files[i].toURL());
				} catch (MalformedURLException e) {
					throw new BeanConfiguerationFileException("File exception at file:"+files[i].getName(),e);
				} 
		}
	}

	/**
	 * 装载默认文件
	 */
	public static void loadIocFile(BeanContainer container, URL[] fileURLs)throws BeanException {
		if(fileURLs ==null)
			 throw new BeanConfiguerationFileException("File url array can't be null");
		for(int i=0;i<fileURLs.length;i++){
			if(fileURLs[i]!=null)
			load(container,fileURLs[i]);
		}
	}
	
	/**
	 * 装载默认文件
	 */
	public static void loadIocFile(BeanContainer container, String[] filenames)throws BeanException {
		if(filenames ==null)
			 throw new BeanConfiguerationFileException("File name array can't be null");
		
		for(int i=0;i<filenames.length;i++){
			load(container,BeanXMLFileFinder.find(null,filenames[i]));
		}
	}

	/**
	 * 装载文件
	 */
	public static void load(BeanContainer iocContainer,URL url)throws BeanException{
		try {
			BeanXMLFileFinder.validateXMLFile(url);
			String filename=url.getFile();
			String fileFolder = BeanXMLFileFinder.getFilePath(url.getPath());
			
			Document document = new SAXBuilder().build(url);
			Element rootElement = document.getRootElement();
			BeanXMLFileFinder.validateXMLRoot(rootElement,BeanXMLNodeTags.BEANS,url.getPath());
			String spacename = rootElement.getAttributeValue("namespace");
	
			log.debug("Begin to load ioc xml file:" + filename);
			List annotationList=rootElement.getChildren(BeanXMLNodeTags.ANNOTATION);
			List includeList = rootElement.getChildren(BeanXMLNodeTags.INCLUDE);
			
			resolveAnnotation(fileFolder,annotationList,iocContainer);
			resolveIncludeFile(fileFolder,includeList,iocContainer);
			List beanElementList = rootElement.getChildren(BeanXMLNodeTags.BEAN);
			resolveBeanList(filename,spacename,beanElementList,iocContainer);
			log.debug("End to load ioc xml file:" + filename);
		} catch (IOException e) {
			//log.error("Failed to load ioc xml file:"+url.getFile(),e);
			throw new BeanConfiguerationFileException(e);
		} catch (JDOMException e) {
			//log.error("Failed to load ioc xml file:"+url.getFile(),e);
			throw new BeanConfiguerationFileException(e);
		} catch (BeanException e) {
			//log.error("Failed to load ioc xml file:"+url.getFile(),e);
			throw e;
	  }
	}
	
	
	
	/**
	 * 解析annotation
	 */
	private static void resolveAnnotation(String parentFolder,List annotationList,BeanContainer iocContainer)throws BeanException{
   Iterator itor = annotationList.iterator();
		while(itor.hasNext()){
			Element element =(Element)itor.next();
			String basePackage = BeanXMLNodeUtil.getValueByName(element,"base-package");
			String[]folders = StringUtil.split(basePackage,",");
	    for(int i=0;i<folders.length;i++){
	    	BeanClassScanner.scan(folders[i],iocContainer);
	    }
		}
	}
	
	/**
	 * 解析Include文件
	 */
	private static void resolveIncludeFile(String parentFolder,List includeList,BeanContainer iocContainer)throws BeanException{
		URL importFileURL = null;
		Iterator itor = includeList.iterator();
		while(itor.hasNext()){
			Element element =(Element)itor.next();
			String includeFile = BeanXMLNodeUtil.getValueByName(element,"file");
			if(includeFile.toLowerCase().startsWith("classpath:")){
				includeFile = includeFile.substring(10);
				importFileURL = BeanXMLFileLoader.class.getClassLoader().getResource(includeFile);
			}else {
				importFileURL = BeanXMLFileFinder.find(parentFolder,includeFile);
			}
			
			if(importFileURL ==null)
				throw new BeanConfiguerationFileException("Not found include file:" + includeFile);
			
			 load(iocContainer,importFileURL);
		}
	}
	
	/**
	 * 解析Bean列表
	 */
	private static void resolveBeanList(String filename,String spacename,List elementList,BeanContainer iocContainer)throws BeanException{
		Iterator itor = elementList.iterator();
		while(itor.hasNext()){
			Element element =(Element)itor.next();
			String beanid = BeanXMLNodeUtil.getValueByName(element,"id");
			String className=BeanXMLNodeUtil.getValueByName(element,"class");
			if(StringUtil.isNull(beanid))
				throw new BeanDefinitionException("Missed bean id at file:"+ filename);
			if(iocContainer.containsId(beanid))
				throw new BeanIdDuplicateRegisterException("Duplicate Registered with bean id:" +beanid +" at file:"+ filename);
			if(StringUtil.isNull(className))
				throw new BeanDefinitionException("Missed bean class with bean id:"+beanid+" at file:"+ filename);
			if(!StringUtil.isNull(spacename))
				beanid = spacename.trim()+"."+beanid;
		
			try {
				Class beanClas = ClassUtil.loadClass(className);
				BeanElement[] beanElements = BeanElementXMLFactory.find(element,beanid,spacename,filename);
				iocContainer.registerClass(beanid,beanClas,beanElements);
			} catch (ClassNotFoundException e) {
				throw new BeanClassNotFoundException("Not found bean class:"+ className +" with id: "+beanid+" at file:"+filename);
			}
		}
	}


	/**
	 * 获得默认配置文件的URL
	 */
	private static URL getDefaultIocFile()throws BeanException{
		URL url = BeanXMLFileLoader.class.getResource(DEFAULT_IOC_FILE_NAME);
		if(url == null){
			String filename = System.getProperty(IOC_FILE_NAME);
			if(!StringUtil.isNull(filename))
				url = BeanXMLFileFinder.find(null,filename);
			else
			 throw new BeanConfiguerationFileException("Not found default ioc file:" + DEFAULT_IOC_FILE_NAME);
		}
		
		if(url != null)
			return url;
		else
			throw new BeanConfiguerationFileException("Not found default ioc file:" + DEFAULT_IOC_FILE_NAME);
	}
}
