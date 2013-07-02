/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.impl.config.xml.element;

import org.jdom.Element;
import org.jmin.ioc.BeanElement;
import org.jmin.ioc.BeanElementException;

/**
 * Bean Element Finder
 *
 * @author Chris Liao
 * @version 1.0
 */

public interface BeanElementXMLFinder{
	
	/**
	 * 查找Bean定义元素
	 */
	public BeanElement find(Element beanNode,String beanid,String spaceName,String file) throws BeanElementException;

}