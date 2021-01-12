import java.util.* ;
class Person
{
	private String name ;
	private int age ;
	public Person(String name,int age)
	{
		this.name = name ;
		this.age = age ;
	}
	public String toString()
	{
		return "姓名："+this.name+"，年龄："+this.age ;
	}
	public boolean equals(Object obj)
	{
		return true ;
	}
	public int hashCode()
	{
		return this.name.hashCode()+this.age * 1000 ;
	}
};
public class AdvDemo02
{
	public static void main(String args[])
	{
		Map m = new HashMap() ;
		m.put(new Person("张三",30),"张三") ;
		System.out.println(m.get(new Person("张三",30))) ;
	}
};