/*
*Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.test.net;

/**
 * A block class
 *
 * @author Chris Liao
 * @version 1.0
 */

public final class ObjectLocker{

  /**
   * Block the current thread
   */
  public static void lock(Object obj){
    try{
      synchronized(obj){
        obj.wait();
        obj.notify();
      }
    }catch(Exception e){
      e.printStackTrace();
    }
  }

  /**
   * Block the current thread with specified time
   */
  public static void lock(Object obj,long timeout){
    try{
      synchronized(obj){
        obj.wait(timeout);
        obj.notify();
      }
    }catch(Exception e){
      e.printStackTrace();
    }
  }

  /**
   * uUnblock the current thread with specified time
   */
  public static void unlock(Object obj){
    try{
      synchronized(obj){
        obj.notify();
      }
    }catch(Exception e){
      e.printStackTrace();
    }
  }
}