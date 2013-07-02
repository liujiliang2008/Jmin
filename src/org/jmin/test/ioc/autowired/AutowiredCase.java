/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.test.ioc.autowired;

import org.jmin.ioc.BeanContainer;
import org.jmin.ioc.BeanElement;
import org.jmin.ioc.BeanElementFactory;
import org.jmin.ioc.element.AutowiredType;
import org.jmin.ioc.element.InjectionField;
import org.jmin.ioc.impl.MinBeanContainer;
import org.jmin.test.ioc.BeanTestCase;

/**
 * Autowired Demo
 *
 * @author Chris
 */
public class AutowiredCase extends BeanTestCase{
	
	/**
	 *test method
	 */
	public static void test()throws Throwable{
		BeanContainer container = new MinBeanContainer();
		BeanElementFactory beanElementFactory =container.getBeanElementFactory();
		
		container.registerInstance("name", "Chris");
		container.registerInstance("age",  new Integer(28));
		
    InjectionField nameField=beanElementFactory.createInjectionField("name",AutowiredType.BY_NAME);
		container.registerClass("Boy", Boy.class,new BeanElement[]{nameField});
   
		InjectionField ageField=beanElementFactory.createInjectionField("age",AutowiredType.BY_TYPE);
		container.registerClass("Girl", Girl.class,new BeanElement[]{ageField});
		
		Boy boy = (Boy)container.getBean("Boy");
		if(boy!=null){
			if("Chris".equals(boy.getName())){
				System.out.println("[Container].........Autowired(byName) Success.........");
			}else{
				throw new Error("[Container]...........Autowired(byName) Failed............");
			}
		}
		
		Girl girl = (Girl)container.getBean("Girl");
		if(girl!=null){
			if((28== girl.getAge())){
				System.out.println("[Container]...........Autowired(byType) Success.............");
			}else{
				throw new Error("[Container]...........Autowired(byType) Failed...............");
			}
		}
	}
 
	//方法
	public static void main(String[] args)throws Throwable{
		test();
	}
}
