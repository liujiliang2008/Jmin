package org.jmin.test.ioc;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import org.jmin.ioc.impl.util.ClassUtil;

public class BeanTestRunner {
	
	/**
	 * 默认配置文件名
	 */
	private static String defaultFilename="testCase.properties";
	
	/**
	 * 装载测试配置文件
	 */
	private static Class[] getTestCaseClasses()throws Exception{
		return getTestCaseClasses(defaultFilename);
	}
	
	/**
	 * 装载测试配置文件
	 */
	private static Class[] getTestCaseClasses(String caseFile)throws Exception{
		List classList = new ArrayList();
		InputStream propertiesStream = null;
		try {
			Properties properties = new SortKeyProperties();
			propertiesStream = BeanTestRunner.class.getResourceAsStream(caseFile);
			if(propertiesStream==null)
				throw new FileNotFoundException(caseFile);
			
			properties.load(propertiesStream);
			Enumeration enumtion = properties.keys();
			while(enumtion.hasMoreElements()){
				Class clazz = ClassUtil.loadClass((String)enumtion.nextElement());
				classList.add(clazz);
			}
			
			return (Class[])classList.toArray(new Class[0]);

		} finally {
		  if(propertiesStream !=null)
				try {
					propertiesStream.close();
				} catch (IOException e) {
				}
		}
	}
	
	/**
	 * 运行某个类的测试
	 */
	public static void run(Class testClass)throws Throwable{
		if (testClass != null) {
			((BeanTestCase)testClass.newInstance()).run();
		}
	}
	
	/**
	 * 运行一批测试类
	 */
	public static void run(Class[] testClass)throws Throwable{
		if(testClass!=null){
			for(int i=0;i<testClass.length;i++)
			 run(testClass[i]);
		}
	}
	
	
	
	/**
	 * 测试入口
	 */
	public static void main(String[] ags)throws Throwable{
		BeanTestRunner.run(getTestCaseClasses());
	}
}
