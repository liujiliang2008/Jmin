/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.connection;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.jmin.log.Logger;

/**
 * Source info creater
 *
 * @author Chris
 * @version 1.0
 */

public class StatementHandler implements InvocationHandler{
	
	/**
	 * closed indicator
	 */
	private boolean closed = false;
	
	/**
	 * physical connection
	 */
	private PreparedStatement statement;
	
	/**
	 * 所在的Connection Handler
	 */
	private ConnectionHandler connectionHandler;
	
	/**
	 * message Printer
	 */
	private static final Logger printer = Logger.getLogger(StatementHandler.class);
	
	/**
	 * constructor with a statement
	 */
	public StatementHandler(PreparedStatement statement,ConnectionHandler connectionHandler){
		this.statement = statement;
		this.connectionHandler =connectionHandler;
	}
	
	/**
	 * reflect method
	 */
	public Object invoke(Object proxy, Method method, Object[] args)throws Throwable{
		try {
			this.connectionHandler.setLastExecutedTime(System.currentTimeMillis());
			if(closed)
				throw new SQLException("PreparedStatement has been closed");
			if("close".equals(method.getName())){
				 this.closed = true;
				 return null;
			}else{
				Object result = method.invoke(this.statement,args);
				if(method.getName().startsWith("execute"))
					printer.debug("Executed preparedStatement:"+ statement);
				
				return result;
			}
		} catch (InvocationTargetException e) {
			 throw e.getTargetException();
		}
	}
}