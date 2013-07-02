/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.test.jda.simple;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 驱动装载
 * 
 * @author Chris Liao
 */
public class Link {
	
	private static String driver;
	
	private static String url;
	
	private static String user;
	
	private static String password;
	
	private static final String FILE ="link.properties";
	
	static {
		try{
			InputStream resourceStream = Link.class.getResourceAsStream(FILE);
			Properties prop = new Properties();
			prop.load(resourceStream);
			driver = prop.getProperty("Driver");
			url = prop.getProperty("URL");
			user = prop.getProperty("User");
			password = prop.getProperty("Password");
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public static String getDriver() {
		return driver;
	}

	public static String getURL() {
		return url;
	}

	public static String getUser() {
		return user;
	}

	public static String getPassword() {
		return password;
	}
}
