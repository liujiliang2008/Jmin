package org.jmin.ioc.impl.config.xml.element;

import java.util.List;

import org.jdom.Element;
import org.jmin.ioc.BeanElement;
import org.jmin.ioc.BeanElementException;
import org.jmin.ioc.BeanParameter;
import org.jmin.ioc.BeanParameterException;
import org.jmin.ioc.element.InvocationInterception;
import org.jmin.ioc.impl.config.xml.BeanXMLNodeUtil;
import org.jmin.ioc.impl.util.ClassUtil;
import org.jmin.ioc.impl.util.StringUtil;
import org.jmin.ioc.parameter.ClassParameter;
import org.jmin.ioc.parameter.ReferenceParameter;

/**
 * XML Template is below
 *
 *
 * <interception>
 *   <method-name>hello</method-name>
 *   <method-param-types>
 *    <method-param-type>int</method-param-type>
 *   </method-param-types>
 *   <interceptor class=""/>  // <interceptor ref=""/>
 * </interception>
 */

public class InvocationInterceptionFinder implements BeanElementXMLFinder{
	
	/**
	 * 查找Bean定义元素
	 */
	public BeanElement find(Element beanNode,String beanid,String spaceName,String file) throws BeanElementException{
		String methodName = BeanXMLNodeUtil.getChildext(beanNode,XMLEelementTags.Method_Name);
		try {
			if (StringUtil.isNull(methodName))
				throw new BeanElementException("Null intercepted method name of bean:" + beanid + " at file:" +file);

			Class[] parameters = getMethodParameterTypes(beanNode,methodName,beanid,file);
			InvocationInterception interception = new InvocationInterception(methodName,parameters);

			BeanParameter[] interceptorValues = getInterceptorValueMetas(beanNode,methodName,beanid,file);
			for(int i=0;i<interceptorValues.length;i++){
				if(interceptorValues[i] instanceof ClassParameter){
			    Class className =(Class)((ClassParameter)interceptorValues[i]).getParameterContent();
			    interception.addInterceptorClass(className);
			  }else if (interceptorValues[i]  instanceof ReferenceParameter){
			    Object refID = ((ReferenceParameter)interceptorValues[i]).getReferenceId();
			    interception.addInterceptorReference(refID);
			  }
			}
			return interception;
		} catch (BeanParameterException e) {
			throw new BeanElementException("Failed to find parameters for intercepted method:"+methodName + " of bean:" +beanid + " at file:" +file,e);
		}
	}
	
 
	/**
	 * 获取拦截器列表
	 */
  private static BeanParameter[] getInterceptorValueMetas(Element node,String methodname,String beanid,String file)throws BeanElementException{
    List interceptorMetaList = node.getChildren(XMLEelementTags.Interceptor);
    BeanParameter[] metas = new BeanParameter[interceptorMetaList.size()];
    for(int i=0;i<interceptorMetaList.size();i++){
      Element interceptorNode = (Element)interceptorMetaList.get(i);
      metas[i] = getInterceptorValueMeta(interceptorNode,methodname,beanid,file);
    }
    return metas;
  }
	
  /**
   * Find all parameter types from interception node
   */
  private static Class[] getMethodParameterTypes(Element node,String methodname,String beanid,String file)throws BeanElementException{
  	try {
			Element paramsNode = node.getChild(XMLEelementTags.Method_Param_Types);
			if(paramsNode != null){
			  List paramList = paramsNode.getChildren(XMLEelementTags.Method_Param_Type);
			  Class[] types = new Class[paramList.size()];
			  for(int i =0;i< paramList.size();i++){
			     Element paramNode = (Element)paramList.get(i);
			     types[i] = ClassUtil.loadClass(BeanXMLNodeUtil.getNodeText(paramNode));
			  }
			  return types;
			}else{
				return new Class[0];
			}
		} catch (ClassNotFoundException e) {
			 throw new BeanElementException("A method parameter type was not found for intercepted method: "+methodname + " of bean:" +beanid + " at file:" +file,e);
		}
  }
	
  /**
   * return Interceptor valueMeta
   */
  private static BeanParameter getInterceptorValueMeta(Element node,String methodname,String beanid,String file)throws BeanElementException{
    try {
			String clsname = BeanXMLNodeUtil.getValueByName(node,"class");
			String clsRef = BeanXMLNodeUtil.getValueByName(node,"ref");
			if(!StringUtil.isNull(clsname)) {
				return new ClassParameter(ClassUtil.loadClass(clsname));
			} else if (!StringUtil.isNull(clsRef)) {
				return new ReferenceParameter(clsRef);
			}else {
			  throw new BeanElementException("Missed interceptor class/refereceid for intercepted method: "+methodname + " of bean:" +beanid + " at file:" +file);
			}
		} catch (ClassNotFoundException e) {
			 throw new BeanElementException("Not found interceptor class for intercepted method: "+methodname + " of bean:" +beanid + " at file:" +file,e);
		}catch(BeanParameterException e){
			 throw new BeanElementException("Failded to find interceptor for intercepted method: "+methodname + " of bean:" +beanid + " at file:" +file,e);
		}
  }
}