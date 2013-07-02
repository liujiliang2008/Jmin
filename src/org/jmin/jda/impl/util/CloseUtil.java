/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Util class to close database connection,Statement,ResultSet.
 * 
 * @author Chris
 */

public class CloseUtil {
	
	/**
	 * close connection
	 */
	public static void close(Connection connection) {
		try {
			connection.close();
		} catch (Throwable e) {
		}
	}
	/**
	 * close statement 
	 */
	public static void close(Statement statement) {
		try {
			statement.close();
		} catch (Throwable e) {
		}
	}
	
	/**
	 * close result set
	 */
	public static void close(ResultSet resultSet) {
		try {
			resultSet.close();
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
	 * close InputStreamt
	 */
	public static void close(Writer writer) {
		try {
			writer.close();
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
}
