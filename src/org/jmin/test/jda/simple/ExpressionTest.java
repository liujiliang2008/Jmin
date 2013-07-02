package org.jmin.test.jda.simple;

import java.util.HashMap;
import java.util.Map;

import org.jmin.jda.impl.property.OgnlPropertyUtil;

public class ExpressionTest {
	public static void main(String[] args) throws Exception {
		Book book = new Book("<java>");
		Object obj = OgnlPropertyUtil.getPropertyValue(book, "author");
		System.out.println(obj.getClass());

		long time = System.currentTimeMillis();
		for (int i = 0; i < 10000; i++) {
			OgnlPropertyUtil.setPropertyValue(book, "map.name", "ddd");
			System.out.println(OgnlPropertyUtil.getPropertyValue(book, "map.name"));
		}
		long end = System.currentTimeMillis();
		System.out.println(end - time);

		OgnlPropertyUtil.setPropertyValue(book, "name", "book2");
		System.out.println(OgnlPropertyUtil.getPropertyValue(book, "name"));

		Map paramMap = new HashMap();
		paramMap.put("book", book);
		paramMap.put("x2", new Integer(1));

		System.out.println(OgnlPropertyUtil.assertBool("book.age != 0", paramMap));
		System.out.println(OgnlPropertyUtil.assertBool("author.trim() =='chris' && name.trim() ==''",book));
	}
}

class Book {
	private Map map;

	private String name = "  ";

	private String author;

	private int age = 1;

	public Book() {
	}

	public Book(String bookName) {
		this.name = bookName;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public static String hello() {
		return "Hello World";
	}
}