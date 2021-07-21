package ioc.xml;


public class Student {
  
	private String name;
	private Book book;
	private Desk desk;
		
	public Student(){}
	public Student(String name, Book book, Desk desk) {
		super();this.name = name;
		this.book = book;this.desk = desk;
	}
	public String getName() { return name;}
	public void setName(String name) { this.name = name;}
	public Book getBook() { return book;}
	public void setBook(Book book) { this.book = book;}
	public Desk getDesk() { return desk;}
	public void setDesk(Desk desk) { this.desk = desk;}
	
}
