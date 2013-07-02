package org.jmin.gui.security;

public class UserRole {

  private String name;

  private String description;

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int hashCode() {
    return name.hashCode();
  }

  public boolean equals(Object target) {
    if (target != null &&  target instanceof UserRole) {
      UserRole other = (UserRole) target;
      return getName().equals(other.getName());
    }
    return false;
  }

}