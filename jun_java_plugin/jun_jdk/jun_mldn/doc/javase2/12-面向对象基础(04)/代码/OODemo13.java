class Person
{
	private String name ;
	private int age ;
	public void setName(String name)
	{
		this.name = name ;
	}
	public String getName()
	{
		return this.name ;
	}
};
public class OODemo13
{
	public static void main(String args[])
	{
		Person per = new Person() ;
		per.setName("张三") ;
		System.out.println(per.getName()) ;
	}
};

// 10  水瓶
// 0.1 水瓶