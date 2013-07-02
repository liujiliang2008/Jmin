/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.mapping;

/**
 * 存储过程调用的参数模式
 * 
 * @author Chris
 */
public final class ParamValueMode {
	public final static ParamValueMode IN =new ParamValueMode(0);//进参数
	public final static ParamValueMode OUT =new ParamValueMode(1);//出参数
	public final static ParamValueMode INOUT =new ParamValueMode(2);//进出参数
	
	private int modeValue;
	public ParamValueMode(int modeValue){
		this.modeValue = modeValue;
	}
	
	/**
	 * 重写方法
	 */
	public boolean equals(Object obj){
		if(obj instanceof ParamValueMode){
			ParamValueMode other = (ParamValueMode)obj;
			return this.modeValue == other.modeValue;
		}else {
			return false;
		}
	}
	
	/**
	 * 获得参数模式
	 */
	public static ParamValueMode getParamMode(String name){
	 if(name.equalsIgnoreCase("in")){
		 return IN;
	 }else if(name.equalsIgnoreCase("out")){
		 return OUT;
	 }else if(name.equalsIgnoreCase("inout")){
		 return INOUT;
	 }else{
		 return null;
	 }
	}
}