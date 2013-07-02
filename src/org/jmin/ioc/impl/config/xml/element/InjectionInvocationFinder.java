package org.jmin.ioc.impl.config.xml.element;

import java.util.List;

import org.jdom.Element;
import org.jmin.ioc.BeanElement;
import org.jmin.ioc.BeanElementException;
import org.jmin.ioc.BeanParameter;
import org.jmin.ioc.BeanParameterException;
import org.jmin.ioc.element.InjectionInvocation;
import org.jmin.ioc.impl.config.xml.BeanXMLNodeUtil;
import org.jmin.ioc.impl.config.xml.parameter.BeanParameterXMLFactory;
import org.jmin.ioc.impl.util.StringUtil;

public class InjectionInvocationFinder implements  BeanElementXMLFinder{

	/**
	 * 查找Bean定义元素
	 */
	public BeanElement find(Element beanNode,String beanid,String spaceName,String file) throws BeanElementException{
		String methodName = BeanXMLNodeUtil.getChildext(beanNode,XMLEelementTags.Method_Name);
		
		try {
			if (StringUtil.isNull(methodName))
				throw new BeanElementException("Null method name of bean:"+beanid +" at file:"+file);
			
			Element paramsNode = beanNode.getChild(XMLEelementTags.Method_Param_Values);
			if(paramsNode == null) 
				throw new BeanElementException("Missed <method-param-values>node for method["+methodName+"]of bean:"+beanid +" at file:"+file);
			
			List paramValueList = paramsNode.getChildren(XMLEelementTags.Method_Param_Value);
			BeanParameter[] parameters = new 	BeanParameter[paramValueList.size()];
			for (int i = 0; i < parameters.length; i++) {
				Element argNode = (Element) paramValueList.get(i);
				parameters[i] = BeanParameterXMLFactory.find(spaceName,argNode);
			}
			return new InjectionInvocation(methodName,parameters);
		} catch (BeanParameterException e) {
			throw new BeanElementException("Failed to find parameters for method["+methodName+"]of bean:"+beanid +" at file:"+file,e);
		}
	}
}