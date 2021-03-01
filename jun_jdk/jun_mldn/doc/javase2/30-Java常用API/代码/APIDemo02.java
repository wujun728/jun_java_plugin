public class APIDemo02
{
	public static void main(String args[])
	{
		// String str = "" ;
		// 用+表示字符串连接
		// str = "A" + "B" + "c" ;
		StringBuffer sb = new StringBuffer() ;
		sb.append("A").append("B") ;
		sb.append("C").append(1) ;
		fun(sb) ;
		System.out.println(sb) ;
	}
	// StringBuffer传递的是引用
	public static void fun(StringBuffer s)
	{
		s.append("LXH --> MLDN") ;
	}
};