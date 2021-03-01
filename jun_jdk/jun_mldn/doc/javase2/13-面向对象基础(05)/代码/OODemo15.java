class Person
{
	// 构造块
	{
		System.out.println("Hello World!!!") ;
	}
	// 声明一个构造方法
	Person()
	{
		System.out.println("Person()构造方法") ;
	}
};
public class OODemo15
{
	public static void main(String args[])
	{
		new Person() ;
	}
};