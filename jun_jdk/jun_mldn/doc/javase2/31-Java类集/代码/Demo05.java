import java.util.* ;
public class Demo05
{
	public static void main(String args[])
	{
		// 准备好了一个对象数组容器
		// Set<java.lang.String> all = new HashSet<java.lang.String>() ; 
		Set<java.lang.String> all = new TreeSet<java.lang.String>() ; 
		
		all.add("X") ;
		all.add("D") ;
		all.add("A") ;
		all.add("E") ;
		all.add("G") ;
		all.add("H") ;
		
		Iterator iter = all.iterator() ;
		while(iter.hasNext())
		{
			Object obj = iter.next() ;
			System.out.println(obj) ;
		}
	}
};