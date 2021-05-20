interface A
{
	public void fun() ;
}

// B类只使用一次，那还有必要声明吗？
class B implements A
{
	public void fun() 
	{
		System.out.println("MLDN --> LXH") ;
	}
};
class C
{
	public void fun2()
	{
		this.print(new B()) ;
	}
	public void print(A a)
	{
		a.fun() ;
	}
};
public class OODemo09
{
	public static void main(String args[])
	{
		new C().fun2() ;
	}
};