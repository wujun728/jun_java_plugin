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
class C extends A
{
	// 覆写了fun1()方法
	public void fun1()
	{
		System.out.println("4、C --> public void fun1()") ;
	}
	// 增加了一个fun3()方法
	public void fun4()
	{
		System.out.println("5、C --> public void fun4()") ;
	}
};
public class OODemo03
{
	public static void main(String args[])
	{
		// 对象都态性到底该怎么用？
		// 一个print方法，此方法可以接收A和B类的对象，并调用方法打印
		// 如果是A类对象，则调用fun2()方法
		// 如果是B类对象，则调用fun2()和fun3()两个方法
		print(new C()) ;
		// 如果A类还有20个子类，会怎么样？
		// 则此方法还要再写20遍
	}
	public static void print(A a)
	{
		a.fun2() ;
		// 缺少一个对象是否是某一个类的实例的判断
		if(a instanceof B)
		{
			B b = (B)a ;
			b.fun3() ;
		}
		if(a instanceof C)
		{
			C c = (C)a ;
			c.fun4() ;
		}
	}
	/*
	public static void print(B b)
	{
		b.fun2() ;
		b.fun3() ;
	}
	*/
};