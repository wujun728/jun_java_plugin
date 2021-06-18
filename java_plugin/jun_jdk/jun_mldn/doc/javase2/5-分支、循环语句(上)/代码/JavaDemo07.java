public class JavaDemo07
{
	public static void main(String args[])
	{
		char c = 'a' ;
		// 字符可以直接向int转型
		switch(c)
		{
			case 'a':
				System.out.println("条件1满足。") ;
				// 表示退出switch语句
				break ;
			case 'b':
				System.out.println("条件2满足。") ;
				break ;
			case 'c':
				System.out.println("条件3满足。") ;
				break ;
			default:
				// 如果一切条件都不满足了，则执行default语句
				System.out.println("没有任何条件满足。") ;
				break ;
		}
	}
};