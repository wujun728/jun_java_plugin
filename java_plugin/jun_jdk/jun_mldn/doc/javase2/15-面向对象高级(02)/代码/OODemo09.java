class A
{
	public String say()
	{
		return "hello" ;
	}
};
class B extends A
{
	public String say()
	{
		return super.say()+" world!!" ;
	}
};

public class OODemo09
{
	public static void main(String args[])
	{
		System.out.println(new B().say()) ;
	}
};