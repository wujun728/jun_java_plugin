public class JavaDemo15
{
	public static void main(String args[])
	{
		for (int i=1;i<=100;i++)
		{
			if(i>=50)
			{
				// 程序中断，不再被执行
				// break一般与if语句联用
				break ;
			}
			if(i%2==0)
			{
				System.out.print(i+"\t") ;
			}
		}
	}
};