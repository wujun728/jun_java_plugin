public class JavaDemo17
{
	public static void main(String args[])
	{
		for (int i=1;i<=100;i++)
		{
			if(i>=40&&i<=50)
			{
				continue ;
			}
			if(i%2==0)
			{
				System.out.print(i+"\t") ;
			}
		}
	}
};