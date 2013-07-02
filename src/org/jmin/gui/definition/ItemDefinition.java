package org.jmin.gui.definition;
				
import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionListener;

import javax.swing.Icon;

import org.jmin.gui.resource.LocaleResourceBundle;
import org.jmin.gui.resource.ResourceManager;

/**
 * 可点击Item描述，主要为菜单条目或Toolbar的点击的Button
 *
 * @author chris
 */

public class ItemDefinition {

  /**
   * name
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
   * display component
   */
  private Component guiView;

  /**
   * action Listener
   */
  private ActionListener actionListener;
  
  /**
   * application resource bundle
   */
  private static LocaleResourceBundle applicationResourceBundle = ResourceManager.getManager().getApplicationResourceBundle();
  
  /**********************Methods begin ******************/

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

  public Image getImage() {
    return applicationResourceBundle.getImage(iconName);
  }

  public String getShortcut() {
    return shortcut;
  }

  public void setShortcut(String shortcut) {
    this.shortcut = shortcut;
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

  public Component getGuiView() {
		return guiView;
	}

	public void setGuiView(Component guiView) {
		this.guiView = guiView;
	}

	public ActionListener getActionListener() {
    return actionListener;
  }

  public void setActionListener(ActionListener actionListener) {
    this.actionListener = actionListener;
  }

}