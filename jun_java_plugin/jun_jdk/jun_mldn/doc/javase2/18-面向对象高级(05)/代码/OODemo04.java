interface A
{
	public void fun() ;
}
class B implements A
{
	public void fun()
	{
		System.out.println("Hello World!!!") ;
	}
};
public class OODemo04
{
	public static void main(String args[])
	{
		A a = new B() ;
		a.fun() ;
	}
};