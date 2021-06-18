class Person
{
	int x = 0 ;
};

public class OODemo09
{
	public static void main(String args[])
	{
		Person p1 = new Person() ;
		p1.x = 30 ;
		fun(p1) ;
		System.out.println(p1.x) ;
	}
	public static void fun(Person p2)
	{
		p2.x = 50 ;
	}
};