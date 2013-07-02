/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.test.jda.user;

/**
 * 实体类
 * 
 * @author chris
 */
public class User {

	private String ID;

	private String name;

	private String sex;
	
	public User() {}
	public User(String name) {
		this.name = name;
	}
	
	public User(String name, String sex) {
		this.name = name;
		this.sex = sex;
	}

	public String getID() {
		return ID;
	}

	public void setID(String id) {
		ID = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String toString() {
		return "Name " + name + " sex: " + sex;
	}

	public static void main(String[] args) {
		System.out.println("(ID.inlineparamete) it is error");
		System.out
				.println("[Jda-INFO](ID.inlineparamete)Type handler for base parameter class("
						+ "safsdf" + ")not found");

	}
}
