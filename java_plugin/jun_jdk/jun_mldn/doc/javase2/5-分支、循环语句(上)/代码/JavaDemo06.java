public class JavaDemo06
{
	public static void main(String args[])
	{
		int i = 20 ;
		int j = 50 ;
		// 将i 和j 中的最大值赋值给max
		int max = 0 ;
		/*
		if(i>j)
		{
			max = i ;
		}
		else
		{
			max = j ;
		}
		*/
		max = i>j?i:j ;
		System.out.println("最大值 MAX = "+max) ;
	}
};