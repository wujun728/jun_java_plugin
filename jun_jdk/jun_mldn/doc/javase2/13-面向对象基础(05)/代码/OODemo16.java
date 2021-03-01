class Person
{
	// 静态代码块
	static
	{
		System.out.println("static code() ...") ;
	}
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
public class OODemo16
{
	public static void main(String args[])
	{
		new Person() ;
		new Person() ;
	}
};