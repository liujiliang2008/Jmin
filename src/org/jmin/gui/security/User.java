package org.jmin.gui.security;

import java.security.Permission;
import java.security.PermissionCollection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class instance works as a infomation storer of current user
 *
 * @author chris
 */

public class User {

  /**
   * user name
   */
  private String userID;

  /**
   * user role id
   */
  private List roleList = new ArrayList();

  /**
   * commission List,contains other user ID
   */
  private List commissionList = new ArrayList();

  /**
   * option Map,which can carry some expand info
   */
  private Map optionMap = new HashMap();

  /**
   * permission list
   */
  private PermissionCollection permissionList;

  /**
   * set name
   */
  public void setUserID(String userID) {
    this.userID = userID;
  }

  public String geUserID() {
    return this.userID;
  }

  public void putOption(String name, Object value) {
    optionMap.put(name, value);
  }

  public void removeOption(String name) {
    optionMap.remove(name);
  }

  public Object getOption(Object key) {
    return this.optionMap.get(key);
  }

  public void addRole(String name, Object value) {
    optionMap.put(name, value);
  }

  public void removeRole(String name) {
    optionMap.remove(name);
  }

  public boolean isRole(UserRole role) {
    if (roleList == null)
      return true;
    else
      return roleList.contains(role) || roleList.contains(role.getName());
  }

  public void addCommission(String userName) {
    if (!commissionList.contains(userName))
      commissionList.add(userName);
  }

  public void removeCommission(String userName) {
    commissionList.remove(userName);
  }

  public boolean containsCommission(String userName) {
    if (commissionList == null)
      return true;
    else
      return commissionList.contains(userID);
  }

  public void setPermissionList(PermissionCollection permissionList) {
    this.permissionList = permissionList;
  }

  public boolean implies(Permission permission) {
    if (permissionList == null)
      return true;
    else
      return permissionList.implies(permission);
  }

  public boolean implies(String permissionID) {
    if (permissionList == null)
      return true;
    else {
      UserPermission permission = new UserPermission(permissionID);
      return permissionList.implies(permission);
    }
  }
}