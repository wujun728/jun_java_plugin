class Person
{
	String name ;
};
public class OODemo11
{
	public static void main(String args[])
	{
		Person p1 = new Person() ;
		p1.name = "Ä§ÀÖ" ;
		fun(p1) ;
		System.out.println(p1.name) ;
	}
	public static void fun(Person p2)
	{
		p2.name = "ÀîĞË»ª" ;
	}
};