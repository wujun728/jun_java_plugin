class Outer
{
	private String info = "LXH --> MLDN" ;
	public String getInfo()
	{
		return this.info ;
	}
	public void setInfo(String info)
	{
		this.info = info ;
	}
	// 内部类
	public void fun()
	{
		new Inner(this).print() ;
	}
};
// 将内部类取出，形成单独的一个类
class Inner
{
	private Outer out = null ;
	public Inner(Outer out)
	{
		this.out = out ;
	}
	public void print()
	{
		System.out.println("INFO = "+out.getInfo()) ;
	}
};
public class OODemo06
{
	public static void main(String args[])
	{
		Outer o = new Outer() ;
		o.fun() ;
	}
};