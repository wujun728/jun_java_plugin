class MathDemo
{
	// 此方法有没有可能发生异常？
	public int div(int i,int j) throws Exception
	{
		if(j==0)
		{
			throw new Exception("被除数不能为零。") ;
		}
		return i / j ;
	}
};

public class OODemo11
{
	public static void main(String arg[])
	{
		try
		{
			System.out.println(new MathDemo().div(10,0)) ;
		}
		catch (Exception e)
		{
			System.out.println(e) ;
		}
	}
};