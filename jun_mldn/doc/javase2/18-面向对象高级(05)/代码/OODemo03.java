abstract class Person
{
	private String name ;
	private int age ;
	public Person(String name,int age)
	{
		this.setName(name) ;
		this.setAge(age) ;
	}
	public void setName(String name)
	{
		this.name = name ;
	}
	public void setAge(int age)
	{
		this.age = age ;
	}
	public String getName()
	{
		return this.name ;
	}
	public int getAge()
	{
		return this.age ;
	}
	// 将说话的内容作为一个抽象方法，通过子类去实现
	public void say()
	{
		System.out.println(this.getContent()) ;
	}
	public abstract String getContent() ;
};
// 假设人分为两种 ：一种是工人，一种是学生
// 工人有工资，学生有成绩
// 不管是学生还是工人，肯定都可以说话
// 说话的内容不一样
class Worker extends Person
{
	private float salary ;
	public Worker(String name,int age,float salary)
	{
		super(name,age) ;
		this.setSalary(salary) ;
	}
	public void setSalary(float salary)
	{
		this.salary = salary ;
	}
	public float getSalary()
	{
		return this.salary ;
	}
	public String getContent()
	{
		return "工人说 --> 姓名："+super.getName()+"，年龄："+super.getAge()+"，工资："+this.getSalary() ;
	}
};
class Student extends Person
{
	private float score ;
	public Student(String name,int age,float score)
	{
		super(name,age) ;
		this.setScore(score) ;
	}
	public void setScore(float score)
	{
		this.score = score ;
	}
	public float getScore()
	{
		return this.score ;
	}
	public String getContent()
	{
		return "学生说 --> 姓名："+super.getName()+"，年龄："+super.getAge()+"，成绩："+this.getScore() ;
	}
};
public class OODemo03
{
	public static void main(String args[])
	{
		Person p = null ;
		// p = new Student("张三",30,90) ;
		p = new Worker("张三",30,3000) ;
		p.say() ;
	}
};