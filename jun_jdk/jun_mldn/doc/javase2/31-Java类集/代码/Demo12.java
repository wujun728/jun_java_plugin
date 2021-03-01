import java.util.* ;
public class Demo12
{
	public static void main(String args[])
	{
		Properties p = new Properties() ;
		p.setProperty("中国的首都","北京") ;
		p.setProperty("日本的首都","东京") ;
		p.setProperty("美国的首都","华盛顿") ;

		System.out.println(p.getProperty("日本的首都","不存在。")) ;
	}
};