/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.test.ioc.invocation;

/**
 * A bean class for IOC test.
 *
 * @author Chris Liao
 */
public class Man implements java.io.Serializable{

  /**
   * name
   */
  private String name;

  /**
   * age
   */
  private int age;

  public Man(){}

  /**
   * Default constructor
   */
  public Man(String name,int age){
    this.name=name;
    this.age =age;
  }

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
   * @param target
   */
  public void sayHello(String target){
    this.name = target;
  }

  /**
   * destroy method
   */
  public void destroy(){
    System.out.println("destroy");
  }

  /**
   * Static factory method to create a man
   */
  public static Man create(String name,int age){
    return new Man(name,age);
  }
}