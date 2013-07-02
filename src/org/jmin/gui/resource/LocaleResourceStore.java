package org.jmin.gui.resource;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.MissingResourceException;
import java.util.Properties;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.jmin.log.Logger;

public class LocaleResourceStore {
	
	private static Logger logger = Logger.getLogger(LocaleResourceStore.class);

  /**
   * message file name
   */
  private final static String MESSAGE_FILE_NAME = "message.properties";

  /**
   * message path
   */
  private String resourcePath;
  
  
  private String messageFilePath;
  
  /**
   * message Map
   */
  private Properties messages = null;

  /**
   * constructor
   */
  public LocaleResourceStore(String resourcePath) {
    this.resourcePath = resourcePath;
    if (!resourcePath.endsWith("/"))
      this.resourcePath += "/";
     messageFilePath = this.resourcePath + MESSAGE_FILE_NAME;
  }

  /**
   * load message
   */
  private void load(String messageFilePath) {
    InputStream stream = null;
    try {
      if(messageFilePath.startsWith("/")){
        stream = getResourceAsStream(messageFilePath);
      }else {
        stream = new FileInputStream(messageFilePath);
      }

      if (stream == null )
        if(messageFilePath.startsWith("/resource")){
      return;
        }else {
          throw new MissingResourceException(
              "missed source file with name: " + messageFilePath, "","");
        }

        messages.load(stream);
    } catch (RuntimeException e) {
      throw e;
    } catch (Exception e) {
      throw new MissingResourceException("missed source file with name: "
          + messageFilePath, "", "");
    } finally {
      if (stream != null)
        try {
        stream.close();
      } catch (Exception e) {

      }
    }
  }

  private InputStream getResourceAsStream(String resourcePath)
      throws FileNotFoundException {

    InputStream stream = null;
    if (resourcePath.startsWith("/")) {
      stream = (LocaleResourceStore.class)
             .getResourceAsStream(resourcePath);
    } else {
      stream = new FileInputStream(resourcePath);
    }
    return stream;
  }

  public String getText(String key) {
  	if(messages==null){
  		messages = new Properties();
  	  logger.debug("load Resource file " + messageFilePath);
  	  this.load(messageFilePath);
  	}
  	
    if(key.startsWith("#")){
      key = key.substring(1);
      if (!messages.containsKey(key))
        throw new MissingResourceException("missed message with key: "
            + key, "", "");
      try {
        return messages.getProperty(key);
      } catch (Exception e) {
        return null;
      }
    }else {
      return key;
    }
  }

  public String getText(String key, String defaultValue) {
    try {
      return getText(key);
    } catch (MissingResourceException e) {
      return defaultValue;
    }
  }

  public String getText(String key, String[] param) {
    return null;
  }

  public boolean getBoolean(String key) {
    return Boolean.getBoolean(getText(key));
  }

  public boolean getBoolean(String key, boolean defaultValue) {
    try {
      return getBoolean(key);
    } catch (MissingResourceException e) {
      return defaultValue;
    }
  }

  public byte getByte(String key) {
    return Byte.parseByte(getText(key));
  }

  public byte getByte(String key, byte defaultValue) {
    try {
      return getByte(key);
    } catch (MissingResourceException e) {
      return defaultValue;
    }
  }

  public short getShort(String key) {
    return Short.parseShort(getText(key));
  }

  public short getShort(String key, short defaultValue) {
    try {
      return getShort(key);
    } catch (MissingResourceException e) {
      return defaultValue;
    }
  }

  public int getInt(String key) {
    return Integer.parseInt(getText(key));
  }

  public int getInt(String key, int defaultValue) {
    try {
      return getInt(key);
    } catch (MissingResourceException e) {
      return defaultValue;
    }
  }

  public long getLong(String key) {
    return Long.parseLong(getText(key));
  }

  public long getLong(String key, long defaultValue) {
    try {
      return getLong(key);
    } catch (MissingResourceException e) {
      return defaultValue;
    }
  }

  public float getFloat(String key) {
    return Float.parseFloat(getText(key));
  }

  public float getFloat(String key, float defaultValue) {
    try {
      return getFloat(key);
    } catch (MissingResourceException e) {
      return defaultValue;
    }
  }

  public double getDouble(String key) {
    return Double.parseDouble(getText(key));
  }

  public double getDouble(String key, double defaultValue) {
    try {
      return getDouble(key);
    } catch (MissingResourceException e) {
      return defaultValue;
    }
  }

  public Image getImage(String imagename) {
    URL imageURL = ImageLoader.getImageURL(resourcePath, imagename);
    if (imageURL != null)
      return Toolkit.getDefaultToolkit().createImage(imageURL);
    else {
      System.err.println("not found image: " + resourcePath + "images/"
                         + imagename);
      return null;
    }
  }

  public Icon getIcon(String iconname) {
    URL imageURL = ImageLoader.getImageURL(resourcePath, iconname);
    if (imageURL != null)
      return new ImageIcon(imageURL);
    else {
      System.err.println("not found icon: " + resourcePath + "images/"
                         + iconname);
      return null;
    }
  }
}