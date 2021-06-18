public class OODemo10
{
	public static void main(String arg[])
	{
		try
		{
			// 本身抛出的就是一个异常类的对象
			throw new Exception("自己抛出的一个异常。") ;
		}
		catch (Exception e)
		{
			System.out.println(e) ;
		}
	}
};