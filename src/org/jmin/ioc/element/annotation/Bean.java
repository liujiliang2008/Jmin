/*
* Copyright (c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.element.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * bean annotation
 *
 * @author Chris Liao
 * @version 1.0
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented 
@Inherited   
public @interface Bean {
	
	/**
	 * Bean Id
	 */
	public String id()default "";;
	
	/**
	 * parent id
	 */
	public String parentId() default "";
	
	/**
	 * singleton
	 */
	public boolean singleton() default true;
	
	/**
	 * init mehtod
	 */
	public String initMethod() default "";
	
	/**
	 * destroy mehtod
	 */
	public String destroyMethod() default "";
}
