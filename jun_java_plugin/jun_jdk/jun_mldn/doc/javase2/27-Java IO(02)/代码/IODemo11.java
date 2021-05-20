import java.io.* ;
public class IODemo11
{
	public static void main(String args[])
	{
		File f = new File("f:\\lxh.txt") ;
		Reader in = null ;
		try
		{
			in = new FileReader(f) ;
		}
		catch (Exception e)
		{
		}
		// 声明一个byte数组，用于接收内容
		char b[] = new char[500] ;
		int len = 0 ;
		try
		{
			// 所有的数据都在byte数组中
			len = in.read(b) ;
		}
		catch (Exception e)
		{
		}
		try
		{
			in.close() ;
		}
		catch (Exception e)
		{
		}
		System.out.println(new String(b,0,len)) ;
		// 输出打印的内容

	}
};