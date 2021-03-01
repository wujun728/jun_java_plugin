// 定义一个抽象类
abstract class A
{
	public void fun2()
	{
		System.out.println("Hello World!!!") ;
	}
	public abstract void fun() ;
};
class B extends A	// B是一个普通类
{
	// 抽象类的子类要覆写抽象类中的全部抽象方法
	public void fun()
	{
		System.out.println("欢迎大家光临www.mldn.cn。") ;
	}
};
public class OODemo05
{
	public static void main(String args[])
	{
		B b = new B() ;
		b.fun() ;
		b.fun2() ;
	}
};