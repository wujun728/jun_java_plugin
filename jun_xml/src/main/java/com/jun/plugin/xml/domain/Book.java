package com.jun.plugin.xml.domain;

/**
 * 自定义对象 --JavaBean
 * * 需要有一组特殊的方法：getXxx 和 setXxx
 * * 必须存在默认的构造方法
 * 
 * alt + shift + s
 */
public class Book {
	
	private String id;
	private String title;
	private String price;
	
	public String getId(){
		return this.id;
	}

	public void setId(String id){
		this.id = id;
	}

	

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public Book() {
	}

	public Book(String id, String title, String price) {
		this.id = id;
		this.title = title;
		this.price = price;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", price=" + price + ", title=" + title + "]";
	}
	
	
	

	
}
