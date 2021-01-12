class Person
{
	private String name ;
	private int age ;
	public Person(String name,int age)
	{
		this.name = name ;
		this.age = age ;
	}
	public void change(Person p)
	{
		p.name = "李四" ;
		p.age = 50 ;
	}
	public void print()
	{
		System.out.println("姓名："+this.name) ;
		System.out.println("年龄："+this.age) ;
	}
};
public class OODemo18
{
	public static void main(String args[])
	{
		Person p = new Person("张三",30) ;
		p.change(p) ;
		p.print() ;
	}
};