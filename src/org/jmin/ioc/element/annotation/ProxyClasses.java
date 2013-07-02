/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.element.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Proxy annotation
 *
 * @author Chris Liao
 * @version 1.0
 */ 
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented 
public @interface ProxyClasses {
	
  //代理类名
	public String[] interfaces() default "";
}
