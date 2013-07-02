package org.jmin.gui.resource;

import java.util.Locale;

/**
 * Resource manager
 *
 * @author huang
 */
public class ResourceManager {

  /**
   * single tonle
   */
  private static ResourceManager manager;

  /**
   * Return singleton instance
   */
  public synchronized static ResourceManager getManager() {
    if (manager == null)
      manager = new ResourceManager();
    return manager;
  }

  /**
   * current locale
   */
  private Locale currentLocale = Locale.getDefault();

  /**
   * application resource bundle
   */
  private LocaleResourceBundle applicationResourceBundle;

  /**
   * framework resource bundle
   */
  private LocaleResourceBundle frameworkResourceBundle;

  /**
   * set application Resource Path
   * @param applicationResourcePath
   */
  public synchronized void setApplicationResourcePath(String applicationResourcePath) {
    ApplicationResourceBundle bundle =(ApplicationResourceBundle)getApplicationResourceBundle();
    bundle.setResourcePath(applicationResourcePath);
  }

  /**
   * main frame resource bundle
   */
  public synchronized LocaleResourceBundle getFrameworkResourceBundle() {
    if (frameworkResourceBundle == null){
      frameworkResourceBundle = new FrameworkResourceBundle();
      frameworkResourceBundle.load();
    }
    return frameworkResourceBundle;
  }

  /**
   * applicationResourceBundle
   */
  public synchronized LocaleResourceBundle getApplicationResourceBundle() {
    if (applicationResourceBundle == null){
      applicationResourceBundle = new ApplicationResourceBundle();
      applicationResourceBundle.load();
    }
    return applicationResourceBundle;
  }

  /**
   * Change current Locale
   */
  public synchronized void setLoale(Locale locale) {
    this.currentLocale = locale;
    this.getFrameworkResourceBundle().setLoale(this.currentLocale);
    this.getApplicationResourceBundle().setLoale(this.currentLocale);
  }

  /**
   * reload source from disk
   */
  public synchronized void reload() {
    this.getFrameworkResourceBundle().reload();
    this.getApplicationResourceBundle().reload();
  }

  public static void main(String[] arg){
    ResourceManager.getManager().getApplicationResourceBundle();
  }
}