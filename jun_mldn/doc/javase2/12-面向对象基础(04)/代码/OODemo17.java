class Person
{
	String name ;
	int age ;
	public Person(String name,int age)
	{
		this.name = name ;
		this.age = age ;
	}
	public void print()
	{
		System.out.println("print方法中的："+this) ;
	}
};
public class OODemo17
{
	public static void main(String args[])
	{
		Person p = new Person("张三",30) ;
		System.out.println("main方法中的："+p) ;
		p.print() ;
		System.out.println("------------------------") ;
		Person p1 = new Person("李四",33) ;
		System.out.println("main方法中的："+p1) ;
		p1.print() ;
	}
};