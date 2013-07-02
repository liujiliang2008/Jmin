/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.test.ioc.bijection;

import org.jmin.ioc.BeanContainer;
import org.jmin.ioc.BeanElementFactory;
import org.jmin.ioc.BeanParameter;
import org.jmin.ioc.BeanParameterFactory;
import org.jmin.ioc.element.InjectionProperty;
import org.jmin.ioc.impl.MinBeanContainer;
import org.jmin.test.ioc.BeanTestCase;

/**
 * Bijection Demo
 * 
 * @author Chris Liao
 */
public class BijectionTest extends BeanTestCase{
	
	/**
	 * test method
	 */
	public static void test()throws Throwable{
		BeanContainer container = new MinBeanContainer();
		BeanElementFactory beanElementFactory =container.getBeanElementFactory();
		BeanParameterFactory beanParameterFactory =container.getBeanParameterFactory();
		
		BeanParameter WifNameParmeter =  beanParameterFactory.createStringParameter("Summer");
		InjectionProperty WifenameProperty = beanElementFactory.createInjectionProperty("name",WifNameParmeter);
		BeanParameter HusbandParmeter =  beanParameterFactory.createReferenceParameter("Husband");
		InjectionProperty HusbandProperty =  beanElementFactory.createInjectionProperty("husband",HusbandParmeter);
		container.registerClass("Wife",Wife.class,new InjectionProperty[] {WifenameProperty,HusbandProperty});
		
		BeanParameter HusNameParmeter =  beanParameterFactory.createStringParameter("Chris");
		InjectionProperty HusNameProperty = beanElementFactory.createInjectionProperty("name",HusNameParmeter);
		BeanParameter WifeParmeter =  beanParameterFactory.createReferenceParameter("Wife");
		InjectionProperty WifeProperty =  beanElementFactory.createInjectionProperty("wife",WifeParmeter);
		container.registerClass("Husband",Husband.class,new InjectionProperty[] {HusNameProperty,WifeProperty});
		
		Wife wife =(Wife)container.getBean("Wife");
		Husband husband =	(Husband)container.getBean("Husband");
		if(wife!=null && wife.getHusband()==husband){
			System.out.println("[Container].........Bijection success..........");
		}else{
			System.out.println("[Container].........Bijection failed ..........");
		}
	}

  //方法
	public static void main(String[] args)throws Throwable{
		test();
	}
}