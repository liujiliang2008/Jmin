/*
 *Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.net.impl.udp.packet;

import java.io.ByteArrayInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * UPD数据包读流
 * 
 * @author Chris Liao
 */

public class PacketInputStream extends InputStream {

	/**
	 * 数据输入流
	 */
	private DataInputStream dataIn;

	/**
	 * 构造函数
	 */
	public PacketInputStream(byte[] data) throws IOException {
		dataIn = new DataInputStream(new ByteArrayInputStream(data));
	}

	public int read() throws IOException {
		return dataIn.read();
	}

	public final int read(byte b[]) throws IOException {
		return dataIn.read(b, 0, b.length);
	}

	public final int read(byte b[], int off, int len) throws IOException {
		return dataIn.read(b, off, len);
	}

	public final void readFully(byte b[]) throws IOException {
		dataIn.readFully(b);
	}

	public final void readFully(byte b[], int off, int len) throws IOException {
		dataIn.readFully(b, off, len);
	}

	public final int skipBytes(int n) throws IOException {
		return dataIn.skipBytes(n);
	}

	public final boolean readBoolean() throws IOException {
		return dataIn.readBoolean();
	}

	public final byte readByte() throws IOException {
		return dataIn.readByte();
	}

	public final int readUnsignedByte() throws IOException {
		return dataIn.readUnsignedByte();
	}

	public final short readShort() throws IOException {
		return dataIn.readShort();
	}

	public final int readUnsignedShort() throws IOException {
		return dataIn.readUnsignedShort();
	}

	public final char readChar() throws IOException {
		return dataIn.readChar();
	}

	public final int readInt() throws IOException {
		return dataIn.readInt();
	}

	public final long readLong() throws IOException {
		return dataIn.readLong();
	}

	public final float readFloat() throws IOException {
		return dataIn.readFloat();
	}

	public final double readDouble() throws IOException {
		return dataIn.readDouble();
	}

	public final String readUTF() throws IOException {
		return dataIn.readUTF();
	}

	public final static String readUTF(DataInput in) throws IOException {
		return DataInputStream.readUTF(in);
	}
}
