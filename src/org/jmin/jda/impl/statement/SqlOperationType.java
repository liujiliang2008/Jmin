/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.statement;

/**
 * SQL文本操作类型
 * 
 * @author Chris Liao
 */

public class SqlOperationType {
	
	/**
	 * Insert操作类型
	 */
	public static final SqlOperationType Insert = new SqlOperationType(1);
 
	/**
	 * Update操作类型 
	 */
	public static final SqlOperationType Update = new SqlOperationType(2);
	
	/**
	 * Delete操作类型
	 */
	public static final SqlOperationType Delete = new SqlOperationType(3);

	/**
	 * 查询操作类型
	 */
	public static final SqlOperationType Select = new SqlOperationType(4);

	/**
	 * 存储过程调用类型
	 */
	public static final SqlOperationType Procedure = new SqlOperationType(5);
	
	/**
	 * 未知的操作类型，一般是那些DDL的操作
	 */
	public static final SqlOperationType Unknown = new SqlOperationType(6);
	
	/**
	 * type code
	 */
	private int operationCode;
	
	/**
	 * 构造函数
	 */
	private SqlOperationType(int code) {
		this.operationCode = code;
	}

	/**
	 * hashcode
	 */
	public int hashCode() {
		return this.operationCode;
	}
	
	/**
	 * equals
	 */
	public boolean equals(Object obj) {
		if (obj instanceof SqlOperationType) {
			SqlOperationType other = (SqlOperationType) obj;
			return this.operationCode == other.operationCode;
		} else {
			return false;
		}
	}
	
	/**
	 * override method
	 */
	public String toString(){
		String type ="";
		switch(operationCode){
			case 1:type="Insert";break;
			case 2:type="Update";break;
			case 3:type="Delete";break;
			case 4:type="Select";break;
			case 5:type="Procedure";break;
			case 6:type="Unknown";break;
			default:type="Unknown";break;
		}
		return type;
	}
}
