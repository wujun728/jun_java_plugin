import java.util.* ;
import java.lang.reflect.* ;
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
public class AdvDemo09
{
	public static void main(String args[]) throws Exception
	{
		Person p = null ;
		// 通过Class实例化Person对象
		// p = (Person)Class.forName("Person").newInstance() ;
		// 根源在于，程序实例化时需要使用构造方法
		Class cl = Class.forName("Person") ;
		Object obj[] = {"张三",new Integer(30),new Float(89)} ;
		Constructor c = cl.getConstructors()[0] ;
		p = (Person)c.newInstance(obj) ;
		System.out.println(p) ;
	}
};