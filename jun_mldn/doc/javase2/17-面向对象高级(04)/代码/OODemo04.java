class A
{
};
class B extends A
{
};
public class OODemo04
{
	public static void main(String args[])
	{
		A a = new A() ;
		System.out.println(a instanceof A) ;
		System.out.println(a instanceof B) ;
	}
};