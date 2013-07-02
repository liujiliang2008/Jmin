package org.jmin.test.ioc.classedit;
import java.lang.reflect.Method;

import org.jmin.ioc.impl.intercept.ClassWrapEditor;
import org.jmin.ioc.impl.intercept.InterceptorChain;
import org.jmin.test.ioc.BeanTestCase;

/**
 * 类的AOP修改
 * 
 * @author Liao
 */
public class BoyClassModify extends BeanTestCase{
	
	/**
	 * 运行测试代码
	 */
	public static void test()throws Throwable{
		InterceptorChain chain = new InterceptorChain();
		chain.addMethodInterceptor("print",new Class[]{Integer.TYPE},new Interceptor1());
		chain.addMethodInterceptor("print",new Class[]{Integer.TYPE},new Interceptor2());
		Method printMethod = Boy.class.getMethod("print",new Class[]{Integer.TYPE});
		
		Class class2 = ClassWrapEditor.createWrapClass(Boy.class, new Method[]{printMethod}); 
		Boy boy = (Boy)class2.newInstance();
		Method method = boy.getClass().getDeclaredMethod("setInterceptorChain", new Class[]{org.jmin.ioc.impl.intercept.InterceptorChain.class});
		method.invoke(boy,new Object[]{chain});
		boy.say("Hello");
		boy.print(1);
		System.out.println("[Container].........类的AOP修改测试通过..........");
	}
	
	 //方法
	public static void main(String[] args) throws Throwable {
		test();
	}
}
