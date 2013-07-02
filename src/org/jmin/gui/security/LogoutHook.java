package org.jmin.gui.security;

class LogoutHook extends Thread {

  private SecurityManager securityManager;

  public LogoutHook(SecurityManager securityManager) {
    this.securityManager = securityManager;
  }

  public void run() {
    this.securityManager.logout();
  }
}