package org.jmin.test.swing.security;

import java.awt.Dimension;
import java.awt.Toolkit;

import org.jmin.gui.security.SecurityContext;
import org.jmin.gui.security.User;

public class LoginContext implements SecurityContext{

  /**
   * log in system,when successful,then return a user context object
   */
  public User login()throws SecurityException{
  	LoginDialog dialog = new LoginDialog();
  	dialog.setSize(500,500);
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