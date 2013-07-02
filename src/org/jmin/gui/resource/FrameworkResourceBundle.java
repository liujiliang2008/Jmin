package org.jmin.gui.resource;


public class FrameworkResourceBundle extends LocaleResourceBundle {


  private String resourcePath = null;
  /**
   * Return source path
   */
  protected String getResourcePath(){
  	if(resourcePath== null){
  			resourcePath = this.getClass().getPackage().getName();
  			resourcePath = resourcePath.replace('.', '/');
  			resourcePath = "/"+ resourcePath + "/";
  	}
  	
  	
    return resourcePath;
  }
}