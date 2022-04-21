package com.jun.plugin.xml.text;

import com.jun.plugin.xml.domain.Book;

public class TestHelloWorld {
	
	public static void main(String[] args) {
		
		HelloWorld h = new HelloWorld();
		
		Book book = new Book();  //反射
		book.setId("b001");
		book.setTitle("Java Core");
		//book.setBookPrice("98");
		
		Book book2 = new Book("b002","Thinking in java","78");
		
		System.out.println(book2);
		
		String s = h.value(book);
		System.out.println(s);
		
		
		
		
	}

}
