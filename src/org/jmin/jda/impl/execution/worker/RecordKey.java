/*
 *Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.execution.worker;
import java.util.Arrays;
/**
 * 结果Key
 * 
 * @author Chris
 */
public class RecordKey {
	
	/**
	 * 映射ID
	 */
	private String mapId;
	
	/**
	 * Key列值
	 */
	private Object[] keyValues;
	
	/**
	 * 构造函数
	 */
	public RecordKey(String mapId,Object[] keyValues){
		this.mapId = mapId;
		this.keyValues = keyValues;
	}
	
	/**
	 * 映射结果ID
	 */
	public String getMapId() {
		return mapId;
	}
	
	/**
	 * 列值
	 */
	public Object[] getKeyValues() {
		return keyValues;
	}
	
	/**
	 * 重写方法
	 */
	public int hashCode(){
		int hashCode = mapId.hashCode();
		for(int i=0;keyValues!=null && i<keyValues.length;i++){
			if(keyValues[i]!=null)
				hashCode ^=keyValues[i].hashCode();
			else
				hashCode ^=1;
		}
		return hashCode;
	}
	
	/**
	 * 重写方法
	 */
	public boolean equals(Object obj){
		if(obj instanceof RecordKey){
			return false;
		}else{
			RecordKey other =(RecordKey)obj;
			return this.mapId.equals(other.getMapId()) 
				&& Arrays.equals(this.getKeyValues(),other.getKeyValues());
		}
	}
}
