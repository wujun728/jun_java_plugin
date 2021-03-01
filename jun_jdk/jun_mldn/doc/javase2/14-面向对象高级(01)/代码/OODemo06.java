class A
{
	public A()
	{
		System.out.println("1、public A(){}") ;
	}
};
class B extends A
{
	public B()
	{
		// 此处隐含了一段代码
		super() ;
		System.out.println("2、public B(){}") ;
	}
};
public class OODemo06
{
	public static void main(String args[])
	{
		B b = new B() ;
	}
};