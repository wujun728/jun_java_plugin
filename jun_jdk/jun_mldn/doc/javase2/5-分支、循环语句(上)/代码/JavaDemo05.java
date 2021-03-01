public class JavaDemo05
{
	public static void main(String args[])
	{
		int i = 4 ;
		switch(i)
		{
			case 1:
				System.out.println("条件1满足。") ;
				// 表示退出switch语句
				break ;
			case 2:
				System.out.println("条件2满足。") ;
				break ;
			case 3:
				System.out.println("条件3满足。") ;
				break ;
			default:
				// 如果一切条件都不满足了，则执行default语句
				System.out.println("没有任何条件满足。") ;
				break ;
		}
	}
};