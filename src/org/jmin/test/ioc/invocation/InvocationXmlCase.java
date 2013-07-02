/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.test.ioc.invocation;

import org.jmin.ioc.BeanContext;
import org.jmin.ioc.impl.config.BeanContextImpl;
import org.jmin.test.ioc.BeanTestCase;

/**
 * 调用注入方式
 *
 * @author Chris
 */
public class InvocationXmlCase extends BeanTestCase{
	
	/**
	 * 运行测试代码
	 */
	public static void test()throws Throwable{
		BeanContext context = new BeanContextImpl("org/jmin/test/ioc/invocation/pojo.xml");
		Man man = (Man)context.getBean("Bean1");
		if(man!=null){
			if("Chris".equals(man.getName())){
				System.out.println("[XML]........方法调用测试成功..........");
			}else{
				throw new Error("[XML]..........方法调用测试失败............");
			}
		}
	}

  //启动测试方法
	public static void main(String[] args)throws Throwable{
		test();
	}
}