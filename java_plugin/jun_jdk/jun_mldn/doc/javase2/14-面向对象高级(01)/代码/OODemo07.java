class A
{
	void fun1()
	{
		System.out.println("* 1¡¢A --> public void fun1()") ;
	}
};
class B extends A
{
	public void fun1()
	{
		System.out.println("* 2¡¢B --> public void fun1()") ;
	}
};
public class OODemo07
{
	public static void main(String args[])
	{
		B b = new B() ;
		b.fun1() ;
	}
};