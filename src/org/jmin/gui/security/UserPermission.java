package org.jmin.gui.security;

import java.security.Permission;

/**
 * User Permission object
 *
 * @author Chris
 */
public class UserPermission extends Permission {

  private String displayName;

  private String description;

  public UserPermission(String name) {
    this(name, name, null);
  }

  public UserPermission(String name, String displayName) {
    this(name, displayName, null);
  }

  public UserPermission(String name, String displayName, String description) {
    super(name);
    this.displayName = displayName;
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

  public String getDisplayName() {
    return displayName;
  }

  public String getActions() {
    return "";
  }

  public boolean implies(Permission permission) {
    return equals(permission);
  }

  public boolean equals(Object target) {
    if (target != null && target.getClass().equals(getClass())) {
      UserPermission perm = (UserPermission) target;
      return getName().equals(perm.getName());
    }
    return false;
  }

  public int hashCode() {
    return getName().hashCode();
  }

}