class Person{
	private String name ;
	private int age;
	private Book book ;	// 一个人有一本书
	private Person child ;
	public Person(String n,int a){
		this.setName(n) ;
		this.setAge(a) ;
	}
	public void setChild(Person c){
		child = c ;
	}
	public Person getChild(){
		return child ;
	}
	public void setBook(Book b){
		book = b ;
	}
	public void setName(String n){
		name = n ;
	}
	public void setAge(int a){
		age = a ;
	}
	public Book getBook(){
		return book ;
	}
	public String getName(){
		return name ;
	}
	public int getAge(){
		return age ;
	}
};
class Book{
	private String title ;
	private float price ;
	private Person person ;
	public Book(String t,float p){
		this.setTitle(t) ;
		this.setPrice(p) ;
	}
	public void setPerson(Person p){
		person = p ;
	}
	public void setTitle(String t){
		title = t ;
	}
	public void setPrice(float p){
		price = p ;
	}
	public Person getPerson(){
		return person ;
	}
	public String getTitle(){
		return title ;
	}
	public float getPrice(){
		return price ;
	}
};

public class RefDemo05{
	public static void main(String args[]){
		Person per = new Person("张三",30) ;
		Person chd = new Person("张四",10) ;
		Book bk = new Book("Java基础",89.0f) ;
		Book bkc = new Book("童话故事",89.0f) ;
		per.setChild(chd) ;
		chd.setBook(bkc) ;
		bkc.setPerson(chd) ;
		per.setBook(bk) ;		// 一个人有一本书
		bk.setPerson(per) ;	// 一本书属于一个人
		System.out.println(per.getBook().getTitle()) ;	// 由人找到其所拥有书的名字
		System.out.println(bk.getPerson().getName()) ;	// 由书找到人的名字
	}
};