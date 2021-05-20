class Person
{
	// private声明的属性只能在本类看见
	// 名字
	private String name ;
	// 年龄
	private int age ;
	Person()
	{}
	// 加入一个构造方法
	Person(String n,int a)
	{
		// 为类中的属性初始化
		this.setName(n) ; 
		this.setAge(a) ;
		System.out.println("** 构造方法被调用...") ;
	}

	// 加入一系列的setter和getter方法
	public void setName(String n)
	{
		name = n ;
	}
	public void setAge(int a)
	{
		if(a>0&&a<150)
		{
			age = a ;
		}
		else
		{
			age = -1 ;
		}
	}
	public String getName()
	{
		return name ;
	}
	public int getAge()
	{
		return age ;
	}

	public String shout()
	{
		return "姓名："+this.name+"\n年龄："+this.age ;
	}
};
// 要使用类需要通过对象
public class OODemo06
{
	public static void main(String args[])
	{
		// Person lxh1 = new Person("李兴华",-30) ;
		Person lxh1 = new Person() ;
		lxh1.setName("李兴华") ;
		// 从逻辑上而言代码已经是错误的
		lxh1.setAge(30) ;
		System.out.println(lxh1.shout()) ;
	}
};