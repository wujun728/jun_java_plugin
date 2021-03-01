class Outer
{
	private String info = "LXH --> MLDN" ;
	// ÄÚ²¿Àà
	class Inner
	{
		public void print()
		{
			System.out.println("INFO = "+info) ;
		}
	};
	public void fun()
	{
		new Inner().print() ;
	}
};
public class OODemo05
{
	public static void main(String args[])
	{
		Outer o = new Outer() ;
		o.fun() ;
	}
};