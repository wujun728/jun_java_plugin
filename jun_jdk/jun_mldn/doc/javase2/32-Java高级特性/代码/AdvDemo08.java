import java.util.* ;
public class AdvDemo08
{
	public static void main(String args[]) throws Exception
	{
		// 通过代码验证
		List all = null ;

		// all = new ArrayList() ;
		// 根据字符串找到一个类
		Class c = Class.forName("java.util.ArrayList") ;
		all = (List)c.newInstance() ;
		all.add("A") ;
		all.add("B") ;
		all.add("C") ;

		System.out.println(all) ;
	}
};