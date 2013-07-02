/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.impl.config.xml.parameter;

import org.jdom.Element;
import org.jmin.ioc.BeanParameter;
import org.jmin.ioc.BeanParameterException;
import org.jmin.ioc.impl.config.xml.BeanXMLNodeUtil;
import org.jmin.ioc.impl.util.ClassUtil;
import org.jmin.ioc.impl.util.StringUtil;
import org.jmin.ioc.parameter.ClassParameter;

/**
 *
 * @author Chris Liao
 * @version 1.0
 */

public final class ClassNameParamFinder implements BeanParameterXMLFinder{

	/**
	 * 获得参数
	 */
	public BeanParameter find(String spaceName,Element node)throws BeanParameterException{
		try {
			String className = BeanXMLNodeUtil.getValueByName(node,"class");
			if(!StringUtil.isNull(className))
				return new ClassParameter(ClassUtil.loadClass(className));
			else
				throw new BeanParameterException("Null value at class parameter node:<"+node.getName()+">");
		} catch (ClassNotFoundException e) {
			throw new BeanParameterException(e);
		}
	}
}