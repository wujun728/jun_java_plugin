interface A
{
	// 定义常量
	// public static final String INFO = "LXH" ;
	String INFO = "LXH" ;
	// public abstract void say() ;
	public void say() ;
}

class X implements A
{
	// 子类要实现接口中全部的抽象方法
	public void say()
	{
		System.out.println("信息是："+INFO) ;
	}
};
public class OODemo10
{
	public static void main(String args[])
	{
		X x = new X() ;
		x.say() ;
	}
};