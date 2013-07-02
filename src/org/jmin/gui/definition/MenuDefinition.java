package org.jmin.gui.definition;

import java.util.List;

import javax.swing.Icon;

import org.jmin.gui.resource.LocaleResourceBundle;
import org.jmin.gui.resource.ResourceManager;

/**
 * Menu description object
 *
 * @author chris
 */

public class MenuDefinition {

  /**
   * Name
   */
  private String name;

  /**
   * toolTipText
   */
  private String toolTipText;

  /**
   * image name
   */
  private String iconName;

  /**
   * mnemonic
   */
  private char mnemonic;

  /**
   * hot key
   */
  private String shortcut;

  /**
   * permission list
   */
  private String permissions;

  /**
   * sub item list
   */
  private List itemList;

  /**
   * application resource bundle
   */
  private static LocaleResourceBundle applicationResourceBundle = ResourceManager.getManager().getApplicationResourceBundle();
  
  
  public String getName() {
    return applicationResourceBundle.getText(name);
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getToolTipText() {
    return applicationResourceBundle.getText(toolTipText);
  }

  public void setToolTipText(String toolTipText) {
    this.toolTipText = toolTipText;
  }

  public String getIconName() {
    return iconName;
  }

  public void setIconName(String iconName) {
    this.iconName = iconName;
  }

  public Icon getIcon() {
    return applicationResourceBundle.getIcon(iconName);
  }

  public List getItemList() {
    return itemList;
  }

  public void setItemList(List itemList) {
    this.itemList = itemList;
  }

  public char getMnemonic() {
    return mnemonic;
  }

  public void setMnemonic(char mnemonic) {
    this.mnemonic = mnemonic;
  }

  public String getPermissions() {
    return permissions;
  }

  public void setPermissions(String permissions) {
    this.permissions = permissions;
  }

  public String getShortcut() {
    return shortcut;
  }

  public void setShortcut(String shortcut) {
    this.shortcut = shortcut;
  }

}