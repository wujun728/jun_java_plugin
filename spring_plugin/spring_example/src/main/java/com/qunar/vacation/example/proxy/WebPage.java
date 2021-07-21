package com.qunar.vacation.example.proxy;

public class WebPage {
	private Image image;
	private String text;

	public WebPage(Image image, String text) {
		super();
		this.image = image;
		this.text = text;
	}
	
	public void display(){
		System.out.println(text);
		image.display();
	}
	
	public static void main(String[] args) {
		Image image = new ImageProxy("/tmp/xxx.png");
		WebPage webPage = new WebPage(image, "hello");
		webPage.display();
	}
}
