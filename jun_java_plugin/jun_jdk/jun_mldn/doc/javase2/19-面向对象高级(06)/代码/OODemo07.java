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
};
public class OODemo07
{
	public static void main(String args[])
	{
		Outer o = new Outer() ;
		Outer.Inner in = o.new Inner() ;
		in.print() ;
	}
};