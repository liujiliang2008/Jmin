/*
 * Copyright (c) jmin Organization. All rights reserved.
 */
package org.jmin.jec;

/**
 * 异步事件回调
 * 
 * @author Chris
 */
public interface EventCallBack {
	
	/**
	 * 异步事件处理结束时候调用
	 */
	public void callback(EventHandle handle);
	
}
