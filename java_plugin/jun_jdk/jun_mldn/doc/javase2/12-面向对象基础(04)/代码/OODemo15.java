class Person
{
	private String name ;
	private int age ;
	/*
		程序要提供一个无参的构造方法，此构造方法用于打印创建person对象的信息
		程序要再提供一个设置姓名的方法，年龄的默认值为1，但此方法也要打印对象创建信息
		程序要再再提供一个有两个参数的构造方法，用于设置姓名和年龄，但要求也打印对象创建信息
	*/
	public Person()
	{
		System.out.println("** 创建了一个Person对象。") ;
	}
	public Person(String name)
	{
		System.out.println("** 创建了一个Person对象。") ;
		this.setName(name) ;
		this.setAge(1) ;
	}
	public Person(String name,int age)
	{
		System.out.println("** 创建了一个Person对象。") ;
		this.setName(name) ;
		this.setAge(age) ;
	}
	public void setName(String name)
	{
		this.name = name ;
	}
	public void setAge(int age)
	{
		this.age = age ;
	}
	public int getAge()
	{
		return this.age ;
	}
	public String getName()
	{
		return this.name ;
	}
};
public class OODemo15
{
	public static void main(String args[])
	{
		Person per = new Person() ;
		per.setName("张三") ;
		System.out.println(per.getName()) ;
	}
};

// 10  水瓶
// 0.1 水瓶