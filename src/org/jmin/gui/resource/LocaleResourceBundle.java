package org.jmin.gui.resource;
import java.awt.Image;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;

import javax.swing.Icon;

public abstract class LocaleResourceBundle {
	
  /**
   * resouce Store Map
   */
  private Map resouceStoreMap = new HashMap();

  /**
   * current locale
   */
  private Locale currentLocale = Locale.getDefault();

  /**
   * default Resource Store
   */
  protected LocaleResourceStore defaultResourceStore;

  /**
   * current Resource Store
   */
  protected LocaleResourceStore currentResourceStore;

  /**
   * Return source path
   */
  protected abstract String getResourcePath();

  /**
   * constructor
   */
  public LocaleResourceBundle() {}

  /**
   * set locale
   */
  public synchronized void setLoale(Locale locale) {
    if (!currentLocale.equals(locale)) {
      this.currentLocale = locale;
      String currentPath = getResourcePath() + getLanguage(currentLocale);
      currentResourceStore = new LocaleResourceStore(currentPath);
      resouceStoreMap.put(currentPath, currentResourceStore);
    }
  }

  public synchronized void load() {
    String currentLanguagePath = getResourcePath() + getLanguage(currentLocale);
    String defaultLanguagePath = getResourcePath() + "_";
    currentResourceStore = new LocaleResourceStore(currentLanguagePath);
    defaultResourceStore = new LocaleResourceStore(defaultLanguagePath);
   
    resouceStoreMap.put(currentLanguagePath, currentResourceStore);
    resouceStoreMap.put(defaultLanguagePath, defaultResourceStore);
  }

  public synchronized void reload() {
    load();
  }

  /**
   * Return text
   */
  public String getText(String key) {
    try {
      if (key == null)
        return null;

      return currentResourceStore.getText(key);
    } catch (MissingResourceException e) {
      try {
        return defaultResourceStore.getText(key);
      } catch (MissingResourceException ee) {
        return "[Key: " + key + "]";
      }
    }
  }

  public String getText(String key, String defaultValue) {
    return currentResourceStore.getText(key, defaultValue);
  }

  public String getText(String key, String[] param) {
    try {
      return currentResourceStore.getText(key, param);
    } catch (MissingResourceException e) {
      return defaultResourceStore.getText(key, param);
    }
  }

  public boolean getBoolean(String key) {
    try {
      return currentResourceStore.getBoolean(key);
    } catch (MissingResourceException e) {
      return defaultResourceStore.getBoolean(key);
    }
  }

  public boolean getBoolean(String key, boolean defaultValue) {
    return currentResourceStore.getBoolean(key, defaultValue);
  }

  public byte getByte(String key) {
    try {
      return currentResourceStore.getByte(key);
    } catch (MissingResourceException e) {
      return defaultResourceStore.getByte(key);
    }
  }

  public byte getByte(String key, byte defaultValue) {
    return currentResourceStore.getByte(key, defaultValue);
  }

  public short getShort(String key) {
    try {
      return currentResourceStore.getShort(key);
    } catch (MissingResourceException e) {
      return defaultResourceStore.getShort(key);
    }
  }

  public short getShort(String key, short defaultValue) {
    return currentResourceStore.getShort(key, defaultValue);
  }

  public int getInt(String key) {
    try {
      return currentResourceStore.getInt(key);
    } catch (MissingResourceException e) {
      return defaultResourceStore.getInt(key);
    }
  }

  public int getInt(String key, int defaultValue) {
    return currentResourceStore.getInt(key, defaultValue);
  }

  public long getLong(String key) {
    try {
      return currentResourceStore.getLong(key);
    } catch (MissingResourceException e) {
      return defaultResourceStore.getLong(key);
    }
  }

  public long getLong(String key, long defaultValue) {
    return currentResourceStore.getLong(key, defaultValue);
  }

  public float getFloat(String key) {
    try {
      return currentResourceStore.getFloat(key);
    } catch (MissingResourceException e) {
      return defaultResourceStore.getFloat(key);
    }
  }

  public float getFloat(String key, float defaultValue) {
    return currentResourceStore.getFloat(key, defaultValue);
  }

  public double getDouble(String key) {
    try {
      return currentResourceStore.getDouble(key);
    } catch (MissingResourceException e) {
      return defaultResourceStore.getDouble(key);
    }
  }

  public double getDouble(String key, double defaultValue) {
    return currentResourceStore.getDouble(key, defaultValue);
  }

  public Image getImage(String name) {
    if (name == null)
      return null;

    Image image = currentResourceStore.getImage(name);
    if (image == null) {
      image = defaultResourceStore.getImage(name);
    }
    return image;
  }

  public Icon getIcon(String name) {
    if (name == null)
      return null;

    Icon icon = currentResourceStore.getIcon(name);
    if (icon == null) {
      icon = defaultResourceStore.getIcon(name);
    }
    return icon;
  }

  protected String getLanguage(Locale locale) {
    return (locale.getLanguage() + "_" + locale.getCountry()).toLowerCase();
  }
}