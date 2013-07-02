/*
*Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.ioc.impl.intercept;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jmin.ioc.BeanInterceptor;

/**
 * AOP调用的拦截链
 *
 * @author chris liao
 */

public class InterceptorChain {
	
	/**
	 * 拦截链表，存放interceptor 到Node的映射
	 */
	private Map methodIntercepMap;

	/**
	 * 构造函数
	 */
	public InterceptorChain(){
		this.methodIntercepMap = new HashMap();
	}
	
	/**
	 * 增加一个拦截器
	 */
	public void addMethodInterceptor(String methodName,Class[] paramTypes,BeanInterceptor interceptor){
		int hashCode = methodName.hashCode();
		for (int i = 0; i < paramTypes.length; i++)
			hashCode ^= paramTypes[i].hashCode();

		Integer hashCodeKey = new Integer(hashCode);
		List interceporList = (List) methodIntercepMap.get(hashCodeKey);
		if (interceporList == null) {
			interceporList = new ArrayList();
			methodIntercepMap.put(hashCodeKey, interceporList);
		}
		interceporList.add(new InteceptionNode(interceptor));
	}

	/**
	 * 增加一个拦截器
	 */
	public void removeMethodInterceptor(String methodName,Class[] paramTypes,BeanInterceptor interceptor){
		int hashCode = methodName.hashCode();
		for (int i = 0; i < paramTypes.length; i++)
			hashCode ^= paramTypes[i].hashCode();
		Integer hashCodeKey = new Integer(hashCode);
		List interceporList = (List) methodIntercepMap.get(hashCodeKey);
		if (interceporList != null) {
			interceporList.remove(interceptor);
		}
	}

  /**
   * intercept method
   */
  public Object intecept(Object bean,Method method,Object[] params,int hashCode)throws Throwable {
  	Integer hashCodeKey = new Integer(hashCode);
  	List interceporList = (List) methodIntercepMap.get(hashCodeKey);
  	try{
	  	if(interceporList==null || interceporList.isEmpty()){
	  		return method.invoke(bean,params);
	  	}else{
	  		int index = 0;
	  		InteceptionNode node =(InteceptionNode)interceporList.get(index);
	  		return node.invoke(bean,method,params,interceporList,index);
	  	}
  	}catch(InvocationTargetException e){
  		throw e.getTargetException();
  	}
  }
}
