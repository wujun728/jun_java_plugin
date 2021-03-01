import java.util.* ;
class Person
{
	private String name ;
	public Person(String name)
	{
		this.name = name ;
	}
	public String toString()
	{
		return "姓名："+this.name ;
	}
};
public class AdvDemo01
{
	public static void main(String args[])
	{
		// Person p = new Person("张三") ;
		Map m = new HashMap() ;
		// m.put("张三",new Person("张三")) ;
		m.put(new Person("张三"),"张三") ;
		// m.put(p,"张三") ;
		// System.out.println(m.get("张三")) ;
		System.out.println(m.get(new Person("张三"))) ;
		// System.out.println(m.get(p)) ;
	}
};