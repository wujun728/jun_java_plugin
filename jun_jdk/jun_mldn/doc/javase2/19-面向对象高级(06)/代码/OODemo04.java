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
	public boolean equals(Object obj)
	{
		if(!(obj instanceof Person))
		{
			return false ;
		}
		Person per1 = this ;
		Person per2 = (Person)obj ;
		boolean flag = false ;
		if(per1==per2)
		{
			// 判断是否是同一个引用
			flag = true ;
		}
		else
		{
			if(per1.name.equals(per2.name)&&per1.age==per2.age)
			{
				flag = true ;
			}
		}
		return flag ;
	}
	public String toString()
	{
		return "姓名："+this.name+"，年龄："+this.age ;
	}
};
public class OODemo04
{
	public static void main(String args[])
	{
		Person p1 = new Person("LXH",30) ;
		// Person p2 = new Person("LXH",30) ;
		Person p2 = p1 ;
		System.out.println(p1.equals("abc")) ;
	}
};