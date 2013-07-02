/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.test.ioc.autowired;

import org.jmin.ioc.BeanContext;
import org.jmin.ioc.impl.config.BeanContextImpl;
import org.jmin.test.ioc.BeanTestCase;

/**
 * A IOC Autowired example
 *
 * @author Chris
 */
public class AutowiredXmlCase extends BeanTestCase{
	
	/**
	 * Test method
	 */
	public static void test()throws Throwable{
		BeanContext context = new BeanContextImpl("org/jmin/test/ioc/autowired/pojo.xml");
		Boy boy = (Boy)context.getBean("Boy");
		if(boy!=null){
			if("Chris".equals(boy.getName())){
				System.out.println("[XML].........Autowired(byName) Success.........");
			}else{
				throw new Error("[XML]...........Autowired(byName) Failed............");
			}
		}
		
		Girl girl = (Girl)context.getBean("Girl");
		if(girl!=null){
			if((28== girl.getAge())){
				System.out.println("[XML]...........Autowired(byType) Success.............");
			}else{
				throw new Error("[XML]...........Autowired(byType) Failed...............");
			}
		}
	}
	
	//方法
	public static void main(String[] args)throws Throwable{
		test();
	}
}