/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.test.ioc.bijection;

import org.jmin.ioc.BeanContext;
import org.jmin.ioc.impl.config.BeanContextImpl;
import org.jmin.test.ioc.BeanTestCase;

/**
 * A IOC Bijection demo
 *
 * @author Chris
 */
public class AnnotationCase extends BeanTestCase{
	
	/**
	 * test method
	 */
	public static void test()throws Throwable{
		BeanContext context=new BeanContextImpl("org/jmin/test/ioc/bijection/pojo2.xml");
		Wife wife =(Wife)context.getBean("Wife");
		Husband husband =	(Husband)context.getBean("Husband");
		if(wife!=null && wife.getHusband()==husband){
			System.out.println("[Annotation].........Bijection success..........");
		}else{
			System.out.println("[Annotation].........Bijection failed..........");
		}
	}
	
	//启动测试方法
	public static void main(String[] args)throws Throwable{
		test();
	}
}