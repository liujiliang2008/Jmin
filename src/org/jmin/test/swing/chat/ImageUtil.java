/*
 *Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.test.swing.chat;

import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.ImageIcon;

/**
 * Image Util class to load image or icon
 *
 * @author liao
 *
 */

public class ImageUtil {

  /**
   * image path
   */
  private final static String Image_Path = "images";

  /**
   * get Icon
   */
  public static ImageIcon getIcon(String filename) {
    return new ImageIcon(getFileURL(filename));
  }

  /**
   * get Image
   */
  public static Image getImage(String filename) {
    return Toolkit.getDefaultToolkit().getImage(getFileURL(filename));
  }

  /**
   * get file url
   */
  private static URL getFileURL(String filename) {
    return ImageUtil.class.getResource(Image_Path + "/" + filename);
  }

//  /**
//   * main method
//   */
//  public static void main(String[] args) throws Exception {
//    Object obj = ImageUtil.getIcon(Images.ADD_ICON);
//    System.out.print(obj);
//  }

}