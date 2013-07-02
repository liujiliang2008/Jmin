package org.jmin.gui;

import org.jmin.ioc.BeanContext;
import org.jmin.ioc.impl.config.BeanContextImpl;

/**
 * Application launcher, it will get the applicaion instance from system
 * context, then run it.
 *
 * @author chris
 */

public class GuiLauncher {

  /**
  * 默认IOC ID
  */
  public final static String ApplicationBeanID ="application";

  /**
   * 运行默认默认配置文件:swing.xml
   */
  public void launch()throws GuiException{
    this.launchUI("swing.xml",ApplicationBeanID);
  }
     
  /**
   * 运行指定文件
   */
  public void launch(String file)throws GuiException{
  	this.launchUI(file,ApplicationBeanID);
  }
  
  /**
   * 运行指定文件
   */
  public void launch(String file,String beanId)throws GuiException{
  	this.launchUI(file,beanId);
  }
  
   /**
   * 运行指定文件
   */
  private void launchUI(String file,String beanId)throws GuiException{
    try{
    	BeanContext context = new BeanContextImpl(file);
      GuiDefinition application =(GuiDefinition)(context.getBean(beanId));
      application.show();
    }catch(Exception e){
     throw new GuiException(e.getMessage());
    }
  }
}