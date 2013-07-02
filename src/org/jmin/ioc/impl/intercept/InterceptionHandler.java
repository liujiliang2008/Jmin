/*
 *Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.impl.intercept;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 拦截处理器
 * 
 * @author chris
 */
public class InterceptionHandler implements InvocationHandler {

	/**
	 * proxy object
	 */
	private Object bean;
	
	/**
	 * 拦截链
	 */
	private InterceptorChain chain;

	/**
	 * 构造函数
	 */
	public InterceptionHandler(Object bean) {
		this.bean = bean;
	}
	
	/**
	 * 拦截链
	 */
	public void setInterceptorChain(InterceptorChain chain){
		this.chain =chain;
	}

	/**
	 * 调用
	 */
	public Object invoke(Object proxy, Method method, Object[] args)throws Throwable {
		int hashCode = method.getName().hashCode();
		Class[] paramTypes =method.getParameterTypes();
		for (int i = 0; i < paramTypes.length; i++)
			hashCode ^= paramTypes[i].hashCode();
	
		return chain.intecept(bean,method,args,hashCode);
	}
}
