/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.net.impl.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;

import org.jmin.net.Connection;

/**
 * Util class
 * 
 * @author Chris
 */

public class CloseUtil {
	
	
	/**
	 * close Connection
	 */
	public static void close(Connection connection) {
		try {
			connection.close();
		} catch (Throwable e) {
		}
	}
	
	/**
	 * close InputStreamt
	 */
	public static void close(InputStream inputStream) {
		try {
			inputStream.close();
		} catch (Throwable e) {
		}
	}
	
	/**
	 * close outputStream
	 */
	public static void close(OutputStream outputStream) {
		try {
			outputStream.close();
		} catch (Throwable e) {
		}
	}
	
	/**
	 * close writer
	 */
	public static void close(Writer writer) {
		try {
			writer.close();
		} catch (Throwable e) {
		}
	}
	
	/**
	 * close Reader
	 */
	public static void close(Reader reader) {
		try {
			reader.close();
		} catch (Throwable e) {
		}
	}

	/**
	 * close Socket
	 */
	public static void close(Socket socket) {
		try {
			socket.close();
		} catch (Throwable e) {
		}
	}
	
	/**
	 * close DatagramSocket
	 */
	public static void close(DatagramSocket socket) {
		try {
			socket.close();
		} catch (Throwable e) {
		}
	}
	
	/**
	 * close ServerSocket
	 */
	public static void close(ServerSocket socket) {
		try {
			socket.close();
		} catch (Throwable e) {
		}
	}
}
