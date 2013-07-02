/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.gui;

/**
 * GUI异常
 * 
 * @author Chris Liao 
 */

public class GuiException extends Exception {
  
	 /**
   * constructor
   */
  public GuiException() {
  }

  /**
   * constructor
   */
  public GuiException(final String message) {
    super(message);
  }
}
