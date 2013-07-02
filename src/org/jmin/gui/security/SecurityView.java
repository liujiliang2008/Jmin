package org.jmin.gui.security;

import java.awt.Component;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.jmin.gui.component.GuiView;
import org.jmin.gui.util.GuiUtil;

public class SecurityView extends GuiView {

  /**
   * Store some permission for components
   */
  private Map permissionMap;

  /**
   * Security somponents
   */
  private Map securityComponents = new HashMap();

  /**
   * setSecurityMap
   */
  public void setPermissionMap(Map permissionMap) {
    this.permissionMap = permissionMap;
    if (this.permissionMap != null && !this.permissionMap.isEmpty()) {
      Iterator permissonItor = this.permissionMap.entrySet().iterator();
      while (permissonItor.hasNext()) {
        Map.Entry entry = (Map.Entry) permissonItor.next();
        String componentName = (String) entry.getKey();
        String permissions = (String) entry.getValue();
        Component component = (Component) securityComponents
                            .get(componentName);
        if (component != null) {
          component.setEnabled(isPermitted(permissions));
        }
      }
    }
  }

  /**
   * Add a security component to map,which can be set enable
   */
  protected void addSecurityComponent(Component component) {
    securityComponents.put(component.getName(), component);
  }

  /**
   * Remove a security component to map,which can be set enable
   */
  protected void renoveSecurityComponent(Component component) {
    securityComponents.remove(component.getName());
  }

  /**
   * Validate security permission for component
   */
  private boolean isPermitted(String permissions) {
    String[] permissionArray = GuiUtil.split(permissions, ",");
    if (permissionArray == null || permissionArray.length == 0) {
      return true;
    } else {
      return SecurityManager.getInstance().implies(permissionArray);
    }
  }
}