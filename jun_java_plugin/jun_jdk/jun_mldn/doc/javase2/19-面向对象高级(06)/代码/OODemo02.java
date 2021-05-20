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
public class OODemo02
{
	public static void main(String args[])
	{
		Person p = new Person("LXH",30) ;
		// 默认情况下打印对象，实际上就相当于调用对象中的toString方法
		System.out.println(p) ;
	}
};