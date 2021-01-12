// 定义一个抽象类
abstract class A
{
	private String info ;
	/*
	public A()
	{
		System.out.println("抽象类中的构造方法。") ;
	}
	*/
	public A(String info)
	{
		this.info = info ;
		System.out.println("抽象类中的构造方法。") ;
	}
	public void fun2()
	{
		System.out.println("info --> "+this.info) ;
	}
	public abstract void fun() ;
};
class B extends A
{
	public B(String info)
	{
		super(info) ;
	}
	public void fun()
	{
		System.out.println("欢迎大家光临www.mldn.cn。") ;
	}
};
public class OODemo07
{
	public static void main(String args[])
	{
	}
};