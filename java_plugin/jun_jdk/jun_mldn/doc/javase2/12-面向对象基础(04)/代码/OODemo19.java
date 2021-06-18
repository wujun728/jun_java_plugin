class Person
{
	private String name ;
	private int age ;
	public Person(String name,int age)
	{
		this.name = name ;
		this.age = age ;
	}
	public boolean compare(Person p1)
	{
		boolean flag = false ;
		Person p2 = this ;
		if(p1.name.equals(p2.name)&&p1.age==p2.age)
		{
			flag = true ;
		}
		return flag ;
	}
	public void print()
	{
		System.out.println("姓名："+this.name) ;
		System.out.println("年龄："+this.age) ;
	}
};
public class OODemo19
{
	public static void main(String args[])
	{
		// 假设认为，名字和年龄相等的就是同一个人
		Person per1 = new Person("张三",30) ;
		Person per2 = new Person("张三",31) ;
		System.out.println(per1.compare(per2)?"是同一个人":"不是同一个人") ;
	}
};