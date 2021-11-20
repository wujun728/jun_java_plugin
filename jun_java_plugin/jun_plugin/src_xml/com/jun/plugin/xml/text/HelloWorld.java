package com.jun.plugin.xml.text;

import com.jun.plugin.xml.domain.Book;

public class HelloWorld {
	
	public String value(Book book){
		return book.getId() + ":" + book.getTitle();
	}
	
	

}
