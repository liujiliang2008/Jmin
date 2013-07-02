package org.jmin.gui.resource;

import java.net.URL;
import java.io.File;

public class ImageLoader {

  public static URL getImageURL(String resourcePath, String fileName) {
    if (!resourcePath.endsWith("/"))
      resourcePath += "/";

    String imagePath = resourcePath + "images/" + fileName;
    if (imagePath.startsWith("/")) {
      return ImageLoader.class.getResource(imagePath);
    } else {
      try {
        return new File(imagePath).toURL();
      } catch (Exception e) {
        return null;
      }
    }
  }
}