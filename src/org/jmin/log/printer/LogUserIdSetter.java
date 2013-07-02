/*
* Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.log.printer;

/**
 * 设置当前Log用户的ID
 * 
 * @author Chris Liao
 */

public abstract class LogUserIdSetter {
	
	/**
	 * 打印日记的驱动用户ID
	 */
	public static final String Log_User_ID ="UserID";
	
	/**
	 * 设置当前用户ID
	 */
	public static void setUserID(String userID){
		LogVariable.put(Log_User_ID,userID);
	}
}
