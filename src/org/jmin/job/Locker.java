/*
 *Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.job;

/**
 * 线裎阻塞类
 *
 * @author Chris Liao
 * @version 1.0
 */

public final class Locker {

  /**
   * Block the current thread
   */
  public static void lock(Object obj) {
    try {
      synchronized (obj) {
        obj.wait();
        obj.notify();
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Block the current thread with specified time
   */
  public static void lock(Object obj, long timeout) {
    if (timeout > 0) {
      try {
        synchronized (obj) {
          obj.wait(timeout);
          obj.notify();
        }
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * uUnblock the current thread with specified time
   */
  public static void unlock(Object obj) {
    try {
      synchronized (obj) {
        obj.notify();
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * uUnblock the current thread with specified time
   */
  public static void sleep(long time) {
    try {
      Thread.sleep(time);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
}