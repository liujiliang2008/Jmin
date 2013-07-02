package org.jmin.test.ioc.interception;

import org.jmin.ioc.BeanContainer;
import org.jmin.ioc.BeanElementFactory;
import org.jmin.ioc.element.InvocationInterception;
import org.jmin.ioc.impl.MinBeanContainer;
import org.jmin.test.ioc.BeanTestCase;
import org.jmin.test.ioc.interception.object.Child;
import org.jmin.test.ioc.interception.object.ChildInterceptor;
import org.jmin.test.ioc.interception.object.ChildInterceptor2;

/**
 * 方法拦截测试
 * 
 * @author chris
 *
 */
public class WrapCase extends BeanTestCase{
	
	/**
	 * 运行测试代码
	 */
	public static void test()throws Throwable{
		BeanContainer container = new MinBeanContainer();
		
		BeanElementFactory beanElementFactory =container.getBeanElementFactory();
		InvocationInterception interception = beanElementFactory.createInvocationInterception("sayHello", new Class[] {java.lang.String.class });
		interception.addInterceptorClass(ChildInterceptor.class);
		interception.addInterceptorClass(ChildInterceptor2.class);
		container.registerClass("Bean2", Child.class);
		container.addInvocationInterception("Bean2",interception);
		
		Child child = (Child)container.getBean("Bean2");
		child.sayHello("Chris");
		System.out.println("[Container].........AOP类修改拦截测试成功..........");
	}
	
	//启动测试方法
	public static void main(String[] args)throws Throwable{
		test();
	}
}