interface A 
{
	public void fun1() ;
	public void fun2() ;
	public void fun3() ;
}
abstract class B implements A
{
	public void fun1()
	{}
	public void fun2()
	{}
	public void fun3()
	{}
};
class C extends B
{
	public void fun1()
	{
		System.out.println("HELLO MLDN ...") ;
	}
};
public class OODemo06
{
	public static void main(String args[])
	{
		A a = new C() ;
		a.fun2() ;
	}
};