public class JavaDemo03
{
	public static void main(String args[])
	{
		int i = 10 ;
		// i++ ;
		// 以上代码相当于以下的程序段：
		// i = i + 1 ;
		// ++i：表示先让数据自增，之后再进行操作
		// i++：表示先让数据进行操作，操作完成之后进行自增
		int k = 10+(++i) ;
		System.out.println(k) ;
		System.out.println(i) ;
	}
};
