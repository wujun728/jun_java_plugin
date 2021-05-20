public class OODemo07
{
	public static void main(String args[])
	{
		System.out.println("----------- 异常发生之前 ----------") ;
		
		try
		{	
			System.out.println(1 / 1) ;
			System.out.println("*****************") ;
		}
		catch(Exception e)
		{
			System.out.println("程序出错啦。。。") ;
			System.out.println(e) ;
		}
		finally
		{
			// 一般用于释放资源连接：JDBC、IO
			System.out.println("无论是否发生异常都要执行我。。。") ;
		}
		System.out.println("----------- 异常发生之后 ----------") ;
	}
};