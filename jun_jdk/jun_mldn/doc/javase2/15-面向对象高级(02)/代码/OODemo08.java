class A
{
	private String name ;
	private int age ;
	public A()
	{
		System.out.println("1¡¢A --> public A(){}") ;
	}
	public A(String name)
	{
		System.out.println("2¡¢A --> public A(String name){}") ;
	}
	public A(String name,int age)
	{
		System.out.println("3¡¢A --> public A(String name,int age){}") ;
	}
};
class B extends A
{
	private String school ;
	public B(String name,int age,String school)
	{
		super(name,age) ;
	}
};

public class OODemo08
{
	public static void main(String args[])
	{
		new B("zhangsan",30,"www.MLDN.cn") ;
	}
};