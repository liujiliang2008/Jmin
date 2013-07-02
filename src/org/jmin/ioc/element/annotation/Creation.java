/*
* Copyright(c) jmin Organization. All rights reserved.
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

public @interface Creation {
	
	/**
	 *@parameters=({"val:xxxx","ref:xxxx","class:xxx"})
	 */
	public String[] parameters() default "";
	
	/**
	 * factory bean
	 */
	public String factoryBean() default "";
	
	/**
	 * factory mehtod
	 */
	public String factoryMethod() default "";
	
}
