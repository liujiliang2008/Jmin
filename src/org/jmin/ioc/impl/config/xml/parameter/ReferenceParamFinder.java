/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.impl.config.xml.parameter;

import org.jdom.Attribute;
import org.jdom.Element;
import org.jmin.ioc.BeanParameter;
import org.jmin.ioc.BeanParameterException;
import org.jmin.ioc.impl.util.StringUtil;
import org.jmin.ioc.parameter.ReferenceParameter;

/**
 * 引用参数
 *
 * @author Chris Liao
 * @version 1.0
 */

public final class ReferenceParamFinder implements BeanParameterXMLFinder{
	
	/**
	 * 查找引用参数
	 */
	public BeanParameter find(String spaceName,Element node)throws BeanParameterException{
    String refid = null;
    if("ref".equalsIgnoreCase(node.getName())){
    	//有可能出现三种编写方式
    	Attribute beanRefAttr = node.getAttribute("bean");
    	Attribute localRefAttr = node.getAttribute("local");
    	Attribute valueRefAttr = node.getAttribute("value");
    	
    	if(beanRefAttr!=null)
    		refid = beanRefAttr.getValue();
    	else if(localRefAttr!=null)
    		refid = localRefAttr.getValue();
    	else if(valueRefAttr!=null)
    		refid = valueRefAttr.getValue();
    }else if(node.getAttribute("ref")!=null ){
    	refid = node.getAttributeValue("ref");
    } 
   
  	if(StringUtil.isNull(refid))
  		throw new BeanParameterException("Null value at reference-id parameter node:<"+node.getName()+">");
  	else if(!StringUtil.isNull(spaceName))
  		refid = spaceName +"."+ refid.trim();
   
		return new ReferenceParameter(refid);
	}
}