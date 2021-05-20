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
public class Demo02
{
	public static void main(String args[])
	{
		// 准备好了一个对象数组容器
		List all = new ArrayList() ;
		// 向集合中加入数据
		all.add("MLDN") ;
		all.add("LXH") ;
		all.add(new Person("李")) ;

		// 向第二个元素处加入新内容
		all.add(1,"www.mldn.cn") ;
		Iterator iter = all.iterator() ;
		while(iter.hasNext())
		{
			Object obj = iter.next() ;
			System.out.println(obj) ;
		}
	}
};