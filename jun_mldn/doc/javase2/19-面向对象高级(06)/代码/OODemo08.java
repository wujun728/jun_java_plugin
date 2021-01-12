class Outer
{
	private String info = "LXH --> MLDN" ;
	public void fun(final int len)
	{
		class Inner
		{
			public void print()
			{
				System.out.println("len = "+len) ;
				System.out.println("INFO = "+info) ;
			}
		};
		new Inner().print() ;
	}
};
public class OODemo08
{
	public static void main(String args[])
	{
		new Outer().fun(10) ;
	}
};