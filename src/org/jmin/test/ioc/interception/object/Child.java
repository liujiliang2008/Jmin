/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.test.ioc.interception.object;

/**
 * A bean class for IOC test.
 *
 * @author Chris Liao
 */
public class Child implements Young{

  /**
   * name
   */
  private String name;

  /**
   * age
   */
  private int age;

  /**
   * Return man age
   */
  public int getAge() {
    return age;
  }

  /**
   * set Age
   */
  public void setAge(int age) {
    this.age = age;
  }

  /**
   * return name
   */
  public String getName() {
    return name;
  }

  /**
   * Set name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Print hello
   */
  public void sayHello(String target){
    System.out.println("Hello,"+ target);
  }
}