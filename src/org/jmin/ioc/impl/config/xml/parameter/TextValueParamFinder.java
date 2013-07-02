/*
* Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.ioc.impl.config.xml.parameter;

import org.jdom.Element;
import org.jmin.ioc.BeanParameter;
import org.jmin.ioc.BeanParameterException;
import org.jmin.ioc.impl.config.xml.BeanXMLNodeUtil;
import org.jmin.ioc.impl.util.StringUtil;
import org.jmin.ioc.parameter.InstanceParameter;

/**
 * 文本参数
 *
 * @author Chris Liao
 * @version 1.0
 */
public class TextValueParamFinder implements BeanParameterXMLFinder{
	
	/**
	 * 获得参数
	 */
	public BeanParameter find(String spaceName,Element node)throws BeanParameterException{
		String text = BeanXMLNodeUtil.getValueByName(node,"value");
		if(text!=null && text.trim().length()>0){
			return new InstanceParameter(StringUtil.getFilterValue(text));//防止参数值里面带有系统的环境变量 ${XX},#{XX}
		}else if(text!=null){
			return new InstanceParameter(text);
		}else{
			throw new BeanParameterException("Null value at text parameter node:<"+node.getName()+">");
		}
	}
}
