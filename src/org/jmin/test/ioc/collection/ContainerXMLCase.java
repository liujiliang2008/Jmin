/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.test.ioc.collection;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jmin.ioc.BeanContext;
import org.jmin.ioc.impl.config.BeanContextImpl;
import org.jmin.test.ioc.BeanTestCase;

/**
 * A IOC Autowire example
 *
 * @author Chris
 */
public class ContainerXMLCase extends BeanTestCase{
	
	/**
	 * 运行测试代码
	 */
	public static void test()throws Throwable{
		BeanContext context=new BeanContextImpl("org/jmin/test/ioc/collection/pojo.xml");
		School school = (School)context.getBean("school");
		List roomList = school.getRoomList();
		Set classSet = school.getClassSet();
		Map teacherMap= school.getTeacherMap();
		
		if(roomList!=null&&roomList.size()==3){
			System.out.println("[XML].........List测试成功..........");
		}else{
			throw new Error("[XML].........List测试失败..........");
		}
		
		if(classSet!=null&&classSet.size()==3){
			System.out.println("[XML].........Set测试成功..........");
		}else{
			throw new Error("[XML].........Set测试失败..........");
		}
		
		if(teacherMap!=null&&teacherMap.size()==3){
			System.out.println("[XML].........Map测试成功..........");
		}else{
			throw new Error("[XML].........Map测试失败..........");
		}
	}
	
	//启动测试方法
	public static void main(String[] args)throws Throwable{
		test();
	}
}