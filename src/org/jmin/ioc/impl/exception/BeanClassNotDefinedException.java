package org.jmin.ioc.impl.exception;

public class BeanClassNotDefinedException extends BeanDefinitionException {

	/**
	 * 构造函数
	 */
	public BeanClassNotDefinedException(Object beanId,String message) {
		super(message);
	}
	
	/**
	 * 构造函数
	 */
	public BeanClassNotDefinedException(Object beanId,Throwable cause) {
		super(cause);
	}
	
	/**
	 * 构造函数
	 */
	public BeanClassNotDefinedException(Object beanId,String message,Throwable cause) {
		super(message,cause);
	}
}