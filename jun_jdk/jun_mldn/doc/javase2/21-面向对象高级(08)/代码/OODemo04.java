public class OODemo04
{
	public static void main(String args[])
	{
		System.out.println("----------- 异常发生之前 ----------") ;
		
		try
		{
			// 抛出的是一个异常类的实例化对象
			System.out.println("第一个参数："+args[0]) ;	
			System.out.println(1 / 0) ;
			System.out.println("*****************") ;
		}
		catch (ArithmeticException ae)
		{
			System.out.println("发生异常类：") ;
			System.out.println(ae) ;
		}
		catch(ArrayIndexOutOfBoundsException abe)
		{
			System.out.println("发生第二个异常：") ;
			System.out.println(abe) ;
		}
		
		System.out.println("----------- 异常发生之后 ----------") ;
	}
};