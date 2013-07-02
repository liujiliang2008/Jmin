/*
 *Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.net.event;

import org.jmin.net.Connection;

/**
 * 当往某个连接上写数据完毕后时候触发
 *
 * @author Chris
 */

public class DataWriteInEvent extends ConnectionEvent {

	/**
	 * data to remote host
	 */
	private byte[] data;

	/**
	 * Constructor with a source object.
	 */
	public DataWriteInEvent(Connection source, byte[] data) {
		super(source);
		this.data = data;
	}

	/**
	 * Return byte data
	 * @return
	 */
	public byte[] getWroteByteArrayData() {
		return data;
	}

	/**
	 * set byte data.
	 */
	void setWroteByteArrayData(byte[] data) {
		this.data = data;
	}
}