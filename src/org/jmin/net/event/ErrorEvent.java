/*
 *Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.net.event;

import org.jmin.net.Connection;

/**
 * When exception occurs during net operation
 *
 * @author Chris
 */

public class ErrorEvent extends ConnectionEvent {

	/**
	 * caused exception
	 */
	public Throwable detail;

	/**
	 * Constructor with a source object.
	 */
	public ErrorEvent(Connection source, Throwable detail) {
		super(source);
		this.detail = detail;
	}

	/**
	 *  Return caused throwable.
	 */
	public Throwable getCause() {
		return detail;
	}

	/**
	 * override method
	 * @return
	 */
	public String toString() {
		return detail.toString();
	}
}