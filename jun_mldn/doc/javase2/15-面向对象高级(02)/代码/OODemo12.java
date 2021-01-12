// 为了代码的简单，程序暂时不封装
class Employee
{
	String name ;
	int age ;
	float salary ;
	// 表示一个雇员有一个领导
	Manager mgr = null ;
	public Employee(String name,int age,float salary)
	{
		this.name = name ;
		this.age = age ;
		this.salary = salary ;
	}
	public String say()
	{
		return "姓名："+this.name+"，年龄："+this.age+"，薪水："+this.salary ;
	}
};

class Manager extends Employee
{
	// 管理的人员数
	int number ;
	public Manager(String name,int age,float salary,int number)
	{
		super(name,age,salary) ;
		this.number = number ;
	}
	public String say()
	{
		return super.say()+"，管理人员数："+this.number ;
	}
};

public class OODemo12
{
	public static void main(String args[])
	{
		Employee e = new Employee("李四",20,15000) ;
		Manager m = new Manager("张三",30,3000,5) ;
		// m是e的领导
		e.mgr = m ;
		System.out.println("雇员信息："+e.say()) ;
		// 为了防止mgr的内容为空，如果为空则肯定出现NullPointerException
		if(e.mgr!=null)
		{
			System.out.println("经理信息："+e.mgr.say()) ;
		}
	}
};