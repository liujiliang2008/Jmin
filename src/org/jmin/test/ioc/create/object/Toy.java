/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.test.ioc.create.object;

/**
 * 创建测试的类
 *
 * @author Chris Liao
 */
public class Toy {

  /**
   * 名称
   */
  private String name;
  
  /**
   * 构造函数
   */
  public Toy(){}
  
  /**
   * 构造函数
   */
  public Toy(String name){
    this.name=name;
  }

  /**
   * 名称
   */
  public String getName() {
    return name;
  }

  /**
   * 名称
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * 静态方法创建
   */
  public static Toy create(String name){
    return new Toy(name);
  }
}