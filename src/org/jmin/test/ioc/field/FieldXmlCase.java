/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.test.ioc.field;

import org.jmin.ioc.BeanContext;
import org.jmin.ioc.impl.config.BeanContextImpl;
import org.jmin.test.ioc.BeanTestCase;

/**
 * A IOC field example
 *
 * @author Chris
 */
public class FieldXmlCase extends BeanTestCase{
	
	/**
	 * 运行测试代码
	 */
	public static void test()throws Throwable{
		BeanContext context = new BeanContextImpl("org/jmin/test/ioc/field/pojo.xml");
		Boy boy = (Boy)context.getBean("Bean1");
		if(boy!=null){
			if("Chris".equals(boy.getName())){
				System.out.println("[XML].........字段参数注射测试成功..........");
			}else{
				throw new Error("[XML] ...........字段参数注射测试测试失败............");
			}
		}
		
		Boy boy2 = (Boy)context.getBean("Bean2");
		if(boy!=null){
			if((28== boy2.getAge())){
				System.out.println("[XML]...........字段参数注射测试成功.............");
			}else{
				throw new Error("[XML]............字段参数注射测试测试失败...............");
			}
		}
	}
	
	//方法
	public static void main(String[] args)throws Throwable{
		test();
	}
}