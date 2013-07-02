package org.jmin.gui.security;

import java.awt.Dimension;
import java.awt.Toolkit;

public class LoginContext implements SecurityContext{

  /**
   * log in system,when successful,then return a user context object
   */
  public User login()throws SecurityException{
    LoginDialog dialog = new LoginDialog();
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = dialog.getSize();
    if (frameSize.height > screenSize.height) {
      frameSize.height = screenSize.height;
    }
    if (frameSize.width > screenSize.width) {
      frameSize.width = screenSize.width;
    }
    dialog.setLocation((screenSize.width - frameSize.width) / 2,
                     (screenSize.height - frameSize.height) / 2);
    dialog.setVisible(true);
    return dialog.getUserInfo();
  }

  /**
   * logout from system
   */
  public void logout(User userContext)throws SecurityException{

  }
}