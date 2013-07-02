package org.jmin.gui.security;

import org.jmin.gui.GuiException;
import org.jmin.gui.GuiLauncher;
import org.jmin.gui.util.GuiUtil;

/**
 * Security Xml Launcher,it will get the applicaion instance from system
 * context, then run it.
 *
 * @author chris
 */

public abstract class SecurityLauncher extends GuiLauncher {

  /**
   * Launch method to run application
   */
  public void launch()throws GuiException  {
  	GuiUtil.initGuiFont();
    SecurityManager.getInstance().initSecurityContext(getSecurityContext());
    SecurityManager.getInstance().login();
    super.launch();
  }

  /**
   * Launch method to run application
   */
  public void launch(String file) throws GuiException {
  	GuiUtil.initGuiFont();
    SecurityManager.getInstance().initSecurityContext(getSecurityContext());
    SecurityManager.getInstance().login();
    super.launch(file);
  }

  
  /**
   * 运行指定文件
   */
  public void launch(String file,String beanid)throws GuiException{
  	GuiUtil.initGuiFont();
    SecurityManager.getInstance().initSecurityContext(getSecurityContext());
    SecurityManager.getInstance().login();
    super.launch(file,beanid);
  }
  
  /**
   * Abstract method to get a Security Context,this method should be implemented in sub class
   */
  public abstract SecurityContext getSecurityContext();

}