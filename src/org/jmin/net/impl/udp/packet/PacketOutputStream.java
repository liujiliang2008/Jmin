/*
 *Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.net.impl.udp.packet;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * UPD数据包读流
 * 
 * @author Chris Liao
 */

public class PacketOutputStream extends OutputStream{

	/**
	 * 二进制数据流
	 */
	private ByteArrayOutputStream byteStream;

	/**
	 * 数据输出流
	 */
	private DataOutputStream dataOut;

	/**
	 * 构造函数
	 */
	public PacketOutputStream()throws IOException {
		this.byteStream = new ByteArrayOutputStream();
		this.dataOut = new DataOutputStream(this.byteStream);
	}

	/**
	 * 写byte
	 */
	public void write(int b) throws IOException {
		dataOut.write(b);
	}
	
	/**
	 * 写byte array
	 */
	public void write(byte b[]) throws IOException {
		dataOut.write(b);
	}

	/**
	 * 写byte array
	 */
	public void write(byte b[], int off, int len) throws IOException {
		dataOut.write(b, off, len);
	}

	/**
	 * 写boolean
	 */
	public void writeBoolean(boolean v) throws IOException {
		dataOut.writeBoolean(v);
	}

	/**
	 *  写Byte
	 */
	public void writeByte(int v) throws IOException {
		dataOut.writeByte(v);
	}

	/**
	 *写Short
	 */
	public final void writeShort(int v) throws IOException {
		dataOut.writeShort(v);
	}

	/**
	 * 写char
	 */
	public void writeChar(int v) throws IOException {
		dataOut.writeChar(v);
	}

	/**
	 * 写Int
	 */
	public void writeInt(int v) throws IOException {
		dataOut.writeInt(v);
	}
	
	/**
	 * 写long
	 */
	public final void writeLong(long v) throws IOException {
		dataOut.writeLong(v);
	}

	/**
	 * 写float
	 */
	public final void writeFloat(float v) throws IOException {
		dataOut.writeFloat(v);
	}

	/**
	 * 写double
	 */
	public final void writeDouble(double v) throws IOException {
		dataOut.writeDouble(v);
	}
	
	/**
	 * 写String
	 */
	public void writeBytes(String str) throws IOException {
		 dataOut.writeBytes(str);
	}

	/**
	 * 写String
	 */
	public void writeChars(String str) throws IOException {
		dataOut.writeChars(str);
	}
	
	/**
	 * 写String
	 */
	public final void writeUTF(String str) throws IOException {
		dataOut.writeUTF(str);
	}
	
	/**
	 * 关闭流
	 */
	public void close()throws IOException {
		dataOut.close();
	}

	/**
	 * Flush
	 */
	public void flush() throws IOException {
		dataOut.flush();
	}

	/**
	 * 获得二进制数组
	 */
	public byte[] toByteArray() {
		return this.byteStream.toByteArray();
	}
}
