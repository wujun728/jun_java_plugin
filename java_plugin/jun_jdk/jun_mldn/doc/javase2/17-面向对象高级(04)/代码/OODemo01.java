// 演示方便所以使用普通类
class A
{
	public void fun1()
	{
		System.out.println("1、A --> public void fun1()") ;
	}
	public void fun2()
	{
		this.fun1() ;
	}
};
class B extends A
{
	// 覆写了fun1()方法
	public void fun1()
	{
		System.out.println("2、B --> public void fun1()") ;
	}
	// 增加了一个fun3()方法
	public void fun3()
	{
		System.out.println("3、B --> public void fun3()") ;
	}
};
public class OODemo01
{
	public static void main(String args[])
	{
		A a = null ;
		a = new B() ;
		// 子类对象为父类实例化
		// fun1方法被子类覆写了
		// B b = null ;
		// b = (B)a ;
		a.fun2() ;
		a.fun3() ;
		// b.fun2() ;
	}
};