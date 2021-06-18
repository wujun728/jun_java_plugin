// 抽象类该如何去使用呢？
abstract class A
{
	public abstract void fun() ;
}
class B extends A
{
	public void fun()
	{
		System.out.println("Hello World!!!") ;
	}
};
public class OODemo01
{
	public static void main(String args[])
	{
		A a = new B() ;
		a.fun() ;
	}
};