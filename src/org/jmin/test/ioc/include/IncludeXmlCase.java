/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.test.ioc.include;

import org.jmin.ioc.BeanContext;
import org.jmin.ioc.impl.config.BeanContextImpl;
import org.jmin.test.ioc.BeanTestCase;
import org.jmin.test.ioc.include.object.Computer;

/**
 * XML include测试
 *
 * @author Chris
 */
public class IncludeXmlCase extends BeanTestCase{
	
	/**
	 * 运行测试代码
	 */
	public static void test()throws Throwable{
		BeanContext context=new BeanContextImpl("org/jmin/test/ioc/include/pojo.xml");
		Computer computer = (Computer)context.getBean("Bean1");
		if(computer!=null && computer.getKeyBoard()!=null && computer.getMonitor()!=null){
			System.out.println("[XML].........Include xml file 测试成功..........");
		}else{
			throw new Error("[XML] ...........Include xml file 测试失败............");
	  }
	}
	
	//启动测试方法
	public static void main(String[] args)throws Throwable{
		test();
	}
}