/*
* Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.ioc.impl.config.xml.parameter;

import org.jdom.Element;
import org.jmin.ioc.BeanParameter;
import org.jmin.ioc.BeanParameterException;
 
/**
 * 参数查找
 * 
 * @author Chris Liao 
 */
public interface BeanParameterXMLFinder {

	/**
	 * 获取bean参数
	 */
	public BeanParameter find(String spaceName,Element node)throws BeanParameterException;

}
