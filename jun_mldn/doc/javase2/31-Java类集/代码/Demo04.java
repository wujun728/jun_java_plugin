import java.util.* ;
public class Demo04
{
	public static void main(String args[])
	{
		// 准备好了一个对象数组容器
		Set<java.lang.String> all = new HashSet<java.lang.String>() ; 
		// List<java.lang.String> all = new ArrayList<java.lang.String>() ; 
		// 向集合中加入数据
		all.add("MLDN") ;
		all.add("LXH") ;
		all.add("LXH") ;
		all.add("LXH") ;
		all.add("LXH") ;

		// 向第二个元素处加入新内容
		all.add("www.mldn.cn") ;
		Iterator iter = all.iterator() ;
		while(iter.hasNext())
		{
			Object obj = iter.next() ;
			System.out.println(obj) ;
		}
	}
};