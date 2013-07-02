/*
*Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.ioc.impl.instance;

import org.jmin.ioc.BeanContainer;

/**
 * A Java Hook run when application end and dispose all beans in ioc container.
 *
 * @author Chris Liao
 * @version 1.0
 */

public class InstanceDestroyHook extends Thread{

  /**
   * conatain a ioc container
   */
  private BeanContainer container;

  /**
   * constructor
   */
  public InstanceDestroyHook(BeanContainer container){
    this.container = container;
  }

  /**
   * main method of hook thread
   */
  public void run(){
    this.container.destroy();
  }
}