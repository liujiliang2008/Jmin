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
 * field annotation
 *
 * @author Chris Liao
 * @version 1.0
 */ 

@Documented
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)  

public @interface Parameters {
	
	/**
	 *@Parameters({"val:xxxx","ref:xxxx","class:xxx"})
	 */
	public String[] values() default "";
	
}
