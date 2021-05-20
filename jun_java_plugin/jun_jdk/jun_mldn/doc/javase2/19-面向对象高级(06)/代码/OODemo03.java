// Object类是所有类的父类，如果一个类没有明确声明继承自那个类
// 则肯定会继承Object类
class Person// extends Object
{
	private String name ;
	private int age ;
	public Person(String name,int age)
	{
		this.name = name ;
		this.age = age ;
	}
	public String toString()
	{
		return "姓名："+this.name+"，年龄："+this.age ;
	}
};
public class OODemo03
{
	public static void main(String args[])
	{
		Person p1 = new Person("LXH",30) ;
		Person p2 = new Person("LXH",30) ;
		System.out.println(p1.equals(p2)) ;
	}
};