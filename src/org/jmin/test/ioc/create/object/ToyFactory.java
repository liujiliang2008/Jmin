/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.test.ioc.create.object;

/**
 * 创建测试工厂类
 *
 * @author Chris Liao
 */
public class ToyFactory {
	
  /**
   * 方法创建
   */
  public Toy create(String name){
    return new Toy(name);
  }
}
