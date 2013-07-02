/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc;

import java.util.Map;

/**
 * Bean Context Factory
 *
 * @author Chris Liao
 * @version 1.0
 */
public interface BeanContextFactory {
 
	/**
	 * Create Bean context
	 */
	public BeanContext createContext(Map initMap)throws BeanException;
	
}
