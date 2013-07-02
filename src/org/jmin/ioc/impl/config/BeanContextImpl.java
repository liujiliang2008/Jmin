/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.impl.config;

import java.io.File;
import java.net.URL;
import java.util.Map;

import org.jmin.ioc.BeanContainer;
import org.jmin.ioc.BeanContext;
import org.jmin.ioc.BeanException;
import org.jmin.ioc.impl.MinBeanContainer;
import org.jmin.ioc.impl.config.xml.BeanXMLFileLoader;
 
/**
 * IOC Bean context
 *
 * @author Chris Liao
 * @version 1.0
 */

public class BeanContextImpl extends BeanContext{
	
  /**
   * iocContainer
   */
  private BeanContainer iocContainer = new MinBeanContainer();
  
  /**
   * constructor
   */
  public BeanContextImpl()throws BeanException{
  	BeanXMLFileLoader.loadDefaultFile(iocContainer);
  }
 
  /**
	 * constructor
	 */
	public BeanContextImpl(File file) throws BeanException {
		this(new File[] {file });
	}

	/**
	 * constructor
	 */
	public BeanContextImpl(File[] files) throws BeanException {
		BeanXMLFileLoader.loadIocFile(iocContainer, files);
	}

	/**
	 * constructor
	 */
	public BeanContextImpl(String filename) throws BeanException {
		this(new String[] { filename });
	}

	/**
	 * constructor
	 */
	public BeanContextImpl(String[] filenames) throws BeanException {
		BeanXMLFileLoader.loadIocFile(iocContainer, filenames);
	}

	/**
	 * constructor
	 */
	public BeanContextImpl(URL fileURL) throws BeanException {
		this(new URL[] { fileURL });
	}

	/**
	 * constructor
	 */
	public BeanContextImpl(URL[] fileURLs) throws BeanException {
		BeanXMLFileLoader.loadIocFile(iocContainer, fileURLs);
	}
	
	
	
 
	/**
	 * containsID
	 */
	public boolean containsId(Object key) throws BeanException {
		return iocContainer.containsId(key);
	}

	/**
	 * return bean
	 */
	public Object getBean(Object key) throws BeanException {
		return iocContainer.getBean(key);
	}

	/**
	 * return bean
	 */
	public Object getBeanOfType(Class key) throws BeanException {
		return iocContainer.getBeanOfType(key);
	}

	/**
	 * return bean
	 */
	public Map getBeansOfType(Class key) throws BeanException {
		return iocContainer.getBeansOfType(key);
	}
}