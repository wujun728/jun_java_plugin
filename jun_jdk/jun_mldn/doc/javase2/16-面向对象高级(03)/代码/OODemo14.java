interface A
{
	public void printA() ;
}
interface B
{
	public void printB() ;
}
interface C extends A,B
{
	public void printC() ;
}
abstract class D implements C
{
	public abstract void printC() ;
};
class X extends D implements C
{
	public void printA() 
	{
		System.out.println("Hello A ...") ;
	}
	public void printB() 
	{
		System.out.println("Hello B ...") ;
	}
	public void printC() 
	{
		System.out.println("Hello C ...") ;
	}
};
public class OODemo14
{
	public static void main(String args[])
	{
		X x = new X() ;
		x.printA() ;
		x.printB() ;
		x.printC() ;
	}
};