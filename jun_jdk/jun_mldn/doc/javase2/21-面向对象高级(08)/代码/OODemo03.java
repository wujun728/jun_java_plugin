public class OODemo03
{
	public static void main(String args[])
	{
		System.out.println("----------- 异常发生之前 ----------") ;
		try
		{
			System.out.println(1 / 1) ;
			System.out.println("*****************") ;
		}
		catch (ArithmeticException ae)
		{
			System.out.println("发生异常类：") ;
			System.out.println(ae) ;
		}
		
		System.out.println("----------- 异常发生之后 ----------") ;
	}
};