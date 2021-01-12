public class JavaDemo03
{
	public static void main(String args[])
	{
		// 声明了一个数组
		int i[] = null ;
		// 使用数组？
		i = new int[10] ;
		// i[0]  ~ i[9]
		
		for (int x=0;x<10;x++)
		{
			i[x] = x * x ;
		}
		System.out.println("--------- 改变之前------------------") ;
		for (int x=0;x<10;x++)
		{
			System.out.println("i["+x+"] = "+i[x]) ;
		}
		// 为开辟的堆内存空间声明一个别名
		int y[] = i ;
		// 将y中的内容进行修改
		for (int x=0;x<10;x++)
		{
			y[x] = x + 1 ;
		}
		System.out.println("--------- 改变之后------------------") ;
		for (int x=0;x<10;x++)
		{
			System.out.println("i["+x+"] = "+i[x]) ;
		}
	}
};