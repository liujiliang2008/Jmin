/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.test.ioc.interception;

import org.jmin.ioc.BeanContext;
import org.jmin.ioc.impl.config.BeanContextImpl;
import org.jmin.test.ioc.BeanTestCase;
import org.jmin.test.ioc.interception.object.Young;

/**
 *  代理测试
 *
 * @author Chris
 */
public class ProxyXmlCase extends BeanTestCase{
	
	/**
	 * 运行测试代码
	 */
	public static void test()throws Throwable{
		BeanContext context=new BeanContextImpl("org/jmin/test/ioc/interception/pojo.xml");
		Young child = (Young)context.getBean("Bean1");
		child.sayHello("Chris");
		System.out.println("[XML].........Proxy拦截测试成功..........");
	}
	
  //启动测试方法
	public static void main(String[] args)throws Throwable{
		test();
	}
}