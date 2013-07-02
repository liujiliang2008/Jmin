/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.app.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * Web Session Listener
 * 
 * @author chris
 */
public class DaoWebRequestFilter implements Filter{

	/**
	 * 初试化
	 */
	public void init(FilterConfig arg)throws ServletException{}
	
	/**
	 * 过滤
	 */
	public void doFilter(ServletRequest request,ServletResponse response,FilterChain chain)throws ServletException,IOException{
		DaoTemplateUtil.putHttpServletRequest((HttpServletRequest)request);
	}
	
	/**
	 * destroy
	 */
	public void destroy(){}
}
