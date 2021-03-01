class MathDemo
{
	// 此方法有没有可能发生异常？
	public int div(int i,int j) throws Exception
	{
		return i / j ;
	}
};

public class OODemo09
{
	public static void main(String arg[]) throws Exception
	{
		System.out.println(new MathDemo().div(10,0)) ;
	}
};