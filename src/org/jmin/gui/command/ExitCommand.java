/*
* Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.gui.command;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import org.jmin.gui.resource.LocaleResourceBundle;
import org.jmin.gui.resource.ResourceManager;

public class ExitCommand implements ActionListener {

  /**
   * framework resource bundle
   */
  private static LocaleResourceBundle frameworkResourceBundle = ResourceManager
      .getManager().getFrameworkResourceBundle();

  /**
   * Invoked when an action occurs.
   */
  public void actionPerformed(ActionEvent e) {
    int option = JOptionPane.showConfirmDialog(null,
        frameworkResourceBundle.getText("#system.exit"),
        frameworkResourceBundle.getText("#system.exitTitle"),
        JOptionPane.YES_NO_OPTION);

    if (option == JOptionPane.YES_OPTION) {
      System.exit(0);
    } else {
      return;
    }
  }
}