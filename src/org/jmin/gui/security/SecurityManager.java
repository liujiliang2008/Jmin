package org.jmin.gui.security;
import java.security.Permission;
import java.lang.SecurityException;

/**
 * Security Manager for user
 *
 * @author Chris
 */
public class SecurityManager {

  /**
   * Security Manager
   */
  private static SecurityManager securityManager;

  /**
   * Return singleton Security Manager
   */
  public synchronized static SecurityManager getInstance() {
    if (securityManager == null) {
      securityManager = new SecurityManager();
      LogoutHook hook = new LogoutHook(securityManager);
      Runtime.getRuntime().addShutdownHook(hook);
    }
    return securityManager;
  }

  /**
   * Security Context,which need to be set from outside by invoke init method in this class
   */
  private SecurityContext securityContext;

  /**
   * current user context,if login successful,it will not be null;
   */
  private User currentUserInfo;

  /**
   * initialition, set a security context to manager
   * @param securityContext
   * @throws SecurityException
   */
  public void initSecurityContext(SecurityContext securityContext) throws SecurityException {
    if(this.securityContext != null)
      throw new SecurityException("security context has been set!");
    else
       this.securityContext = securityContext;
  }

  /**
   * login system
   */
  public void login() throws SecurityException {
    if (securityContext == null)
      throw new SecurityException("Please init security context first!");

    if (currentUserInfo != null)
      throw new SecurityException("You have been login successfully.");

    this.currentUserInfo = securityContext.login();
  }

  /**
   * Quit from current system
   * @throws SecurityException
   */
  public void logout() throws SecurityException {
    if (currentUserInfo != null && securityContext != null)
      securityContext.logout(currentUserInfo);
  }

  /**
   * Return user ID
   */
  public String getCurrentUserID(){
    if (currentUserInfo == null)
      return null;
    else
      return currentUserInfo.geUserID();
  }

  /**
   * Get a user Option from current user context
   * @param key
   */
  public Object getOption(Object key) {
    if (currentUserInfo == null)
      return null;
    else
      return currentUserInfo.getOption(key);
  }

  public boolean isRole(UserRole role) {
    if (currentUserInfo == null)
      return true;
    else
      return currentUserInfo.isRole(role);
  }

  public boolean containsCommission(String name) {
    if (currentUserInfo == null)
      return true;
    else
      return currentUserInfo.containsCommission(name);
  }

  public boolean implies(Permission permission) {
    if (currentUserInfo == null)
      return true;
    else
      return currentUserInfo.implies(permission);
  }

  public boolean implies(String permission) {
    if (currentUserInfo == null)
      return true;
    else
      return currentUserInfo.implies(permission);
  }

  public boolean implies(String[] permissions) {
    if (currentUserInfo == null || permissions== null)
      return true;
    else {
      for (int i = 0; i < permissions.length; i++) {
        if (!currentUserInfo.implies(permissions[i]))
          return false;
      }
      return true;
    }
  }
}