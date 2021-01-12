class Person
{
	private String name ;
	private int age ;
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
	public String say()
	{
		return "姓名："+this.name+"，年龄："+this.age ;
	}
};

class Student extends Person
{
	private String school ;
	public void setSchool(String school)
	{
		this.school = school ;
	}
	public String getSchool()
	{
		return this.school ;
	}
	public String say()
	{
		return "姓名："+this.getName()+"，年龄："+this.getAge()+"，学校："+this.school ;
	}
};
public class OODemo02
{
	public static void main(String args[])
	{
		Student s = new Student() ;
		s.setName("张三") ;
		s.setAge(30) ;
		s.setSchool("MLDN 人才培养基地") ;
		System.out.println(s.say()) ;
	}
};