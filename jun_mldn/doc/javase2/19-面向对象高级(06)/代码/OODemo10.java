interface A
{
	public void fun() ;
}
class C
{
	public void fun2()
	{
		this.print(new A()
			{
				public void fun()
				{
					System.out.println("MLDN --> LXH") ;
				}
			}
		) ;
	}
	public void print(A a)
	{
		a.fun() ;
	}
};
public class OODemo10
{
	public static void main(String args[])
	{
		new C().fun2() ;
	}
};