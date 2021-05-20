public class JavaDemo16
{
	public static void main(String args[])
	{
		for (int i=1;i<=10;i++)
		{
			// 如果是5，则不打印
			if(i==5)
			{
				// 不打印
				continue ;
			}
			System.out.print(i+"\t") ;
		}
	}
};