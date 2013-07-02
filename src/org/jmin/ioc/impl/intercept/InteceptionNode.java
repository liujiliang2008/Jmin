/*
*Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.impl.intercept;

import java.lang.reflect.Method;
import java.util.List;

import org.jmin.ioc.BeanInterceptor;

/**
 * 拦截炼中的拦截节点
 * 
 * @author Chris
 */
public class InteceptionNode {
	
	/**
	 * 当前需要执行的拦截器
	 */
	private BeanInterceptor inteceptor;
	
	/**
	 * 构造方法
	 */
	public InteceptionNode(BeanInterceptor inteceptor){
		this.inteceptor = inteceptor;
	}
	
	/**
	 * 被拦截的方法名
	 */
	public BeanInterceptor getInteceptor() {
		return inteceptor;
	}
	
	/**
	 * intercept method
	 */
  public Object invoke(Object bean,Method method,Object[] params,List interceporList,int index)throws Throwable {
  	Object result = null;
  	Throwable throwable = null;
  	try{
  	 	inteceptor.before(bean,method,params);
  	 	result= intecept(bean,method,params,interceporList,++index);
  		inteceptor.after(bean,method,params,result);
  	}catch(Throwable e){
  		throwable = e;
  		inteceptor.afterThrowing(bean,method,params,e);
  		throw e;
  	}finally{
  		inteceptor.afterFinally(bean,method,params,result,throwable);
  	}
  	return result;
  }
  
  /**
   * intercept method
   */
  private Object intecept(Object bean,Method method,Object[] params,List interceporList,int index)throws Throwable {
  	 if(index > interceporList.size()-1){
  		 return method.invoke(bean,params);
  	 }else{
  		 InteceptionNode node =(InteceptionNode)interceporList.get(index);
  		 return node.invoke(bean,method,params,interceporList,index);
  	 }
  }
}
