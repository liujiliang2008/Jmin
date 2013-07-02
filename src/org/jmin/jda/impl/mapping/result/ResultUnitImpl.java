/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.mapping.result;

import org.jmin.jda.JdaTypePersister;
import org.jmin.jda.mapping.ResultUnit;

/**
 * 对象中的结果映射属性
 * 
 * @author Chris
 */

public class ResultUnitImpl implements ResultUnit{
	
	/**
	 *是否初始化过
	 */
	private boolean inited;
	
	/**
	 * 所属于的映射map
	 */
  private Object mapOwner=null;
	
	/**
	 * 属性名
	 */
	private String propertyName=null;
	
	/**
	 * 属性类型
	 */
	private Class propertyType=null;
	
	/**
	* 映射的列名
	*/
	private String resultColumnName=null;

	/**
	 * 结果转换器
	 */
	private JdaTypePersister resultConverter=null;
	
	/**
	* 构造函数
	*/
	public ResultUnitImpl(String propertyName){
		this(propertyName,(Class)null);
	}
	
	/**
	* 构造函数
	*/
	public ResultUnitImpl(String propertyName,Class propertyType){
		this.propertyName = propertyName;
		this.resultColumnName = propertyName;
		this.propertyType = propertyType;
	}
	
	/**
	 *是否初始化过
	 */
	public boolean isInited() {
		return inited;
	}
	
	/**
	 *是否初始化过
	 */
	public void setInited(boolean inited) {
		this.inited = inited;
	}
	
	/**
	 * 所属于的映射map
	 */
	public Object getMapOwner() {
		return mapOwner;
	}

	/**
	 * 所属于的映射map
	 */
	public void setMapOwner(Object mapOwner) {
		this.mapOwner = mapOwner;
	}

	/**
	 * 获得属性名
	 */
	public String getPropertyName(){ 
		return this.propertyName;
	}
	
	/**
	 * 获得属性类别
	 */
	public Class getPropertyType(){ 
		return this.propertyType;
	}
	
	/**
	 * 设置属性类别
	 */
	public void setPropertyType(Class propertyType){ 
	  if(!this.isInited())
		  this.propertyType = propertyType;
	}
	
	/**
	 * 获得属性类别
	 */
	public String getResultColumnName(){ 
		return resultColumnName;
	} 
	
	/**
	 * 设置属性类别
	 */
	public void setResultColumnName(String fieldName){
	  if(!this.isInited())
		  this.resultColumnName = fieldName;
	} 
	
	/**
	 * 获得结果映射
	 */
	public JdaTypePersister getJdbcTypePersister(){
	  return this.resultConverter;
	}
	
	/**
	 * 设置结果映射
	 */
	public void setJdbcTypePersister(JdaTypePersister converter){
		if(!this.isInited())
			this.resultConverter = converter;
	}
}
