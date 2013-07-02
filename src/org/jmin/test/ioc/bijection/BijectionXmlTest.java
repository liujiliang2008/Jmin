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
public class BijectionXmlTest extends BeanTestCase{
	
	/**
	 * 运行测试代码
	 */
	public static void test()throws Throwable{
		BeanContext context=new BeanContextImpl("org/jmin/test/ioc/bijection/pojo.xml");
		Wife wife =(Wife)context.getBean("Wife");
		Husband husband =	(Husband)context.getBean("Husband");
		if(wife!=null && wife.getHusband()==husband){
			System.out.println("[XML].........Bijection success..........");
		}else{
			System.out.println("[XML].........Bijection failed..........");
		}
	}
	
	//启动测试方法
	public static void main(String[] args)throws Throwable{
		test();
	}
}