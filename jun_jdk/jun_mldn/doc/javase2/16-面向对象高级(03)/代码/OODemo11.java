interface A
{
	// 定义常量
	// public static final String INFO = "LXH" ;
	String INFO = "LXH" ;
	// public abstract void say() ;
	public void say() ;
}
interface B 
{
	String MURL = "www.MLDN.cn" ;
	public void hello() ;
}
class X implements A,B
{
	// 子类要实现接口中全部的抽象方法
	public void say()
	{
		System.out.println("信息是："+INFO) ;
		System.out.println("MURL是："+MURL) ;
	}
	public void hello()
	{
		System.out.println("B接口中的内容："+MURL) ;
	}
};
public class OODemo11
{
	public static void main(String args[])
	{
		X x = new X() ;
		x.say() ;
		x.hello() ;
	}
};