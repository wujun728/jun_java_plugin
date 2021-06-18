class Person
{
	private String name ;
	private int age ;
	public void setName(String name)
	{
		// 强调一下，调用本类中的print方法
		this.print() ;
		this.name = name ;
	}
	public String getName()
	{
		return this.name ;
	}
	public void print()
	{
		System.out.println("设置内容了。") ;
	}
};
public class OODemo14
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