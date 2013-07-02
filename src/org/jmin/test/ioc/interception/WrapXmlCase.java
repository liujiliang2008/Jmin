/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.test.ioc.interception;

import org.jmin.ioc.BeanContext;
import org.jmin.ioc.impl.config.BeanContextImpl;
import org.jmin.test.ioc.BeanTestCase;
import org.jmin.test.ioc.interception.object.Child;

/**
 * 拦截测试
 *
 * @author Chris
 */
public class WrapXmlCase extends BeanTestCase{

	/**
	 * 运行测试代码
	 */
	public static void test()throws Throwable{
		BeanContext context = new BeanContextImpl("org/jmin/test/ioc/interception/pojo.xml");
		Child child = (Child)context.getBean("Bean2");
		child.sayHello("Chris");
		System.out.println("[XML].........AOP类修改拦截测试成功..........");
	}

  //启动测试方法
	public static void main(String[] args)throws Throwable{
		test();
	}
}