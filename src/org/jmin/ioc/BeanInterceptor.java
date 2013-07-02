/*
*Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.ioc;

import java.lang.reflect.Method;

/**
 * 拦截器接口
 *
 * @author Chris Liao
 * @version 1.0
 */

public interface BeanInterceptor {

  /**
   * intercept method
   */
  public void before(final Object bean,Method method,Object[] params)throws Throwable;
  
  /**
   * intercept method
   */
  public void after(final Object bean,Method method,Object[] params,final Object result)throws Throwable;
  
  /**
   * intercept method
   */
  public void afterThrowing(final Object bean,Method method,Object[] params,Throwable throwable)throws Throwable;
  
  /**
   * intercept method
   */
  public void afterFinally(final Object bean,Method method,Object[] params,final Object result,Throwable throwable)throws Throwable;
}