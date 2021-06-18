import java.io.* ;	
public class IODemo22
{
	public static void main(String args[]) throws Exception
	{
		BufferedReader buf = new BufferedReader(new InputStreamReader(System.in)) ;
		int i1 = 0 ;
		int i2 = 0 ;
		String str = null ;
		try
		{
			
	
		System.out.print("输入第一个数字：") ;
		str = buf.readLine() ;

		i1 = Integer.parseInt(str) ;
		System.out.print("输入第二个数字：") ;
		str = buf.readLine() ;

		i2 = Integer.parseInt(str) ;
		System.out.println("两数相加之和为："+(i1+i2)) ;
			}
		catch (Exception e)
		{
			System.out.println("输入数据格式错误！") ;
		}
	}
};