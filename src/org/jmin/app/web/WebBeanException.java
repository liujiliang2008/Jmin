/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.app.web;
import org.jmin.ioc.BeanException;

/**
 * 应用Bean
 *
 * @author Chris liao
 * @version 1.0
 */

public class WebBeanException extends BeanException{

  /**
   * constructor
   */
  public WebBeanException(String message) {
    super(message);
  }

  /**
   * constructor
   */
  public WebBeanException(Throwable cause) {
    super(cause);
  }

  /**
   * constructor
   */
  public WebBeanException(String message,Throwable cause) {
    super(message,cause);
  }
}
