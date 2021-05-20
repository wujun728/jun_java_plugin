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
public class Demo01
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
		// all.remove(1) ;
		for(int i=0;i<all.size();i++)
		{
			System.out.println(all.get(i)) ;
		}
	}
};