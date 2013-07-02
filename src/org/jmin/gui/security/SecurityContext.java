package org.jmin.gui.security;

import java.io.Serializable;
import java.lang.SecurityException;

/**
 * A security context object.
 *
 * @author Chris
 */
public interface SecurityContext extends Serializable {

  /**
   * log in system,when successful,then return a user context object
   */
  public User login()throws SecurityException;

  /**
   * logout from system
   */
  public void logout(User userContext)throws SecurityException;
}