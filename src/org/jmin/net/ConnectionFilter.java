/*
*Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.net;

/**
 * Socket Server Interceptor
 *
 * @author Chris
 */
public interface ConnectionFilter {

  /**
   * @throws SecurityException
   */
  public void validate(Connection connection)throws SecurityException;

}