package org.jmin.gui.definition;

import java.awt.Component;
import javax.swing.Icon;
import org.jmin.gui.resource.LocaleResourceBundle;
import org.jmin.gui.resource.ResourceManager;

public class TitleWindowDefinition {

  /**
   * application resource bundle
   */
  private static LocaleResourceBundle applicationResourceBundle = ResourceManager.getManager().getApplicationResourceBundle();

  /**
   * name
   */
  private String name;

  /**
   * image name
   */
  private String iconName;

  /**
   * direction:Left,Right,Down
   */
  private String direction;

  /**
   * permission list
   */
  private String permissions;

  /**
   * component
   */
  private Component component;

  public String getName() {
    return applicationResourceBundle.getText(name);
  }

  public void setName(String name) {
    this.name = name;
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

  /**
   * Return display componen
   */
  public Component getComponent() {
    return component;
  }

  public void setComponent(Component component) {
    this.component = component;
  }

  public String getDirection() {
    return direction;
  }

  public void setDirection(String direction) {
    this.direction = direction;
  }

  public String getPermissions() {
    return permissions;
  }

  public void setPermissions(String permissions) {
    this.permissions = permissions;
  }
}