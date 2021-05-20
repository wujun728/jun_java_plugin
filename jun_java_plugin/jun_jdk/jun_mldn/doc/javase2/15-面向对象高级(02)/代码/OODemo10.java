class A
{
	String name = "LXH" ;
	public String say()
	{
		return "hello" ;
	}
};
class B extends A
{
	String name = "MLDN" ;
	public B()
	{
		super() ;
		System.out.println("HELLO") ;		
	}
	public String say()
	{
		return super.say()+" world!! --> "+super.name ;
	}
};

public class OODemo10
{
	public static void main(String args[])
	{
		System.out.println(new B().say()) ;
	}
};