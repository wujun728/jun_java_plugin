class Person
{
	{
		System.out.println("构造块。") ;
	}
	public Person()
	{
		System.out.println("构造方法。") ;
	}
};
public class Demo02
{
	public static void main(String args[])
	{
		new Person() ;
		new Person() ;
	}
};