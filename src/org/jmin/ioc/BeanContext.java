/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc;

import java.util.Map;

/**
 * Bean Context
 *
 * @author Chris Liao
 * @version 1.0
 */

public abstract class BeanContext {
	
	//context file map key 
	public final static String BEAN_CONTEXT_FILE = "bean.context.file";
	
	//context factory  map key 
	public final static String BEAN_CONTEXT_FACTORY = "bean.context.factory";

	//context init method,
	public static BeanContext initContext(Map initMap)throws BeanException{
		try {
			if(initMap==null)
				throw new NullPointerException("bean init context map can't be null");
			String factoryName = (String)initMap.get(BEAN_CONTEXT_FACTORY);
			if(factoryName==null)
				throw new BeanException("Not found context factoy["+BEAN_CONTEXT_FACTORY+"]in map");
			
			Class factoryClass = Class.forName(factoryName);
			BeanContextFactory factory = (BeanContextFactory)factoryClass.newInstance();
			return factory.createContext(initMap);
		} catch (BeanException e) {
     throw e;
		} catch (Exception e) {
			throw new BeanException(e);
		}
	}
	
  /**
   * contains beanID
   */
  public abstract boolean containsId(Object id)throws BeanException;

  /**
   * Find a bean instance from container by a id.
   * If not found, then return null.
   */
  public abstract Object getBean(Object id)throws BeanException;

  /**
   * Find a bean instance with a class. If not found, then return null.
   */
  public abstract Object getBeanOfType(Class cls)throws BeanException;

  /**
   * Find all bean instance map
   */
  public abstract Map getBeansOfType(Class cls)throws BeanException;

}
