import java.util.* ;
class Person
{
	private String name ;
	private int age ;
	private float score ;
	public Person(String name,int age,float score)
	{
		this.name = name ;
		this.age = age ;
		this.score = score ;
	}
	public String toString()
	{
		return "姓名："+this.name+"，年龄："+this.age+"，成绩："+this.score ;
	}
};
public class AdvDemo07
{
	public static void main(String args[])
	{
		Person p = new Person("张三",30,89) ;
		System.out.println(p.getClass().getName()) ;
	}
};