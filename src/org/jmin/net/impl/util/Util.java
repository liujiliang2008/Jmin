/*
 *Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.net.impl.util;

/**
 * A util class
 *
 * @author Chris Liao
 * @version 1.0
 */

public class Util {

  /**
   * A null string ?
   */
  public static boolean isNull(String value) {
    return (value == null || "".equals(value.trim())) ? true : false;
  }

  /**
   * get hash Code by array
   *
   * @param arry
   * @return
   */
  public static int getClassNameHashCode(Class[] arry) {
    if (arry == null || arry.length == 0) {
      return 0;
    } else {
      String[] classnames = new String[arry.length];
      for (int i = 0; i < arry.length; i++) {
        classnames[i] = arry[i].getName();
      }
      return getHashCode(classnames);
    }
  }

  /**
   * get hash Code by array
   *
   * @param arry
   * @return
   */
  public static int getHashCode(Object[] arry) {
    if (arry == null || arry.length == 0) {
      return 0;
    } else {
      int hashCode = 0;
      for (int i = 0; i < arry.length; i++)
        if (i == 0)
          hashCode = arry[i].hashCode();
      else
        hashCode = hashCode ^ arry[i].hashCode();

      return hashCode;
    }
  }
  
  /**
   * get hash Code by array
   */
  public static double getJavaVersion() {
  	return Double.parseDouble(System.getProperty("java.version").substring(0,3));
  }
  
  
  /**
   * get hash Code by array
   */
  public static void sleep(long time) {
  	Thread currentThread = Thread.currentThread();
  	synchronized(currentThread){
  		 try{
  			 Thread.currentThread().wait(time);
    	 }catch(InterruptedException e){
    	 }
  	}
  }
}