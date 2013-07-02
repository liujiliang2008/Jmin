package org.jmin.test.ioc.property;

import org.jmin.ioc.BeanContext;
import org.jmin.ioc.impl.config.BeanContextImpl;
import org.jmin.test.ioc.BeanTestCase;

/**
 * 属性注入测试
 * 
 * @author Chris liao
 */
public class PropertyXMLCase extends BeanTestCase{
	
	/**
	 * 运行测试代码
	 */
	public static void test()throws Throwable{
		BeanContext context=new BeanContextImpl("org/jmin/test/ioc/property/pojo.xml");
		Man man = (Man)context.getBean("Bean1");
		if(man!=null){
			if("Chris".equals(man.getName()) && (28== man.getAge())){
				System.out.println("[XML].........属性注入测试成功..........");
			}else{
				throw new Error("[XML]...........属性注入测试失败............");
			}
		}
	}
	
  //启动测试方法
	public static void main(String[] args)throws Throwable{
		test();
	}
}
