package org.jmin.gui.resource;

public class ApplicationResourceBundle extends LocaleResourceBundle {

  private String resourcePath = "/resource/";

  /**
   * Return source path
   */
  public String getResourcePath(){
    return this.resourcePath;
  }

  /**
   * set application Resource Path
   * @param applicationResourcePath
   */
  void setResourcePath(String applicationResourcePath) {
    this.resourcePath = applicationResourcePath;
    if (!this.resourcePath.endsWith("/"))
      this.resourcePath += "/";
  }
}