class Person{
	private String name ;
	private int age;
	public Person(){
		System.out.println("新的对象产生了。") ;
	}
	public Person(String name){
		this() ;	// 调用无参构造
		this.name = name ;
	}
	public Person(String name,int age){
		this(name) ;	// 调用有一个参数的构造方法
		this.age = age ;
	}
	public void setName(String name){
		this.name = name ;
	}
	public void setAge(int age){
		this.age = age ;
	}
	public String getName(){
		return this.name ;
	}
	public int getAge(){
		return this.age ;
	}
};
public class ThisDemo05{
	public static void main(String args[]){
		Person per = new Person("张三",30) ;
		System.out.println(per.getName() + " --> " + per.getAge()) ;
	}
};