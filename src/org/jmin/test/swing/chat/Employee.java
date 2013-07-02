package org.jmin.test.swing.chat;

import java.awt.Dimension;

//import org.jmin.app.swing.gui.EnterpriseFrame;

/**
 * 企业雇员类
 *
 * @author chris
 */
public class Employee extends EnterpriseUnit {

  /**
   * indicator for on line
   */
  private boolean online;

  /**
   * Chat message dialog
   */
  private EmployeeChatDialog dialog;

//  /**
//   * net connector to remote server
//   */
//  private NetConnector netConnector;

  /**
   * is on line ?
   */
  public boolean isOnline() {
    return online;
  }

  public void setOnline(boolean online) {
    this.online = online;
  }

  /**
   * Send a message to current employee
   */
  public void sendMessage(String messsage) {
    getMessageDialog().sendMessage(messsage);
  }

  /**
   * Receive a mssage from current employee
   */
  public void receiveMessage(String message) {
    getMessageDialog().receiveMessage(message);
  }

//  /**
//   * Bind with Connector
//   */
//  public void bind(NetConnector netConnector) {
//    this.netConnector = netConnector;
//  }
//
//  /**
//   * unbind from Connector
//   */
//  public void unbind() {
//    this.netConnector = null;
//  }

  private EmployeeChatDialog getMessageDialog() {
    if (dialog == null) {
      dialog = new EmployeeChatDialog(null,
                                      "Talk with " + this.getIdentityName());
    }

    return dialog;
  }

  /**
   * show message dialog on screen
   */
  public void showMessageDialog() {
    EmployeeChatDialog dialog = getMessageDialog();
    Dimension size = dialog.getToolkit().getScreenSize();

   // Dimension size = EnterpriseFrame.rootFrame.getSize();

    int x = (int) (size.getWidth() - dialog.getWidth()) / 2;
    int y = (int) (size.getHeight() - dialog.getHeight()) / 2;
    dialog.setLocation(x, y);
    dialog.setVisible(true);
    dialog.restoreGui();
  }
}